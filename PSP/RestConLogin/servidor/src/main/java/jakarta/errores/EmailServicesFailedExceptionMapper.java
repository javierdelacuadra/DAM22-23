package jakarta.errores;

import domain.exceptions.EmailServicesFailedException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.time.LocalDate;

@Provider
public class EmailServicesFailedExceptionMapper implements ExceptionMapper<EmailServicesFailedException> {

    public Response toResponse(EmailServicesFailedException exception) {
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(APIError.builder()
                        .mensaje(exception.getMessage())
                        .fecha(LocalDate.now()).build())
                .type(MediaType.APPLICATION_JSON_TYPE).build();
    }
}