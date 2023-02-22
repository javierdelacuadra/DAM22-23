package jakarta.rest;

import domain.servicios.impl.NewspapersServicesImpl;
import jakarta.annotation.security.RolesAllowed;
import jakarta.common.RestConstants;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.miutils.common.AplicationConstants;
import org.miutils.domain.modelo.ApiError;
import org.miutils.domain.modelo.Newspaper;

import java.time.LocalDateTime;
import java.util.List;

@Path(RestConstants.PATH_NEWSPAPERS)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RestNewspapers {

    NewspapersServicesImpl newspapersServices;

    @Inject
    public RestNewspapers(NewspapersServicesImpl newspapersServices) {
        this.newspapersServices = newspapersServices;
    }

    @GET
    public List<Newspaper> getAllNewspapers() {
        return newspapersServices.getAll();
    }

    @GET
    @RolesAllowed(RestConstants.ROLE_ADMIN)
    @Path(AplicationConstants.PATH_PARAM_ID)
    public Newspaper get(@PathParam(AplicationConstants.ID) String id) {
        return newspapersServices.get(id);
    }

    @POST
    @RolesAllowed(RestConstants.ROLE_ADMIN)
    public Response create(Newspaper newspaper) {
        return Response.status(Response.Status.CREATED).entity(newspapersServices.add(newspaper)).build();
    }

    @PUT
    @RolesAllowed(RestConstants.ROLE_ADMIN)
    public Newspaper update(Newspaper newspaper) {
        return newspapersServices.update(newspaper);
    }

    @DELETE
    @RolesAllowed(RestConstants.ROLE_ADMIN)
    @Path(AplicationConstants.PATH_PARAM_ID)
    public Response delete(@PathParam(AplicationConstants.ID) String id) {
            if (newspapersServices.delete(id)) {
                return Response.status(Response.Status.NO_CONTENT).build();
            } else {
                ApiError apiError = new ApiError(RestConstants.NO_SE_ENCONTRO_EL_NEWSPAPER_CON_EL_ID + id, LocalDateTime.now());
                return Response.status(Response.Status.NOT_FOUND).entity(apiError)
                        .type(MediaType.APPLICATION_JSON).build();
            }
    }
}
