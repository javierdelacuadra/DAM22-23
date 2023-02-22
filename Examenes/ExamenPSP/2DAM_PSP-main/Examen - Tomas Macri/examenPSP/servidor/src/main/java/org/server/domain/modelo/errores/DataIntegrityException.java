package org.server.domain.modelo.errores;


public class DataIntegrityException extends RuntimeException{

    public DataIntegrityException(String message) {
        super(message);
    }


}
