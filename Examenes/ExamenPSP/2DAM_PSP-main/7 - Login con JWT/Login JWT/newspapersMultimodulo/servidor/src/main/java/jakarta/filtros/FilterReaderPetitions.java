package jakarta.filtros;

import domain.modelo.errores.TooManyRequestException;
import domain.servicios.impl.LoginServicesImpl;
import jakarta.inject.Inject;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import jakarta.ws.rs.ext.Provider;
import org.miutils.domain.modelo.ApiError;

import java.time.LocalDateTime;

@ReaderPetitions
@Provider
public class FilterReaderPetitions implements ContainerRequestFilter {
    public static final String LIMITE_DE_PETICIONES_SUPERADO = "Limite de peticiones superado";
    @Context
    private SecurityContext securityContext;

    @Inject
    private LoginServicesImpl loginServices;

    @Override
    public void filter(ContainerRequestContext containerRequestContext) {


        try {
            if (!loginServices.hasPetitionsLeft(securityContext.getUserPrincipal().getName())){
                throw new TooManyRequestException(LIMITE_DE_PETICIONES_SUPERADO);
            }
        }
        catch (TooManyRequestException e){
            containerRequestContext.abortWith(Response.status(Response.Status.TOO_MANY_REQUESTS)
                    .entity(ApiError.builder().message(e.getMessage()).fecha(LocalDateTime.now()).build())
                    .type(MediaType.APPLICATION_JSON_TYPE).build());
        }
    }

}
