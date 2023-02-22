package org.server.jakarta;


import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;
import org.server.jakarta.common.RestConst;

@ApplicationPath(RestConst.PATH_SERVER)
public class JAXRSApplication extends Application {


}
