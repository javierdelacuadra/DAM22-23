package com.basicrest.springrestbasic.spring.errores;

import com.basicrest.springrestbasic.domain.excepciones.DataIntegrityException;
import com.basicrest.springrestbasic.domain.excepciones.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
@Component
public class ControlErrores extends ResponseEntityExceptionHandler {


    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ApiError> handleNotFoundException(NotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ApiError(e.getMessage(), LocalDateTime.now(), HttpStatus.NOT_FOUND.value()));
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(DataIntegrityException.class)
    public ResponseEntity<ApiError> handleUsernameNotFoundException(DataIntegrityException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ApiError(e.getMessage(),LocalDateTime.now(),HttpStatus.CONFLICT.value()));
    }
}
