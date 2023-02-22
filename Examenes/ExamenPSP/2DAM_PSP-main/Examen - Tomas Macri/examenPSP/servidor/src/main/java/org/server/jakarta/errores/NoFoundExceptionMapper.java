package org.server.jakarta.errores;

import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import org.server.domain.modelo.errores.NoFoundException;
import org.utils.domain.modelo.ApiError;

import java.time.LocalDateTime;

@Provider
public class NoFoundExceptionMapper implements ExceptionMapper<NoFoundException> {
    public Response toResponse(NoFoundException exception) {
        ApiError apiError = new ApiError(exception.getMessage(), LocalDateTime.now());
        return Response.status(Response.Status.NOT_FOUND).entity(apiError).type(MediaType.APPLICATION_JSON).build();
    }
}
