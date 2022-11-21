package jakarta.errores;

import domain.exceptions.ElementNotFoundException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import modelo.APIError;

import java.time.LocalDate;

@Provider
public class ElementNotFoundExceptionMapper implements ExceptionMapper<ElementNotFoundException> {

    public Response toResponse(ElementNotFoundException exception) {
        return Response.status(Response.Status.NOT_FOUND).entity(
                        APIError.builder()
                                .mensaje(exception.getMessage())
                                .fecha(LocalDate.now().toString()).build())
                .type(MediaType.APPLICATION_JSON_TYPE).build();
    }
}