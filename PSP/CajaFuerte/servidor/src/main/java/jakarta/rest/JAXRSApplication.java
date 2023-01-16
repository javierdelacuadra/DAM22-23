package jakarta.rest;

import jakarta.annotation.security.DeclareRoles;
import jakarta.common.ConstantesREST;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

@ApplicationPath(ConstantesREST.API_PATH)
@DeclareRoles({ConstantesREST.ROLE_ADMIN, ConstantesREST.ROLE_USER})
public class JAXRSApplication extends Application {
}