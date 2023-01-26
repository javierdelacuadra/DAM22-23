package jakarta.rest;

import domain.servicios.ServiciosUsuarios;
import jakarta.inject.Inject;
import jakarta.rest.common.ConstantesLoginRest;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import modelo.Usuario;

import java.util.List;

@Path(ConstantesLoginRest.USUARIOS_PATH)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UsuariosRest {

    private final ServiciosUsuarios servicios;

    @Inject
    public UsuariosRest(ServiciosUsuarios servicios) {
        this.servicios = servicios;
    }

    @GET
    public List<Usuario> getAllUsers() {
        return servicios.getAllUsers();
    }

    @POST
    public Response addUsuario(Usuario usuario) {
        return Response.status(Response.Status.CREATED)
                .entity(servicios.addUsuario(usuario))
                .build();
    }
}