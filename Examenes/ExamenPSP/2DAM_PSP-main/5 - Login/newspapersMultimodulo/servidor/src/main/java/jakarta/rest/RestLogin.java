package jakarta.rest;

import domain.modelo.errores.InvalidFieldsException;
import domain.modelo.errores.InvalidLoginException;
import domain.servicios.impl.LoginServicesImpl;
import jakarta.filtros.LoggedIn;
import jakarta.inject.Inject;
import jakarta.common.RestConstants;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.miutils.domain.modelo.ApiError;
import org.miutils.domain.modelo.Reader;

import java.time.LocalDateTime;

@Path(RestConstants.PATH_LOGIN)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RestLogin {
    @Context
    private HttpServletRequest request;

    private final LoginServicesImpl loginServices;

    @Inject
    public RestLogin(LoginServicesImpl loginServices) {
        this.loginServices = loginServices;
    }

    @GET
    public Reader login(@HeaderParam(RestConstants.EMAIL) String email, @HeaderParam(RestConstants.PASS) String password) {
        if (email != null && password != null) {
            Reader readerLogged = loginServices.login(email, password);
            if (readerLogged != null) {
              request.getSession().setAttribute(RestConstants.LOGIN, email);
              return readerLogged;
            } else {
                throw new InvalidLoginException(RestConstants.EL_USUARIO_O_LA_CONTRASENA_SON_INCORRECTOS);
            }
        }
        throw new InvalidFieldsException(RestConstants.HEADER_PARAM_INVALIDO + RestConstants.TIENE_QUE_HABER_UN_PARAMETRO_EMAIL_PARA_EL_CORREO_Y_PASS_PARA_LA_CONTRSENA);
    }

    @POST
    public Response register(Reader reader) {
        return Response.status(Response.Status.CREATED).entity(loginServices.register(reader)).build();
    }

    @GET
    @LoggedIn
    @Path(RestConstants.LOGOUT)
    public Response logout() {
        request.getSession().invalidate();
        return Response.status(Response.Status.NO_CONTENT).build();
    }

    @GET
    @Path(RestConstants.PATH_FORGOT)
    public Response forgot(@HeaderParam(RestConstants.EMAIL) String email) {
        if (email != null) {
            if (loginServices.forgot(email)) {
                return Response.status(Response.Status.NO_CONTENT).build();
            } else {
                ApiError apiError = new ApiError(RestConstants.NO_EXISTE_EL_USUARIO_CON_EL_MAIL + email, LocalDateTime.now());
                return Response.status(Response.Status.NOT_FOUND).entity(apiError).type(MediaType.APPLICATION_JSON).build();
            }
        }
        ApiError apiError = new ApiError(RestConstants.HEADER_PARAM_INVALIDO + RestConstants.EMAIL, LocalDateTime.now());
        return Response.status(Response.Status.BAD_REQUEST).entity(apiError).type(MediaType.APPLICATION_JSON).build();
    }

}
