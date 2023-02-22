package org.servidor.jakarta.rest;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import org.servidor.domain.servicios.impl.ServiciosMessageImpl;
import org.servidor.jakarta.common.RestConstants;
import org.utils.common.AplicationConstants;
import org.utils.domain.modelo.Message;

@Path(AplicationConstants.PATH_MESSAGES)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RolesAllowed({RestConstants.ROLE_ADMIN, RestConstants.ROLE_USER})
public class RestMessages {

    ServiciosMessageImpl messageServices;

    @Context
    SecurityContext securityContext;

    @Inject
    public RestMessages(ServiciosMessageImpl messageServices) {
        this.messageServices = messageServices;
    }

    @POST
    public Response addMessage(Message message) {
        return Response.status(Response.Status.CREATED).entity(messageServices.add(message, securityContext.getUserPrincipal().getName())).build();
    }

    @PUT
    public Message updateMessage(Message message) {
        return messageServices.update(message);
    }

    @DELETE
    @Path(AplicationConstants.PATH_PARAM_ID)
    public Response deleteMessage(@PathParam(RestConstants.ID) String id) {
        if (messageServices.delete(id, securityContext.getUserPrincipal().getName())) {
            return Response.status(Response.Status.NO_CONTENT).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

}
