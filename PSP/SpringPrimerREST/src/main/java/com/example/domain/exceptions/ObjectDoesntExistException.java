package com.example.domain.exceptions;

public class ObjectDoesntExistException extends RuntimeException{
    public ObjectDoesntExistException(String message) {
        super(message);
    }
}
