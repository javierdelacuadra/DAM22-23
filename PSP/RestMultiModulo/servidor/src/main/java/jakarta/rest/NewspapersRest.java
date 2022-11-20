package jakarta.rest;

import domain.servicios.ServiciosNewspapers;
import jakarta.common.ConstantesREST;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import modelo.Newspaper;

import java.util.List;

@Path(ConstantesREST.NEWSPAPERS_PATH)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class NewspapersRest {

    private final ServiciosNewspapers servicios;

    @Inject
    public NewspapersRest(ServiciosNewspapers servicios) {
        this.servicios = servicios;
    }

    @GET
    public List<Newspaper> getAllNewspapers() {
        return servicios.getNewspapers();
    }

    @GET
    @Path(ConstantesREST.PATH_ID)
    public Newspaper getNewspaper(@PathParam(ConstantesREST.ID) String id) {
        return servicios.get(id);
    }

    @POST
    public Response saveNewspaper(Newspaper newspaper) {
        return Response.status(Response.Status.CREATED)
                .entity(servicios.addNewspaper(newspaper))
                .build();
    }

    @PUT
    public Response updateNewspaper(Newspaper newspaper) {
        return Response.status(Response.Status.CREATED)
                .entity(servicios.updateNewspaper(newspaper))
                .build();
    }

    @DELETE
    @Path(ConstantesREST.PATH_ID)
    public Response deleteNewspaper(@PathParam(ConstantesREST.ID) String id) {
        if (servicios.deleteNewspaper(id)) {
            return Response.status(Response.Status.NO_CONTENT).build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
}