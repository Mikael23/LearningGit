package pro.aloginov.revoluttest.exception;

import pro.aloginov.revoluttest.exception.AppException;

public class UniversityAlreadyExistsException extends AppException {

    public UniversityAlreadyExistsException(String message) {
        super(message);
    }
}
