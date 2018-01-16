package pro.aloginov.revoluttest;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import pro.aloginov.revoluttest.exception.UniversityException;
import pro.aloginov.revoluttest.model.University;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import java.sql.*;
import java.sql.Date;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;

@Singleton
public class DatabaseService {

    private final ExecutorService ex;
    private final ComboPooledDataSource cpds;

    /*
    * 1. Должно быть: public методы loadSingle, loadMany, loadMap, .. -""- async -- должны все быть generic <T>
    *     sql, rowMapper, params -> T,    List<T>,        Map<K,V>
    * 2. Должны быть операции модификации (3 в одном - delete, update, insert): public modify(String sql, Object... params), + async
    *     sql, params -> Integer
    *
    *     sql = DELETE FROM university WHERE id = ? AND name = ?
    *     params = [id, name]
    *
    *     StudentService: public int addStudent(id, name) { databaseService.modify("INSERT INTO student VALUES (?, ?)", id, name); }
    *     StudentService: public int deleteStudent(id) { databaseService.modfify("DELETE FROM student WHERE id = ?", id); }
    *
    * 3. Удалить все сущности модели (не должно быть student, university...)
    *
    * */


    @Inject
    public DatabaseService(@Named("ex") ExecutorService ex) throws Exception {
        this.ex = ex;

        cpds = new ComboPooledDataSource();
        cpds.setDriverClass("org.postgresql.Driver"); //loads the jdbc driver
        cpds.setJdbcUrl("jdbc:postgresql://localhost:5432/postgres");
        cpds.setUser("postgres");
        cpds.setPassword("1988");

        // the settings below are optional -- c3p0 can work with defaults
        cpds.setMinPoolSize(5);
        cpds.setAcquireIncrement(5);
        cpds.setMaxPoolSize(20);
        cpds.setMaxStatements(180);

    }

    private Connection getConnection() {
        try {
            return cpds.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /*
    public Connection getConnection() {
        try {
            return DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres",
                    "1988");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
*/
    private static void mapParams(PreparedStatement ps, Object... args) throws SQLException {
        int i = 1;

        for (Object arg : args) {

            if (arg instanceof Date) {
                ps.setTimestamp(i++, new Timestamp(((Date) arg).getTime()));
            } else if (arg instanceof Integer) {
                System.out.print(i);
                ps.setInt(i++, (Integer) arg);
                System.out.print(i);
            } else if (arg instanceof Long) {
                ps.setLong(i++, (Long) arg);
            } else if (arg instanceof Double) {
                ps.setDouble(i++, (Double) arg);
            } else if (arg instanceof Float) {
                ps.setFloat(i++, (Float) arg);
            } else {
                ps.setString(i++, (String) arg);
            }
        }
    }

    private void addParams(PreparedStatement pr, String name, String adress, String city, int id) throws SQLException {


        try {
            pr.setString(1, name);
            pr.setString(2, adress);
            pr.setString(3, city);
            pr.setInt(4, id);

        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    public <T> CompletableFuture<Integer> deleteOneAsync(String sql, Object id) {
        return CompletableFuture.supplyAsync(() -> deleteOne(sql, id), ex);
    }

    private <T> Integer deleteOne(String sql, Object id) {
        try {
            PreparedStatement rs = getConnection().prepareCall(sql);

            loadAnddelete(rs, id);
            return deleteFromBase(rs);

        } catch (SQLException e) {
            e.printStackTrace();
            throw new UniversityException("not fount");

        }
    }

    private Integer deleteFromBase(PreparedStatement rs) throws SQLException {

        Integer i = rs.executeUpdate();
        return i;
    }

    private void loadAnddelete(PreparedStatement rs, Object id) {
        int i = 1;
        try {
            if (id instanceof Integer) {

                rs.setInt(i, (Integer) id);
            } else if (id instanceof String) {

                rs.setString(i, (String) id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public <T> CompletableFuture<List<T>> loadAsync(String sql, RowMapper<T> rowMapper, Object... params) {
        return CompletableFuture.supplyAsync(() -> loadMany(sql, rowMapper, params), ex);
    }

    public <T> CompletableFuture<Map<Integer, University>> loadMaps(String sql, RowMapper<T> rowMapper, Object... params) {
        return CompletableFuture.supplyAsync(() -> loamMaps(sql, rowMapper, params), ex);
    }

    private <T> Map<Integer, University> loamMaps(String sql, RowMapper<T> rowMapper, Object... params) {

        try {
            PreparedStatement rs = getConnection().prepareCall(sql);
            mapParams(rs, params);
            return loadMap(rs, rowMapper, params);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }


    public <T> CompletableFuture<Optional<T>> loadsingle(String sql, RowMapper<T> rowMapper, Object... params) {
        return CompletableFuture.supplyAsync(() -> loadOne(sql, rowMapper, params), ex);
    }

    public <T> Optional<T> loadOne(String sql, RowMapper<T> rowMapper, Object... params) {
        try {
            PreparedStatement ps = getConnection().prepareStatement(sql);
            mapParams(ps, params);
            return loadOptional(ps, rowMapper);
        } catch (Exception e) {
            System.out.print(sql);
            throw new RuntimeException(e);
        }
    }

    public <T> CompletableFuture<String> add(String sql, String name, String adress, String city, int id) throws ExecutionException, InterruptedException {

        return CompletableFuture.supplyAsync(() -> addOne(sql, name, adress, city, id), ex);
    }

    private <T> String addOne(String sql, String name, String adress, String city, int id) {
        try {
            PreparedStatement pr = getConnection().prepareStatement(sql);


            addParams(pr, name, adress, city, id);

            return AddOptional(pr);
        } catch (SQLException e) {
            System.out.println(e);
            throw new RuntimeException();

        }

    }

    private <T> String AddOptional(PreparedStatement pr) throws SQLException {
        Map<Integer, University> mapOfUniversityy = new HashMap<>();
        String name = null;
        University university = new University();
        System.out.println("Hello do");
        //   RowMap rw = new RowMap();


        int ps = pr.executeUpdate();

        if (ps == 1) {
            name = "University creaated";
        }

//
//
        mapOfUniversityy.put(ps, university);

        return name;

    }


    public <T> List<T> loadMany(String sql, RowMapper<T> rowMapper, Object... params) {
        try {
            PreparedStatement ps = getConnection().prepareStatement(sql);

            mapParams(ps, params);

            return load(ps, rowMapper);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public <T> Map<Integer, University> loadMap(PreparedStatement rs, RowMapper<T> rowMapper, Object... params) throws SQLException {
        Map<Integer, University> mapOfUniversity = new HashMap<>();
        ResultSet ps = rs.executeQuery();

        Integer r = 0;

        for (Object p : params) {
            if (p instanceof Integer) {
                System.out.print("eto r " + r);

                r = (Integer) p;
                System.out.print("eto r " + r);
            }
        }
        while (ps.next()) {

            T element = rowMapper.get(ps);
            mapOfUniversity.put(r, (University) element);
        }

        return mapOfUniversity;
    }

    public <T> List<T> load(PreparedStatement ps, RowMapper<T> rowMapper) throws SQLException {
        ResultSet rs = ps.executeQuery();
        List<T> result = new ArrayList<>();
        while (rs.next()) {
            T element = rowMapper.get(rs);
            result.add(element);
        }
        // System.out.println("etot metod list"+ result);
        return result;
    }

    public <T> CompletableFuture<String> addStudentAsync(String sql, String name, String city, int birthYear, int universityId, int id) {
        return CompletableFuture.supplyAsync(() -> addStudent(sql, name, city, birthYear, universityId, id), ex);
    }

    private String addStudent(String sql, String name, String city, int birthYear, int universityId, int id) {

        try {
            PreparedStatement pr = getConnection().prepareStatement(sql);


            addStudentParams(pr, name, city, birthYear, universityId, id);
            System.out.println("eto zapros" + pr);
            return AddStudent(pr);
        } catch (SQLException e) {
            System.out.println(e);
            throw new RuntimeException();

        }


    }

    private String AddStudent(PreparedStatement pr) throws SQLException {
        String message = null;

        int r = pr.executeUpdate();
        System.out.println("etor r" + r);
        if (r == 1) {
            message = "Student is added";
        }

        return message;
    }

    private void addStudentParams(PreparedStatement pr, String name, String city, int birthYear, int universityId, int id) {

        try {
            pr.setString(1, name);
            pr.setString(2, city);
            pr.setInt(3, birthYear);
            pr.setInt(4, universityId);
            pr.setInt(5, id);
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    public interface RowMapper<T> {
        T get(ResultSet rs) throws SQLException;


    }

    public class RowMap<T> {
        public University get(ResultSet rsss) throws SQLException {
            University university = new University();

            university.name = rsss.getString("name");
            university.address = rsss.getString("adress");
            university.city = rsss.getString("city");
            university.id = rsss.getInt("Id");
            return university;

        }

    }

    private <T> Optional<T> loadOptional(PreparedStatement ps, RowMapper<T> rowMapper) throws SQLException {
        ResultSet rs = ps.executeQuery();
        Optional<T> element = null;


        if (!rs.next()) {
            return Optional.empty();
        }

        T value = rowMapper.get(rs);
        System.out.print(value);

        if (rs.next()) {
            throw new RuntimeException("Result is not single");
        }
        System.out.print(Optional.of(value));

        return Optional.of((T) value.toString());
    }
}

  /*  @Inject
    public DatabaseService() {
        try {
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres",
                    "1988");

        } catch (SQLException ex) {
            // log an exception. fro example:
            System.out.println("Failed to create the database connection.");
        }
    }
*/





