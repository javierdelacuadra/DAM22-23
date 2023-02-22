package com.serverql.graphqlserver.domain.excepciones;

public class NotFoundException extends RuntimeException{
    public NotFoundException(String message) {
        super(message);
    }
}
