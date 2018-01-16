package pro.aloginov.revoluttest;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import pro.aloginov.revoluttest.exception.UniversityException;
import pro.aloginov.revoluttest.model.Student;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicInteger;

@Singleton
public class StudentService {
    private final AtomicInteger idStudent = new AtomicInteger();
    private final Map<Integer, Student> students = new HashMap<>();

    private final UniversityService universityService;
    StudentDao studentDao;

    @Inject
    public StudentService(UniversityService universityService,StudentDao studentDao) {
        this.universityService = universityService;
        this.studentDao = studentDao;
    }


    public String add(String name, String city, int birthYear, int universityId) throws ExecutionException, InterruptedException {
//        if (universityService.get(universityId) == null) {
//            throw new UniversityException("univerId = null");
//        }

        int id = idStudent.getAndIncrement();

       String message =  studentDao.Addstudent(name,city,birthYear,universityId,id).get();

        return message;

    }

    public int delete(int id) {
        students.remove(id);
        return id;
    }

    public Student get(int id){
        Student student = students.get(id);
        return student;
    }



}


