package jakarta.rest;

import jakarta.common.ConstantesREST;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

@ApplicationPath(ConstantesREST.API_PATH)
public class JAXRSApplication extends Application {
}