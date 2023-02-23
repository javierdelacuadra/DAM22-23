package jakarta.rest;

import jakarta.annotation.security.DeclareRoles;
import jakarta.common.ConstantesREST;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

@ApplicationPath(ConstantesREST.API_PATH)
@DeclareRoles({"BIOLOGO", "ESPIA", "RATONES", "INFORMES", "NIVEL1", "NIVEL2"})
public class JAXRSApplication extends Application {
}