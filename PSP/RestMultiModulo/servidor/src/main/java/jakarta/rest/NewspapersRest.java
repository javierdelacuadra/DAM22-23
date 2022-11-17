package jakarta.rest;

import dao.modelo.Newspaper;
import domain.servicios.ServiciosNewspapers;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/newspapers")
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
    @Path("/{id}")
    public Newspaper getNewspaper(@PathParam("id") String id) {
        return servicios.get(id);
    }

    @POST
    public Response saveNewspaper(Newspaper newspaper) {
        if (servicios.addNewspaper(newspaper)) {
            return Response.status(Response.Status.CREATED).build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @PUT
    public Response updateNewspaper(Newspaper newspaper) {
        if (servicios.updateNewspaper(newspaper)) {
            return Response.status(Response.Status.OK).build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response deleteNewspaper(@PathParam("id") String id) {
        if (servicios.deleteNewspaper(id)) {
            return Response.status(Response.Status.OK).build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

}