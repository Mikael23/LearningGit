package pro.aloginov.revoluttest;


import pro.aloginov.revoluttest.exception.UniversityAlreadyExistsException;
import pro.aloginov.revoluttest.exception.UniversityException;
import pro.aloginov.revoluttest.model.Student;
import pro.aloginov.revoluttest.model.University;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Singleton

public class StudentDao {

    DatabaseService databaseService;
    UniversityDaoGetUn universityDaoGetUn;


    @Inject

    public StudentDao(DatabaseService databaseService, UniversityDaoGetUn universityDaoGetUn) {
        this.databaseService = databaseService;
        this.universityDaoGetUn = universityDaoGetUn;
    }

    public CompletableFuture<String> Addstudent(String name, String city, int birthYear, int universityId,int id) throws ExecutionException, InterruptedException {
        if (!universityDaoGetUn.getMapUniversity(universityId).get().containsKey(universityId)) {
            System.out.println("eto id" + universityId);
            throw new UniversityAlreadyExistsException("univer doesn't exists");
        }
        return databaseService.addStudentAsync("INSERT INTO student(name,city,birthYear,universityId,id)"
                + " values (?,?,?,?,?)", name, city, birthYear, universityId,id);


    }
}
//    public CompletableFuture<String> Adduniversiy(String name, String adress, String city, int id)
//            throws SQLException, ExecutionException, InterruptedException {
//        List<University> un = new ArrayList<>();
//
//
//        if (universityDaoGetUn.getMapUniversity(id).get().containsKey(id)) {
//            System.out.println("eto id" + id);
//            throw new UniversityAlreadyExistsException("univer exists");
//        }
//
//
//        return databaseService.add(
//                "INSERT INTO university(name,adress,city,id)" + " values (?, ?, ?, ?)", name, adress, city, id);
//
//
//    }
//public int add(String name, String city, int birthYear, int universityId) {
//    if (universityService.get(universityId) == null) {
//        throw new UniversityException("univerId = null");
//    }
//    int id = idStudent.getAndIncrement();
//    Student student = new Student(id, name, birthYear, city, universityId);
//    students.put(id, student);
//
//    return id;
//
//}

