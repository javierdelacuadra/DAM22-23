package org.servidor.jakarta.rest;


import jakarta.annotation.security.DeclareRoles;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;
import org.servidor.jakarta.common.RestConstants;

@ApplicationPath(RestConstants.CAJA)
@DeclareRoles({RestConstants.ROLE_ADMIN, RestConstants.ROLE_USER})
public class JAXRSApplication extends Application {


}
