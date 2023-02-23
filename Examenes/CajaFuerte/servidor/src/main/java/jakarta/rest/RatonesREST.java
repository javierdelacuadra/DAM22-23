package jakarta.rest;

import domain.servicios.ServiciosRatones;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import modelo.Raton;

import java.util.List;

@Path("ratones")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RatonesREST {
    private final ServiciosRatones servicios;

    @Inject
    public RatonesREST(ServiciosRatones servicios) {
        this.servicios = servicios;
    }

    @GET
    @RolesAllowed({"RATONES", "BIOLOGO"})
    public List<Raton> getRatones() {
        return servicios.getRatones();
    }

    @POST
    @RolesAllowed("BIOLOGO")
    public Raton addRaton(Raton raton) {
        servicios.addRaton(raton);
        return raton;
    }
}
