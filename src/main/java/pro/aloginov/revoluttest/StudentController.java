package pro.aloginov.revoluttest;


import pro.aloginov.revoluttest.model.Student;
import pro.aloginov.revoluttest.protocol.StudentCreationRequest;
import pro.aloginov.revoluttest.protocol.StudentCreationResponse;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.concurrent.ExecutionException;

@Path("student")
@Singleton
@Produces(MediaType.APPLICATION_JSON)
public class StudentController {

      private StudentService studentService;
  @Inject
      public StudentController(StudentService studentService) {
         this.studentService = studentService;
    }

    @POST
    public String add(StudentCreationRequest rq) throws ExecutionException, InterruptedException {

         String id = studentService.add(rq.name, rq.city, rq.birthYear, rq.universityId);


      return id;
    }

    @DELETE
    @Path("StudentId")
    public int StudentId(@PathParam("StudentId") int id){
         studentService.delete(id);
         return id;
    }


    @GET
    @Path("{StudentId}")
        public Student StudentGet(@PathParam("StudentId") int id){
           Student student =  studentService.get(id);

            return student;
        }
    }




