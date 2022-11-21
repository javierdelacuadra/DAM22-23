package jakarta.rest;

import domain.servicios.ServiciosJugadores;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import modelo.Equipo;

import java.util.List;

@Path("/equipos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EquiposRest {

    private final ServiciosJugadores servicios;

    @Inject
    public EquiposRest(ServiciosJugadores servicios) {
        this.servicios = servicios;
    }

    @GET
    public List<Equipo> verTodosLosEquipos() {
        return servicios.verTodosLosEquipos();
    }

    @GET
    @Path("/noPlayers")
    public List<Equipo> verTodosLosEquiposSinJugadores() {
        return servicios.verTodosLosEquiposSinJugadores();
    }

    @GET
    @Path("/{nombre}")
    public Equipo verUnEquipo(@PathParam("nombre") String nombre) {
        return servicios.verUnEquipo(nombre);
    }

    @POST
    public Response addEquipo(Equipo equipo) {
        return Response.status(Response.Status.CREATED)
                .entity(servicios.addEquipo(equipo))
                .build();
    }

    @DELETE
    @Path("/{nombre}")
    public Response deleteEquipo(@PathParam("nombre") String nombre) {
        if (servicios.borrarEquipo(nombre)) {
            return Response.status(Response.Status.OK).build();
        } else {
            return Response.status(Response.Status.CONFLICT).build();
        }
    }
}