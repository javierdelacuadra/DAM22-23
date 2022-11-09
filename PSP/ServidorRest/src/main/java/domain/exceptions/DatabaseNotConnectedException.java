package domain.exceptions;

public class DatabaseNotConnectedException extends RuntimeException {
    public DatabaseNotConnectedException(String message) {
        super(message);
    }
}