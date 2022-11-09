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
import java.util.concurrent.atomic.AtomicReference;

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
        return servicios.getAllReaders().get();
    }

    @POST
    public Reader addReader(Reader reader) {
        return servicios.addReader(reader).get();
    }

//    @PUT
//    public Reader updateReader(Reader reader) {
//        return servicios.updateReader(reader);
//    }

    @DELETE
    @Path("/{id}")
    public Response deleteReader(@PathParam("id") String id) {
        if (servicios.deleteReader(id)) {
            return Response.status(Response.Status.NO_CONTENT).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(APIError.builder()
                            .mensaje("No se ha encontrado el usuario")
                            .fecha(LocalDate.now())
                            .build())
                    .build();
        }
    }

    @GET
    @Path("/{id}")
    public Response getReader(@PathParam("id") String id) {
        AtomicReference<Response> r = new AtomicReference<>();
        if (servicios.getReader(id) != null) {
            return Response.ok(Response.Status.OK).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(APIError.builder()
                            .mensaje("No se ha encontrado el usuario")
                            .fecha(LocalDate.now())
                            .build())
                    .build();
        }
    }
}