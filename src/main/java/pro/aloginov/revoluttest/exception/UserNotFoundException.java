package pro.aloginov.revoluttest.exception;

public class UserNotFoundException extends AppException {

    public UserNotFoundException(int id) {
        super("user not found: " + id);
    }

}
