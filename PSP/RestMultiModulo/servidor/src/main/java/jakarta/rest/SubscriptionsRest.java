package jakarta.rest;

import domain.servicios.ServiciosSubscriptions;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/subscriptions")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SubscriptionsRest {

    private final ServiciosSubscriptions servicios;

    @Inject
    public SubscriptionsRest(ServiciosSubscriptions servicios) {
        this.servicios = servicios;
    }

    @POST
    public Response addSubscription(@QueryParam("newspaperId") String newspaperId, @QueryParam("readerId") String readerId) {
        if (servicios.addSubscription(newspaperId, readerId)) {
            return Response.status(Response.Status.CREATED).build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @DELETE
    public Response deleteSubscription(@QueryParam("newspaperId") String newspaperId, @QueryParam("readerId") String readerId) {
        if (servicios.deleteSubscription(newspaperId, readerId)) {
            return Response.status(Response.Status.NO_CONTENT).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}
