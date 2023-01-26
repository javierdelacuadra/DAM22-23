package jakarta.rest;

import domain.servicios.ServiciosMensajes;
import jakarta.inject.Inject;
import jakarta.rest.common.ConstantesLoginRest;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import modelo.Mensaje;

import java.util.List;

@Path(ConstantesLoginRest.MENSAJES_PATH)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MensajesRest {

    private final ServiciosMensajes servicios;

    @Inject
    public MensajesRest(ServiciosMensajes servicios) {
        this.servicios = servicios;
    }

    @GET
    @Path(ConstantesLoginRest.ID_GENERIC_PATH)
    public List<Mensaje> getMensajesByCarpeta(@PathParam(ConstantesLoginRest.ID) String id, @QueryParam(ConstantesLoginRest.PASSWORD) String password) {
        return servicios.getMensajesByUsuario(id, password);
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
    @Path(ConstantesLoginRest.ID_GENERIC_PATH)
    public void deleteMensaje(@PathParam(ConstantesLoginRest.ID) String id) {
        servicios.deleteMensaje(id);
    }

}