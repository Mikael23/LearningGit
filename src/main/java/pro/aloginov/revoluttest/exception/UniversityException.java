package pro.aloginov.revoluttest.exception;

public class UniversityException extends AppException
{
    public UniversityException(String id) {
        super("University isn't found" + id);
    }
}
