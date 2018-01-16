package pro.aloginov.revoluttest.exception;

public class CurrencyNotFoundException extends AppException {

    public CurrencyNotFoundException(String currency) {
        super("currency not found: " + currency);
    }

}
