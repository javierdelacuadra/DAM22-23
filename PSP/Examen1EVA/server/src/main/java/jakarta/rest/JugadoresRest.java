package jakarta.rest;

import domain.servicios.ServiciosJugadores;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import modelo.Jugador;

import java.util.List;

@Path("/jugadores")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class JugadoresRest {

    private final ServiciosJugadores servicios;

    @Inject
    public JugadoresRest(ServiciosJugadores servicios) {
        this.servicios = servicios;
    }

    @GET
    @Path("/{nombre}")
    public List<Jugador> verJugadoresDeUnEquipo(@PathParam("nombre") String nombre) {
        return servicios.verJugadoresDeUnEquipo(nombre);
    }

    @POST
    public Response addJugador(Jugador jugador) {
        return Response.status(Response.Status.CREATED)
                .entity(servicios.addJugador(jugador))
                .build();
    }

    @PUT
    public Response updateJugador(Jugador jugador) {
        return Response.status(Response.Status.OK)
                .entity(servicios.updateJugador(jugador))
                .build();
    }

    @DELETE
    @Path("/{nombre}")
    public Response deleteJugador(@PathParam("nombre") String nombre) {
        return Response.status(Response.Status.OK)
                .entity(servicios.borrarJugador(nombre))
                .build();
    }
}
