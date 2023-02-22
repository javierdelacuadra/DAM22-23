package org.servidor.jakarta.errores;

import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import org.servidor.domain.errores.BaseDeDatosException;
import org.utils.domain.modelo.ApiError;

import java.time.LocalDateTime;

@Provider
public class BaseDeDatosExceptionMapper implements ExceptionMapper<BaseDeDatosException> {

    public Response toResponse(BaseDeDatosException exception) {
        ApiError apiError = new ApiError(exception.getMessage(), LocalDateTime.now());
        return Response.status(Response.Status.NOT_MODIFIED).entity(apiError).type(MediaType.APPLICATION_JSON).build();
    }
}
