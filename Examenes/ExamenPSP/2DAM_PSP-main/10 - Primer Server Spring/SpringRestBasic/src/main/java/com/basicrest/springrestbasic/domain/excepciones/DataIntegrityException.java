package com.basicrest.springrestbasic.domain.excepciones;

public class DataIntegrityException extends RuntimeException {
    public DataIntegrityException(String message) {
        super(message);
    }

}
