package jakarta.errores;

import domain.modelo.errores.InvalidFieldsException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.time.LocalDateTime;


@Provider
public class InvalidFieldsExceptionMapper implements ExceptionMapper<InvalidFieldsException> {
    public Response toResponse(InvalidFieldsException exception) {
        ApiError apiError = new ApiError(exception.getMessage(), LocalDateTime.now());
        return Response.status(Response.Status.BAD_REQUEST).entity(apiError).type(MediaType.APPLICATION_JSON).build();
    }
}

