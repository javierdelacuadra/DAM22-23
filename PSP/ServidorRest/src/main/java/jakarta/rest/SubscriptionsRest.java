package jakarta.rest;

import dao.modelo.Newspaper;
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

//    @POST
//    public Response addSubscription(Newspaper newspaper, String id) {
//        if (servicios.addSubscription(newspaper, id)) {
//            return Response.status(Response.Status.CREATED).build();
//        } else {
//            return Response.status(Response.Status.BAD_REQUEST).build();
//        }
//    }
    //TODO: Implementar el método POST para añadir una suscripción

    @DELETE
    @Path("/{id}")
    public Response deleteSubscription(@PathParam("id") String id, Newspaper newspaper) {
        if (servicios.deleteSubscription(newspaper, id)) {
            return Response.status(Response.Status.OK).build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
}
