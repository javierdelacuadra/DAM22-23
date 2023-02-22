package jakarta.errores;

import domain.modelo.errores.TooManyRequestException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import org.miutils.domain.modelo.ApiError;

import java.time.LocalDateTime;

@Provider
public class TooManyRequestExceptionMapper implements ExceptionMapper<TooManyRequestException> {
    public Response toResponse(TooManyRequestException exception) {
        ApiError apiError = new ApiError(exception.getMessage(), LocalDateTime.now());
        return Response.status(Response.Status.TOO_MANY_REQUESTS).entity(apiError).type(MediaType.APPLICATION_JSON).build();
    }
}

