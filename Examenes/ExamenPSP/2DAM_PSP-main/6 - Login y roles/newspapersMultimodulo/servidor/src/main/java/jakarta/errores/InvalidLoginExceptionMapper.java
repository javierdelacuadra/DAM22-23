package jakarta.errores;

import domain.modelo.errores.InvalidLoginException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import org.miutils.domain.modelo.ApiError;

import java.time.LocalDateTime;

@Provider
public class InvalidLoginExceptionMapper implements ExceptionMapper<InvalidLoginException> {
    public Response toResponse(InvalidLoginException exception) {
        ApiError apiError = new ApiError(exception.getMessage(), LocalDateTime.now());
        return Response.status(Response.Status.FORBIDDEN).entity(apiError).type(MediaType.APPLICATION_JSON).build();
    }
}
