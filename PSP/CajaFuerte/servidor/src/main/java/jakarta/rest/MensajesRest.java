package jakarta.rest;

import domain.servicios.ServiciosMensajes;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import modelo.Mensaje;

import java.util.List;

@Path("mensajes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MensajesRest {

    private final ServiciosMensajes servicios;

    @Inject
    public MensajesRest(ServiciosMensajes servicios) {
        this.servicios = servicios;
    }

    @GET
    @Path("mensajes/{id}")
    public List<Mensaje> getMensajesByCarpeta(@PathParam("id") String id) {
        return servicios.getMensajesByUsuario(id);
    }

    @POST
    public Mensaje addMensaje(Mensaje mensaje) {
        return servicios.addMensaje(mensaje);
    }

    @PUT
    public Mensaje updateMensaje(Mensaje mensaje) {
        return servicios.updateMensaje(mensaje);
    }

    @DELETE
    @Path("/{id}")
    public void deleteMensaje(@PathParam("id") String id) {
        servicios.deleteMensaje(id);
    }

}