package pro.aloginov.revoluttest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pro.aloginov.revoluttest.model.University;

import javax.inject.Inject;
import javax.inject.Named;
import java.sql.Connection;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;

public class UniversityDaoGetUn {

    private final static Logger log = LoggerFactory.getLogger(UniversityDaoGetUn.class);
    private final ExecutorService ex;
   // private Connection connection;
    DatabaseService databaseService;

    @Inject
    public UniversityDaoGetUn(@Named("ex") ExecutorService ex, DatabaseService databaseService) {
        this.ex = ex;

      //  this.connection = databaseService.getConnection();
        this.databaseService = databaseService;
    }


    public CompletableFuture<Optional<University>> getUniversity(int r) {
        return databaseService.loadsingle("SELECT * FROM university WHERE id = ?", rs -> new University(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getString("city"),
                rs.getString("adress")
        ), r);
    }

        public CompletableFuture<Map<Integer, University>> getMapUniversity(int id){

        return databaseService.loadMaps("SELECT * FROM university WHERE id = ?", rs -> new University(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getString("city"),
                rs.getString("adress")
        ),id);
        }



}