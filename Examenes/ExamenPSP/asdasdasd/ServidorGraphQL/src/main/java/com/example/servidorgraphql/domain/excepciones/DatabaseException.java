package com.example.servidorgraphql.domain.excepciones;

public class DatabaseException extends RuntimeException{
    public DatabaseException(String message) {
        super(message);
    }
}
