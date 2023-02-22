package com.basicrest.springrestbasic.spring.errores;

import com.basicrest.springrestbasic.domain.excepciones.DataIntegrityException;
import com.basicrest.springrestbasic.domain.excepciones.NotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.io.IOException;
import java.time.LocalDateTime;

@ControllerAdvice
@Component
public class ControlErrores extends ResponseEntityExceptionHandler implements AccessDeniedHandler {


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

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiError> handleException(Exception e) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(new ApiError(e.getMessage(),LocalDateTime.now(),HttpStatus.FORBIDDEN.value()));
    }
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {
        response.sendError(HttpServletResponse.SC_FORBIDDEN, "logueate boludo");
    }
}
