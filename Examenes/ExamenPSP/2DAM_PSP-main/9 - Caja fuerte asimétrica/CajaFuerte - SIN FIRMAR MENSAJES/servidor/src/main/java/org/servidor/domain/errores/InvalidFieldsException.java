package org.servidor.domain.errores;

public class InvalidFieldsException extends RuntimeException {
    public InvalidFieldsException(String message) {
        super(message);
    }
}
