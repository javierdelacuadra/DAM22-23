package jakarta.rest;

import domain.servicios.ServiciosReaders;
import jakarta.annotation.security.RolesAllowed;
import jakarta.common.ConstantesREST;
import jakarta.filtros.MaxCallsCheck;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import model.Reader;

import java.util.List;

@Path(ConstantesREST.READERS_PATH)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ReadersRest {

    private final ServiciosReaders servicios;

    @Inject
    public ReadersRest(ServiciosReaders servicios) {
        this.servicios = servicios;
    }

    @GET
    @RolesAllowed({ConstantesREST.ROLE_ADMIN, ConstantesREST.ROLE_USER})
    @MaxCallsCheck
    public List<Reader> getAllReaders() {
        return servicios.getAllReaders();
    }

    @POST
    @RolesAllowed({ConstantesREST.ROLE_ADMIN})
    @MaxCallsCheck
    public Response addReader(Reader reader) {
        return Response.status(Response.Status.CREATED)
                .entity(servicios.addReader(reader))
                .build();
    }

    @PUT
    @RolesAllowed({ConstantesREST.ROLE_ADMIN})
    @MaxCallsCheck
    public Response updateReader(Reader reader) {
        return Response.status(Response.Status.CREATED)
                .entity(servicios.updateReader(reader))
                .build();
    }

    @DELETE
    @Path(ConstantesREST.PATH_ID)
    @RolesAllowed({ConstantesREST.ROLE_ADMIN})
    @MaxCallsCheck
    public Response deleteReader(@PathParam(ConstantesREST.ID) String id) {
        if (servicios.deleteReader(id)) {
            return Response.status(Response.Status.NO_CONTENT).build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @GET
    @Path(ConstantesREST.PATH_ID)
    public Reader getReader(@PathParam(ConstantesREST.ID) String id) {
        return servicios.getReader(id);
    }

    @GET
    @Path(ConstantesREST.ARTICLE_TYPES)
    public List<Reader> getReadersByArticleType(@QueryParam(ConstantesREST.TYPE) String type) {
        return servicios.getReadersByArticleType(type);
    }

    @GET
    @Path(ConstantesREST.NEWSPAPERS_PATH)
    public List<Reader> getReadersByNewspaper(@QueryParam(ConstantesREST.ID_NEWSPAPER) String idNewspaper) {
        return servicios.getReadersByNewspaper(idNewspaper);
    }
}