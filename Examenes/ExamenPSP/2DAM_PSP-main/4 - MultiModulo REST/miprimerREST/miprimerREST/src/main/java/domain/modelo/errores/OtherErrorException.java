package domain.modelo.errores;


public class OtherErrorException extends RuntimeException {
    public OtherErrorException(String message) {
        super(message);
    }
}
