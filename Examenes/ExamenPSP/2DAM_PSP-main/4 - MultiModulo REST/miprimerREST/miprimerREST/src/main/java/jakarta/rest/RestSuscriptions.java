package jakarta.rest;

import domain.modelo.Suscription;
import domain.servicios.impl.SuscriptionsServicesImpl;
import jakarta.inject.Inject;
import jakarta.rest.common.RestConstants;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;


@Path(RestConstants.PATH_SUSCRIPCIONES)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RestSuscriptions {

    SuscriptionsServicesImpl suscriptionsServices;

    @Inject
    public RestSuscriptions(SuscriptionsServicesImpl suscriptionsServices) {
        this.suscriptionsServices = suscriptionsServices;
    }

    @POST
    public Response addSuscription(Suscription suscription) {
        return Response.status(Response.Status.CREATED).entity(suscriptionsServices.add(suscription)).build();
    }

    @PUT
    public Suscription updateSuscription(Suscription suscription) {
        return suscriptionsServices.update(suscription);
    }

}
