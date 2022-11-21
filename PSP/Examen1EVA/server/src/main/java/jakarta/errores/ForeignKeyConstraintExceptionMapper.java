package jakarta.errores;

import domain.exceptions.ForeignKeyConstraintException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import modelo.APIError;

import java.time.LocalDate;

@Provider
public class ForeignKeyConstraintExceptionMapper implements ExceptionMapper<ForeignKeyConstraintException> {

    public Response toResponse(ForeignKeyConstraintException exception) {
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(
                        APIError.builder()
                                .mensaje(exception.getMessage())
                                .fecha(LocalDate.now().toString()).build())
                .type(MediaType.APPLICATION_JSON_TYPE).build();
    }
}
