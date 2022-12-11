package jakarta.rest;

import domain.servicios.ServiciosLogin;
import jakarta.inject.Inject;
import jakarta.rest.common.ConstantesLoginRest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import model.ReaderLogin;

@Path(ConstantesLoginRest.RUTA_LOGIN)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class LoginRest {
    @Context
    HttpServletRequest request;

    private final ServiciosLogin servicios;

    @Inject
    public LoginRest(ServiciosLogin servicios) {
        this.servicios = servicios;
    }

    @GET
    public ReaderLogin getLogin(@QueryParam(ConstantesLoginRest.USERNAME) String user, @QueryParam(ConstantesLoginRest.PASSWORD) String pass) {
        ReaderLogin login = servicios.getLogin(user, pass);
        request.getSession().setAttribute(ConstantesLoginRest.LOGIN, true);
        return login;
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
        return Response.status(Response.Status.NO_CONTENT)
                .entity(ConstantesLoginRest.SESION_CERRADA_CORRECTAMENTE)
                .build();
    }

    @GET
    @Path(ConstantesLoginRest.PASSWORD_RECOVERY)
    public Response passwordRecovery(@QueryParam(ConstantesLoginRest.EMAIL) String email) {
        return Response.status(Response.Status.NO_CONTENT)
                .entity(servicios.passwordRecovery(email))
                .build();
    }

    @GET
    @Path(ConstantesLoginRest.EMAIL_RESEND)
    public Response emailResend(@QueryParam(ConstantesLoginRest.EMAIL) String email) {
        return Response.status(Response.Status.NO_CONTENT)
                .entity(servicios.emailResend(email))
                .build();
    }
}