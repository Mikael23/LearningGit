package pro.aloginov.revoluttest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pro.aloginov.revoluttest.exception.UniversityAlreadyExistsException;
import pro.aloginov.revoluttest.model.University;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicInteger;


@Singleton
public class UniversityDao {

    private final static Logger log = LoggerFactory.getLogger(UniversityDao.class);
    private final ExecutorService ex;
    // private Connection connection;
    private DatabaseService databaseService;
    private final AtomicInteger idCounter = new AtomicInteger();
    private UniversityDaoGetUn universityDaoGetUn;


    @Inject
    public UniversityDao(@Named("ex") ExecutorService ex, DatabaseService databaseService, UniversityDaoGetUn universityDaoGetUn) {
        //connection = databaseService.getConnection();
        this.ex = ex;
        this.databaseService = databaseService;
        this.universityDaoGetUn = universityDaoGetUn;
    }

    public CompletableFuture<String> Adduniversiy(String name, String adress, String city, int id)
            throws SQLException, ExecutionException, InterruptedException {
        List<University> un = new ArrayList<>();


        if (universityDaoGetUn.getMapUniversity(id).get().containsKey(id)) {
            System.out.println("eto id" + id);
            throw new UniversityAlreadyExistsException("univer exists");
        }


        return databaseService.add(
                "INSERT INTO university(name,adress,city,id)" + " values (?, ?, ?, ?)", name, adress, city, id);


    }


    public CompletableFuture<List<University>> getUniversities() {
        return databaseService.loadAsync("SELECT * FROM university WHERE id >= ?", rs -> new University(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getString("city"),
                rs.getString("adress")
        ), 0);

    }





    public CompletableFuture<Integer> deleteUniversityId(int id) throws SQLException {


        String sql = "DELETE FROM university WHERE id = ?";

        return databaseService.deleteOneAsync(sql, id);


    }
}