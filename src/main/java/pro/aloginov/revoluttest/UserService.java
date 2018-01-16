package pro.aloginov.revoluttest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pro.aloginov.revoluttest.exception.UserNotFoundException;
import pro.aloginov.revoluttest.model.User;

import javax.inject.Singleton;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Singleton
public class UserService {

    private final Logger log = LoggerFactory.getLogger(UserService.class);

    private final AtomicInteger idGenerator = new AtomicInteger(0);
    private final Map<Integer, User> users = new ConcurrentHashMap<>();

    public User getUser(int id) {
        User user = users.get(id);
        if (user == null) {
            throw new UserNotFoundException(id);
        }
        return user;
    }

    int createUser(String name) {
        int id = idGenerator.getAndIncrement();
        users.put(id, new User(id, name));
        log.info("created user {}", name);
        return id;
    }

}
