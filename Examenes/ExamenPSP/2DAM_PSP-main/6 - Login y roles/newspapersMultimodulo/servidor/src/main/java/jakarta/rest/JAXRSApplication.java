package jakarta.rest;


import jakarta.annotation.security.DeclareRoles;
import jakarta.common.RestConstants;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;
import org.miutils.common.AplicationConstants;

@ApplicationPath(AplicationConstants.PATH_NEWS)
@DeclareRoles({RestConstants.ROLE_ADMIN, RestConstants.ROLE_READER})
public class JAXRSApplication extends Application {


}
