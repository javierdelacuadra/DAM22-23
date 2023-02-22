package jakarta.rest;

import domain.servicios.impl.LoginServicesImpl;
import jakarta.common.RestConstants;
import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import org.miutils.common.AplicationConstants;
import org.miutils.domain.modelo.ApiError;
import org.miutils.domain.modelo.Reader;

import java.time.LocalDateTime;
import java.util.Base64;

@Path(RestConstants.PATH_LOGIN)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RestLogin {
    @Context
    private HttpServletRequest request;

    private final LoginServicesImpl loginServices;

    @Context
    SecurityContext securityContext;


    @Inject
    public RestLogin(LoginServicesImpl loginServices) {
        this.loginServices = loginServices;
    }

    @GET
    public Response login() {
        if (securityContext.getUserPrincipal() != null) {
            return Response.status(Response.Status.NO_CONTENT).build();
        }
        String header = request.getHeader(AplicationConstants.AUTHORIZATION);
        if (header != null && header.split(RestConstants.SPACE).length > 1) {
            String[] credentials = new String(Base64.getDecoder().decode(header.split(RestConstants.SPACE)[1])).split(RestConstants.DOS_PUNTOS);
            loginServices.login(credentials[0], credentials[1]);
        }
        return Response.status(Response.Status.FORBIDDEN).entity(new ApiError(AplicationConstants.COMPLETA_TODOS_LOS_CAMPOS, LocalDateTime.now())).build();
    }

    @POST
    public Response register(Reader reader) {
        return Response.status(Response.Status.CREATED).entity(loginServices.register(reader)).build();
    }

    @GET
    @Path(AplicationConstants.PATH_LOGOUT)
    public Response logout() {
        request.getSession().invalidate();
        return Response.status(Response.Status.NO_CONTENT).build();
    }

    @GET
    @Path(AplicationConstants.PATH_FORGOT)
    public Response forgot(@HeaderParam(AplicationConstants.EMAIL) String email) {
        if (email != null) {
            if (loginServices.forgot(email)) {
                return Response.status(Response.Status.NO_CONTENT).build();
            } else {
                ApiError apiError = new ApiError(RestConstants.NO_EXISTE_EL_USUARIO_CON_EL_MAIL + email, LocalDateTime.now());
                return Response.status(Response.Status.NOT_FOUND).entity(apiError).type(MediaType.APPLICATION_JSON).build();
            }
        }
        ApiError apiError = new ApiError(RestConstants.HEADER_PARAM_INVALIDO + AplicationConstants.EMAIL, LocalDateTime.now());
        return Response.status(Response.Status.BAD_REQUEST).entity(apiError).type(MediaType.APPLICATION_JSON).build();
    }

}
