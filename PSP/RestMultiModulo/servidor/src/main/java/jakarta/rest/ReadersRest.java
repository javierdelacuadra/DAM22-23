package jakarta.rest;

import dao.modelo.Reader;
import domain.servicios.ServiciosReaders;
import jakarta.errores.APIError;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.time.LocalDate;
import java.util.List;

@Path("/readers")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ReadersRest {

    private final ServiciosReaders servicios;

    @Inject
    public ReadersRest(ServiciosReaders servicios) {
        this.servicios = servicios;
    }

    @GET
    public List<Reader> getAllReaders() {
        return servicios.getAllReaders();
    }

    @POST
    public Response addReader(Reader reader) {
        if (servicios.addReader(reader)) {
            return Response.status(Response.Status.CREATED).build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @PUT
    public Response updateReader(Reader reader) {
        if (servicios.updateReader(reader)) {
            return Response.status(Response.Status.OK).build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response deleteReader(@PathParam("id") String id) {
        if (servicios.deleteReader(id)) {
            return Response.status(Response.Status.OK).build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @GET
    @Path("/{id}")
    public Reader getReader(@PathParam("id") String id) {
        return servicios.getReader(id);
    }

    @GET
    @Path("/articleTypes")
    public List<Reader> getReadersByArticleType(@QueryParam("type") String type) {
        return servicios.getReadersByArticleType(type);
    }

    @GET
    @Path("/newspapers")
    public List<Reader> getReadersByNewspaper(@QueryParam("idNewspaper") String idNewspaper) {
        return servicios.getReadersByNewspaper(idNewspaper);
    }
}