package jakarta.rest;

import domain.servicios.ServiciosUsers;
import jakarta.inject.Inject;
import jakarta.rest.common.ConstantesLoginRest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import modelo.User;

@Path(ConstantesLoginRest.RUTA_LOGIN)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class LoginRest {

    @Context
    public HttpServletRequest request;

    @Context
    public SecurityContext securityContext;

    private final ServiciosUsers servicios;

    @Inject
    public LoginRest(ServiciosUsers servicios) {
        this.servicios = servicios;
    }

    @GET
    public User getLogin() {
        return servicios.getUserByName(securityContext.getUserPrincipal().getName());
    }

    @GET
    @Path(ConstantesLoginRest.LOGOUT)
    public Response logout() {
        request.getSession().removeAttribute(ConstantesLoginRest.LOGIN);
        request.getSession().removeAttribute(ConstantesLoginRest.CREDENTIAL);
        return Response.status(Response.Status.OK)
                .entity(ConstantesLoginRest.SESION_CERRADA_CORRECTAMENTE)
                .build();
    }
}