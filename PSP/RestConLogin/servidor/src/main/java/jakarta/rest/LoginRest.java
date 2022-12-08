package jakarta.rest;

import domain.servicios.ServiciosLogin;
import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import model.ReaderLogin;

@Path("/login")
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
    public ReaderLogin getLogin(@QueryParam("username") String user, @QueryParam("password") String pass) {
        ReaderLogin login = servicios.getLogin(user, pass);
        request.getSession().setAttribute("LOGIN", true);
        return login;
    }

    @POST
    public Response addLogin(ReaderLogin login) {
        return Response.status(Response.Status.CREATED)
                .entity(servicios.addLogin(login))
                .build();
    }
}