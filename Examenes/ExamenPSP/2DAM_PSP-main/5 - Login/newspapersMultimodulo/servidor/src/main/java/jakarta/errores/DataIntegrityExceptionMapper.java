package jakarta.errores;

import domain.modelo.errores.DataIntegrityException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import org.miutils.domain.modelo.ApiError;

import java.time.LocalDateTime;

@Provider
public class DataIntegrityExceptionMapper implements ExceptionMapper<DataIntegrityException> {
    public Response toResponse(DataIntegrityException exception) {
        ApiError apiError = new ApiError(exception.getMessage(), LocalDateTime.now());
        return Response.status(Response.Status.CONFLICT).entity(apiError).type(MediaType.APPLICATION_JSON).build();
    }
}
