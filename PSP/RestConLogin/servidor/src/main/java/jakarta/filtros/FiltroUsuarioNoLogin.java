package jakarta.filtros;

import jakarta.errores.APIError;
import jakarta.filtros.common.ConstantesFiltros;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;

import java.io.IOException;
import java.time.LocalDate;

@Provider
@LoginOnly
public class FiltroUsuarioNoLogin implements ContainerRequestFilter {

    @Context
    private HttpServletRequest request;

    @Override
    public void filter(ContainerRequestContext containerRequestContext) throws IOException {
        if (request.getSession().getAttribute(ConstantesFiltros.LOGIN) == null) {
            containerRequestContext.abortWith(Response.status(Response.Status.FORBIDDEN)
                    .entity(APIError.builder()
                            .mensaje(ConstantesFiltros.PAGINA_O_ACCION_NO_DISPONIBLE_DEBES_INICIAR_SESION_PARA_ACCEDER_A_ELLA)
                            .fecha(LocalDate.now())
                            .build())
                    .type(MediaType.APPLICATION_JSON_TYPE)
                    .build());
        }
    }
}