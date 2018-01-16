package pro.aloginov.revoluttest;

import pro.aloginov.revoluttest.model.University;
import pro.aloginov.revoluttest.protocol.UniversityCreationRequest;
import pro.aloginov.revoluttest.protocol.UniversityUpdateRequest;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Singleton
@Path("university")
@Produces(MediaType.APPLICATION_JSON)
public class UniversityController {
    private UniversityService universityService ;
    public DatabaseService databaseService;
    UniversityDao universityDao;
    UniversityDaoGetUn universityDaoGetUn;

    @Inject
    public UniversityController(UniversityService universityService, DatabaseService databaseService,UniversityDao universityDao,
                                UniversityDaoGetUn universityDaoGetUn
    ) {
        this.databaseService = databaseService;
        this.universityService = universityService;
        this.universityDao = universityDao;
        this.universityDaoGetUn = universityDaoGetUn;
    }

    @GET
    @Path("greet")
    public String greet() {
        //stack - for locals, heap - for objects
        int a = 5;
        Object reference1 = new Object();
        Object ref2 = reference1;
            //delete reference1;
        String s = new StringBuilder().append("a").append("b").toString();

        new Object();
        ArrayList<Object> objectList = new ArrayList<>();
        objectList.add(new Object());
        //delete objectList
        return "hello!!!!!";

        /*    o
        *     |
        * x - x - x
        *     | - x
        *
        *   o
        *
        *   o = o
        *
        * */
    }



   /* @GET
    @Path("{universityId}")
    public Optional<University> get(@PathParam("universityId") int id) throws ExecutionException, InterruptedException {
        System.out.print(id);
        Optional<University> university = universityDaoGetUn.getUniversity(id).get();
        return university;
    }*/

    @GET
    @Path("{IDD}")
    public Map<Integer,University> getMapUniversity(@PathParam("IDD") int idd) throws ExecutionException,
            InterruptedException {

        Map<Integer,University> mapsOfUniversity =  universityDaoGetUn.getMapUniversity(idd).get();



        return mapsOfUniversity;


    }


   /* @GET
    @Path("{universityId}")
    public University get(@PathParam("universityId") int id) throws SQLException {
        University university  = universityDao.getUniversities(id);
        System.out.print("getId" + id);
        return university;
    }*/

    @GET
    public List<University> getAll() throws ExecutionException, InterruptedException {

        List<University>universities =  universityDao.getUniversities().get();

        System.out.println("eto univet" + universities.toString());

        return universities;
    }


    @DELETE
    @Path("{universityId}")
    public int deleteUniversity(@PathParam("universityId") int id) throws SQLException, ExecutionException, InterruptedException {

        return universityDao.deleteUniversityId(id).get();
    }

    @POST
    public String add(UniversityCreationRequest req) throws SQLException, ExecutionException, InterruptedException {

       String id = universityService.add(req.name, req.city, req.address);

        return id;
    }

    @PUT
    @Path("{universityId}")
    public University update(UniversityUpdateRequest req, @PathParam("universityId") int id) {
        return universityService.changeFieldsUniversity(id, req.name, req.city, req.address);
    }

}


