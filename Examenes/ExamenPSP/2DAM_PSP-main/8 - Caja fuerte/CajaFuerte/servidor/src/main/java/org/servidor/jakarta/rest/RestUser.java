package org.servidor.jakarta.rest;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import org.servidor.domain.servicios.impl.ServiciosUserImpl;
import org.servidor.jakarta.common.RestConstants;
import org.utils.common.AplicationConstants;
import org.utils.domain.modelo.ApiError;
import org.utils.domain.modelo.User;

import java.time.LocalDateTime;

@Path(AplicationConstants.PATH_USERS)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RestUser {

    private final ServiciosUserImpl userServices;

    @Context
    SecurityContext securityContext;

    @Inject
    public RestUser(ServiciosUserImpl userServices) {
        this.userServices = userServices;
    }

    @POST
    @RolesAllowed(RestConstants.ROLE_ADMIN)
    public Response createReader(User user) {
        return Response.status(Response.Status.CREATED).entity(userServices.add(user)).build();
    }

    @GET
    @Path(AplicationConstants.PATH_LOGIN)
    @RolesAllowed({RestConstants.ROLE_ADMIN, RestConstants.ROLE_USER})
    public Response login() {
        if (securityContext.getUserPrincipal() != null) {
            return Response.status(Response.Status.NO_CONTENT).build();
        }
        return Response.status(Response.Status.FORBIDDEN).entity(new ApiError(RestConstants.LOGIN_INVALIDO, LocalDateTime.now())).build();
    }

}
