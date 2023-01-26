package jakarta.rest;

import domain.servicios.ServiciosCarpetas;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import modelo.Carpeta;

import java.util.List;

@Path("carpetas")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CarpetasRest {

    private final ServiciosCarpetas servicios;

    @Inject
    public CarpetasRest(ServiciosCarpetas servicios) {
        this.servicios = servicios;
    }

    @GET
    @Path("/{id}")
    public List<Carpeta> getCarpetasByUsuario(@PathParam("id") String id) {
        return servicios.getCarpetasByUsuario(id);
    }

    @POST
    public Carpeta addCarpeta(Carpeta carpeta) {
        return servicios.addCarpeta(carpeta);
    }

    @GET
    public Carpeta cargarCarpetaCompartida(@QueryParam("nombreCarpeta") String nombreCarpeta, @QueryParam("nombreUsuario") String nombreUsuario, @QueryParam("passwordCarpeta") String passwordCarpeta) {
        return servicios.cargarCarpetaCompartida(nombreCarpeta, nombreUsuario, passwordCarpeta);
    }

    @PUT
    public Carpeta updateCarpeta(Carpeta carpeta) {
        return servicios.updateCarpeta(carpeta);
    }
}
