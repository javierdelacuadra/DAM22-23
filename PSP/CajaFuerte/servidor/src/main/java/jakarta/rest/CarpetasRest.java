package jakarta.rest;

import domain.servicios.ServiciosCarpetas;
import jakarta.inject.Inject;
import jakarta.rest.common.ConstantesLoginRest;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import modelo.Carpeta;

import java.util.List;

@Path(ConstantesLoginRest.CARPETAS_PATH)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CarpetasRest {

    private final ServiciosCarpetas servicios;

    @Inject
    public CarpetasRest(ServiciosCarpetas servicios) {
        this.servicios = servicios;
    }

    @GET
    @Path(ConstantesLoginRest.ID_GENERIC_PATH)
    public List<Carpeta> getCarpetasByUsuario(@PathParam(ConstantesLoginRest.ID) String id) {
        return servicios.getCarpetasByUsuario(id);
    }

    @POST
    public Carpeta addCarpeta(Carpeta carpeta) {
        return servicios.addCarpeta(carpeta);
    }

    @GET
    public Carpeta cargarCarpetaCompartida(@QueryParam(ConstantesLoginRest.NOMBRE_CARPETA) String nombreCarpeta, @QueryParam(ConstantesLoginRest.NOMBRE_USUARIO) String nombreUsuario, @QueryParam(ConstantesLoginRest.PASSWORD_CARPETA) String passwordCarpeta) {
        return servicios.cargarCarpetaCompartida(nombreCarpeta, nombreUsuario, passwordCarpeta);
    }

    @PUT
    public Carpeta updateCarpeta(Carpeta carpeta) {
        return servicios.updateCarpeta(carpeta);
    }
}
