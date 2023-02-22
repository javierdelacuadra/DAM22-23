package domain.modelo.errores;

public class TooManyRequestException extends RuntimeException{

        public TooManyRequestException(String message) {
            super(message);
        }
}
