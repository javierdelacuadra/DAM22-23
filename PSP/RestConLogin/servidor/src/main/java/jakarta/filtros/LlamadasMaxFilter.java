package jakarta.filtros;

import config.Configuracion;
import domain.servicios.ServiciosCalls;
import jakarta.filtros.common.ConstantesFiltros;
import jakarta.inject.Inject;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import jakarta.ws.rs.ext.Provider;

import java.io.IOException;


@Provider
@MaxCallsCheck
public class LlamadasMaxFilter implements ContainerRequestFilter {

    private final ServiciosCalls servicios;
    private final Configuracion configuracion;

    @Inject
    public LlamadasMaxFilter(ServiciosCalls servicios, Configuracion configuracion) {
        this.servicios = servicios;
        this.configuracion = configuracion;
    }

    @Override
    public void filter(ContainerRequestContext containerRequestContext) throws IOException {
        SecurityContext securityContext = containerRequestContext.getSecurityContext();
        String name = securityContext.getUserPrincipal().getName();
        String rol = securityContext.isUserInRole(ConstantesFiltros.USER) ? ConstantesFiltros.USER : null;
        if (name != null && rol != null) {
            int calls = servicios.getCallsByName(name);
            if (calls >= Integer.parseInt(configuracion.getProperty(ConstantesFiltros.MAX_CALLS))) {
                containerRequestContext.abortWith(Response.status(Response.Status.TOO_MANY_REQUESTS).build());
            } else {
                servicios.registerCall(name);
            }
        } else {
            containerRequestContext.abortWith(Response.status(Response.Status.FORBIDDEN).build());
        }
    }
}
