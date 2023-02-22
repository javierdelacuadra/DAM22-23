package org.server.jakarta;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.server.domain.servicios.impl.PersonasServicesImpl;
import org.server.jakarta.common.RestConst;
import org.utils.domain.modelo.ApiError;
import org.utils.domain.modelo.Persona;

import java.time.LocalDateTime;
import java.util.List;

@Path(RestConst.PATH_PERS)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RestPersonas {

    private final PersonasServicesImpl personasServices;

    @Inject
    public RestPersonas(PersonasServicesImpl personasServices) {
        this.personasServices = personasServices;
    }

    @GET
    public List<Persona> getAllPersonas() {
        return personasServices.getAll();
    }

    @GET
    @Path(RestConst.PATH_PARAM_ID)
    public Persona getPersona(@PathParam(RestConst.ID) String id) {
        return personasServices.getPersona(id);
    }

    @POST
    public Response create(Persona persona) {
        return Response.status(Response.Status.CREATED).entity(personasServices.addPersona(persona)).build();
    }

    @PUT
    public Persona update(Persona persona) {
        return personasServices.updatePersona(persona);
    }

    @DELETE
    @Path(RestConst.PATH_PARAM_ID)
    public Response delete(@PathParam(RestConst.ID) String id) {
        if (personasServices.deletePersona(id)) {
            return Response.status(Response.Status.NO_CONTENT).build();
        } else {
            ApiError apiError = new ApiError(RestConst.NO_SE_ENCONTRO_LA_PERSONA_CON_EL_ID + id, LocalDateTime.now());
            return Response.status(Response.Status.NOT_FOUND).entity(apiError).build();
        }
    }


    @GET
    @Path(RestConst.MENORES)
    public List<Persona> getPersona() {
        return personasServices.getMenores();
    }
}
