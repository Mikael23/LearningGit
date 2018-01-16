package pro.aloginov.revoluttest.exception;

public class AccountNotFoundException extends AppException {

    public AccountNotFoundException(int accountId) {
        super("account not found: " + accountId);
    }

}
