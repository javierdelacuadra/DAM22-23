package org.server.jakarta.errores;

import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import org.server.domain.modelo.errores.InvalidFieldsException;
import org.utils.domain.modelo.ApiError;

import java.time.LocalDateTime;


@Provider
public class InvalidFieldsExceptionMapper implements ExceptionMapper<InvalidFieldsException> {
    public Response toResponse(InvalidFieldsException exception) {
        ApiError apiError = new ApiError(exception.getMessage(), LocalDateTime.now());
        return Response.status(Response.Status.NOT_ACCEPTABLE).entity(apiError).type(MediaType.APPLICATION_JSON).build();
    }
}

