package jakarta.rest;

import domain.servicios.ServiciosInformes;
import domain.servicios.ServiciosUsers;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.SecurityContext;
import modelo.Informe;
import modelo.User;

import java.util.List;

@Path("informes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class InformesREST {

    private final ServiciosInformes servicios;
    private final ServiciosUsers serviciosUsers;

    @Context
    public SecurityContext securityContext;

    @Inject
    public InformesREST(ServiciosInformes servicios, ServiciosUsers serviciosUsers) {
        this.servicios = servicios;
        this.serviciosUsers = serviciosUsers;
    }

    @GET
    @RolesAllowed({"NIVEL1", "NIVEL2"})
    public List<Informe> getInformes() {
        User usuario = serviciosUsers.getUserByName(securityContext.getUserPrincipal().getName());
        List<String> roles = usuario.getRoles();
        return servicios.getInformes(roles);
    }

    @POST
    @RolesAllowed("ESPIA")
    public Informe addInforme(Informe informe) {
        servicios.addInforme(informe);
        return informe;
    }

    //get informe by id
    @GET
    @Path("/{nombre}")
    @RolesAllowed({"NIVEL1", "NIVEL2"})
    public Informe getInforme(@PathParam("nombre") String nombre) {
        User usuario = serviciosUsers.getUserByName(securityContext.getUserPrincipal().getName());
        List<String> roles = usuario.getRoles();
        return servicios.getInforme(nombre, roles);
    }
}