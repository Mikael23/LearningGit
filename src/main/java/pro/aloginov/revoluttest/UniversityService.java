
package pro.aloginov.revoluttest;


import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.sun.istack.internal.Nullable;
import pro.aloginov.revoluttest.model.University;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicInteger;

@Singleton
public class UniversityService {



    private final AtomicInteger idCounter = new AtomicInteger();
    private final DatabaseService databaseService;
    private final UniversityDao universityDao;
    public final Map<Integer, University> universityrep = new HashMap<>(); //todo remove

    @Inject
    public UniversityService(DatabaseService databaseService, UniversityDao universityDao) {
        this.databaseService = databaseService;

        this.universityDao = universityDao;
    }


    public String  add(String name, String adress, String city) throws SQLException, ExecutionException, InterruptedException {
        int id = idCounter.getAndIncrement();

       // University university = new University(id, name, adress, city);
      //  universityDao.Adduniversiy(name,adress,city,id);
       // universityrep.put(id, university);

        return universityDao.Adduniversiy(name,adress,city,id).get();
    }

    public University get(int id){ //4198 vs 4140
        University university = universityrep.get(id);
        return university;
    }

    public int delete(int id){
        universityrep.remove(id);
        return id;
    }

    public University changeFieldsUniversity(int id, @Nullable String name, @Nullable String address, @Nullable String city){
        University university = universityrep.get(id);
        if (university == null) {
            throw new RuntimeException("unknown university with id=" + id);
        }
        University newUniversity = new University(
                id,
                name == null ? university.name : name,
                address == null ? university.address : address,
                city == null ? university.city : city
        );
       universityrep.put(id, newUniversity);
       return  newUniversity;

    }

}





