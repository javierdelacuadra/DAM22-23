package com.example.spring.errors;

import com.example.domain.exceptions.ObjectDoesntExistException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.io.IOException;

@ControllerAdvice
@Component("MisErrores")
public class ControlErrores extends ResponseEntityExceptionHandler implements AccessDeniedHandler, AuthenticationFailureHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ObjectDoesntExistException.class)
    public ResponseEntity<ApiError> handleNotFoundException(ObjectDoesntExistException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ApiError(e.getMessage()));
    }

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, org.springframework.security.access.AccessDeniedException accessDeniedException) throws IOException, ServletException {
        response.getWriter().println(ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ApiError(accessDeniedException.getMessage())));
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        response.getWriter().println(exception);

    }
}
