package org.servidor.jakarta.rest;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.servidor.domain.servicios.impl.ServiciosFolderImpl;
import org.servidor.jakarta.common.RestConstants;
import org.utils.common.AplicationConstants;
import org.utils.domain.modelo.Folder;

import java.util.List;

@Path(AplicationConstants.PATH_FOLDERS)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RolesAllowed({RestConstants.ROLE_ADMIN, RestConstants.ROLE_USER})
public class RestFolder {

    private final ServiciosFolderImpl folderServices;

    @Inject
    public RestFolder(ServiciosFolderImpl folderServices) {
        this.folderServices = folderServices;
    }

    @GET
    public Folder getFolder(@QueryParam(AplicationConstants.USER_NAME) String userName, @QueryParam(AplicationConstants.NAME_FOLDER) String nameFolder) {
        return folderServices.getFolder(userName, nameFolder);
    }

    @PUT
    @Path(AplicationConstants.PATH_CHANGE_PASSWORD)
    public Folder changePassword(@QueryParam(AplicationConstants.USER_NAME) String userName, @QueryParam(AplicationConstants.NAME_FOLDER) String nameFolder, @QueryParam(AplicationConstants.PASSWORD) String password, @QueryParam(AplicationConstants.NEW_PASSWORD) String newPassword) {
        return folderServices.changePassword(userName, nameFolder, password, newPassword);
    }
    @POST
    @Path(RestConstants.ADD)
    public Response addFolder(Folder folder) {
        return Response.status(Response.Status.CREATED).entity(folderServices.add(folder)).build();
    }

    @GET
    @Path(AplicationConstants.PATH_PARAM_USER_NAME)
    public List<Folder> getAll(@PathParam(RestConstants.USER_NAME) String userName) {
        return folderServices.getAll(userName);
    }
}
