package org.servidor.jakarta.errores;

import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import org.servidor.domain.errores.NotFoundException;
import org.utils.domain.modelo.ApiError;

import java.time.LocalDateTime;

@Provider
public class NotFoundExceptionMapper implements ExceptionMapper<NotFoundException> {

    public Response toResponse(NotFoundException exception) {
        ApiError apiError = new ApiError(exception.getMessage(), LocalDateTime.now());
        return Response.status(Response.Status.NOT_FOUND).entity(apiError)
                .type(MediaType.APPLICATION_JSON_TYPE).build();
    }
}
