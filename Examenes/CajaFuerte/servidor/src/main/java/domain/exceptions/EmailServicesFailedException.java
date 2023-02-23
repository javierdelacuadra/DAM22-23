package domain.exceptions;

public class EmailServicesFailedException extends RuntimeException {
    public EmailServicesFailedException(String message) {
        super(message);
    }
}