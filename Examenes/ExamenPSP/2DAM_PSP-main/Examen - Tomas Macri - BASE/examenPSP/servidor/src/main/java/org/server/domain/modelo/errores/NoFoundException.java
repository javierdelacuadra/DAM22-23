package org.server.domain.modelo.errores;

public class NoFoundException extends RuntimeException {

    public NoFoundException(String message) {
        super(message);
    }
}