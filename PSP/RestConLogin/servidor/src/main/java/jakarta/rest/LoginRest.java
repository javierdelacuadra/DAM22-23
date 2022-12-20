package jakarta.rest;

import domain.servicios.ServiciosLogin;
import domain.servicios.ServiciosReaders;
import jakarta.inject.Inject;
import jakarta.rest.common.ConstantesLoginRest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import model.Reader;
import model.ReaderLogin;

@Path(ConstantesLoginRest.RUTA_LOGIN)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class LoginRest {

    @Context
    public HttpServletRequest request;

    @Context
    public SecurityContext securityContext;

    private final ServiciosLogin servicios;
    private final ServiciosReaders serviciosReaders;

    @Inject
    public LoginRest(ServiciosLogin servicios, ServiciosReaders serviciosReaders) {
        this.servicios = servicios;
        this.serviciosReaders = serviciosReaders;
    }

    @GET
    public Reader getLogin() {
        request.getSession().setAttribute(ConstantesLoginRest.LOGIN, true);
        return serviciosReaders.getReaderByName(securityContext.getUserPrincipal().getName());
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