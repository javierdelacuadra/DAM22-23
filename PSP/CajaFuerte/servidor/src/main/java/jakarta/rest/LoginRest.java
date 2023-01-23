package jakarta.rest;

import domain.servicios.ServiciosLogin;
import jakarta.inject.Inject;
import jakarta.rest.common.ConstantesLoginRest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import modelo.ReaderLogin;
import modelo.Usuario;

@Path(ConstantesLoginRest.RUTA_LOGIN)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class LoginRest {

    @Context
    public HttpServletRequest request;

    @Context
    public SecurityContext securityContext;

    private final ServiciosLogin servicios;

    @Inject
    public LoginRest(ServiciosLogin servicios) {
        this.servicios = servicios;
    }

    @GET
    public Usuario getLogin() {
        return servicios.getUsuarioByName(securityContext.getUserPrincipal().getName());
    }

    @POST
    public Response addLogin(ReaderLogin login) {
        return Response.status(Response.Status.CREATED)
                .entity(servicios.addLogin(login))
                .build();
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

    @GET
    @Path(ConstantesLoginRest.PASSWORD_RECOVERY)
    public Response passwordRecovery(@QueryParam(ConstantesLoginRest.EMAIL) String email) {
        servicios.passwordRecovery(email);
        return Response.status(Response.Status.NO_CONTENT)
                .build();
    }

    @GET
    @Path(ConstantesLoginRest.EMAIL_RESEND)
    public Response emailResend(@QueryParam(ConstantesLoginRest.EMAIL) String email) {
        servicios.emailResend(email);
        return Response.status(Response.Status.NO_CONTENT)
                .build();
    }
}