package domain.modelo.errores;

public class InvalidFieldsException extends RuntimeException {
    public InvalidFieldsException(String message) {
        super(message);
    }
}
