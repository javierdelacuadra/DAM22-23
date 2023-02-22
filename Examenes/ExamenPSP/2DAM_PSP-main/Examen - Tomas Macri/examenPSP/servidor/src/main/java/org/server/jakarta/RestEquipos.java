package org.server.jakarta;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.server.domain.servicios.ServiciosEquipos;
import org.utils.domain.modelo.Equipo;
import org.utils.domain.modelo.Jugador;

import java.util.List;

@Path("/teams")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RestEquipos {

    private final ServiciosEquipos serviciosEquipos;

    @Inject
    public RestEquipos(ServiciosEquipos serviciosEquipos){this.serviciosEquipos = serviciosEquipos;}


    @GET
    public List<Equipo> getAll(){
        return serviciosEquipos.getAll();
    }


    @GET
    @Path("/simple")
    public List<Equipo> getAllSinJugadores(){
        return serviciosEquipos.getAllSinJugadores();
    }

    @GET
    @Path("/{nombre}")
    public Equipo getEquipo(@PathParam("nombre") String nombre){
        return serviciosEquipos.getEquipo(nombre);
    }

    @GET
    @Path("/{nombre}/jugs")
    public List<Jugador> getJugadoresPorEquipo(@PathParam("nombre") String nombre){
        return serviciosEquipos.getJugadoresPorEquipo(nombre);
    }

    @POST
    @Path("/{equipo}")
    public Response create(@PathParam("equipo") String equipo, Jugador jugador) {
        return Response.status(Response.Status.CREATED).entity(serviciosEquipos.addPlayer(equipo, jugador)).build();
    }

    @POST
    public Response createTeam(Equipo equipo){
        return Response.status(Response.Status.CREATED).entity(serviciosEquipos.addTeam(equipo)).build();
    }

    @PUT
    @Path("/{equipo}")
    public Jugador editPlayer(@PathParam("equipo") String nEquipo, Jugador jugador){
        return serviciosEquipos.editPlayer(nEquipo, jugador);
    }

    @DELETE
    @Path("/{nombre}")
    public Equipo deleteTeam(@PathParam("nombre") String nombre){
        return serviciosEquipos.deleteTeam(nombre);
    }

    @DELETE
    @Path("/jugs/{id}")
    public Jugador deletePlayer(@PathParam("id") String nombre){
        return serviciosEquipos.deletePlayer(nombre);
    }



}
