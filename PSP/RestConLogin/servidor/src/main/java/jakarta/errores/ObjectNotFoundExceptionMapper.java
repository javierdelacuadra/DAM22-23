package jakarta.errores;

import domain.exceptions.ObjectNotFoundException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.time.LocalDate;

@Provider
public class ObjectNotFoundExceptionMapper implements ExceptionMapper<ObjectNotFoundException> {

    public Response toResponse(ObjectNotFoundException exception) {
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(
                        APIError.builder()
                                .mensaje(exception.getMessage())
                                .fecha(LocalDate.now()).build())
                .type(MediaType.APPLICATION_JSON_TYPE).build();
    }
}