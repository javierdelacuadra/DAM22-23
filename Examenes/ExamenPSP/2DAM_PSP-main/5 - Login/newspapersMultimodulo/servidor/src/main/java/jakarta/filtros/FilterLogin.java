package jakarta.filtros;

import jakarta.common.RestConstants;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;
import org.miutils.domain.modelo.ApiError;

import java.time.LocalDateTime;

@LoggedIn
@Provider
public class FilterLogin implements ContainerRequestFilter {
    @Context
    private HttpServletRequest request;

    @Override
    public void filter(ContainerRequestContext containerRequestContext) {


        if (request.getSession().getAttribute(RestConstants.LOGIN)==null)
        {
            containerRequestContext.abortWith(Response.status(Response.Status.FORBIDDEN)
                    .entity(ApiError.builder().message("Debes loguearte para acceder a esta funcionalidad").fecha(LocalDateTime.now()).build())
                    .type(MediaType.APPLICATION_JSON_TYPE).build());
        }
    }

}
