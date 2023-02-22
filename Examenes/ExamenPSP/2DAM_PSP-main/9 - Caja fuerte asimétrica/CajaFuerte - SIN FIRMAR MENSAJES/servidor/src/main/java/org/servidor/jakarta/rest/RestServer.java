package org.servidor.jakarta.rest;


import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.servidor.domain.servicios.impl.ServiciosServerImpl;
import org.utils.common.AplicationConstants;

@Path(AplicationConstants.PATH_CREDENTIALS)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RestServer {

    private final ServiciosServerImpl serviciosServer;

    @Inject public RestServer(ServiciosServerImpl serviciosServer) {
        this.serviciosServer = serviciosServer;
    }

    @GET
    @Path(AplicationConstants.PATH_PUBLIC)
    public Response getPublic() {
        String publicKey = serviciosServer.getPublic();
        return Response.status(Response.Status.NO_CONTENT).header(AplicationConstants.PUBLIC_KEY,  publicKey).build();
    }
}
