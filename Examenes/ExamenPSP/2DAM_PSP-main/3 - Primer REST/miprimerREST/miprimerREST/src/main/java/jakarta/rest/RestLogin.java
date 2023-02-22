package jakarta.rest;

import domain.modelo.Reader;
import domain.modelo.errores.InvalidFieldsException;
import domain.servicios.impl.LoginServicesImpl;
import jakarta.inject.Inject;
import jakarta.rest.common.RestConstants;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

@Path(RestConstants.PATH_LOGIN)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RestLogin {

    private final LoginServicesImpl loginServices;

    @Inject
    public RestLogin(LoginServicesImpl loginServices) {
        this.loginServices = loginServices;
    }

    @GET
    public Reader login(@QueryParam(RestConstants.USER) String user, @QueryParam(RestConstants.PASS) String password) {
        if (user != null && password != null) {
            return loginServices.login(user, password);
        }
        throw new InvalidFieldsException(RestConstants.QUERY_PARAM_INVALIDO + RestConstants.TIENE_QUE_HABER_UN_PARAMETRO_USER_PARA_EL_USUARIO_Y_PASS_PARA_LA_CONTRSENA);
    }

}
