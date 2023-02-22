package jakarta.rest;

import domain.servicios.impl.LoginServicesImpl;
import domain.servicios.impl.ReaderServicesImpl;
import jakarta.annotation.security.RolesAllowed;
import jakarta.common.RestConstants;
import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.miutils.common.AplicationConstants;
import org.miutils.domain.modelo.ApiError;
import org.miutils.domain.modelo.Reader;

import java.time.LocalDateTime;
import java.util.List;

@Path(RestConstants.PATH_READERS)
@RolesAllowed({RestConstants.ROLE_ADMIN, RestConstants.ROLE_READER})
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RestReaders {
    @Context
    HttpServletRequest request;
    ReaderServicesImpl readerServices;

    LoginServicesImpl loginServices;

    @Inject
    public RestReaders(ReaderServicesImpl readerServices, LoginServicesImpl loginServices) {
        this.readerServices = readerServices;
        this.loginServices = loginServices;
    }

    @GET
    public List<Reader> getAllReaders()  {
        return readerServices.getAll();
    }

    @GET
    @Path(AplicationConstants.PATH_PARAM_ID)
    public Reader getUnReader(@PathParam(AplicationConstants.ID) String id) {
        return readerServices.get(id);
    }

    @POST
    public Response createReader(Reader reader) {
        return Response.status(Response.Status.CREATED).entity(loginServices.register(reader)).build();
    }

    @PUT
    @Path(AplicationConstants.PATH_UPDATE)
    public Reader updateReader(Reader reader) {
        return readerServices.update(reader);
    }

    @DELETE
    @Path(AplicationConstants.PATH_PARAM_ID)
    public Response deleteReader(@PathParam(AplicationConstants.ID) String id) {
        if (readerServices.delete(id))
            return Response.status(Response.Status.NO_CONTENT).build();
        else
            return Response.status(Response.Status.NOT_FOUND).entity(new ApiError(RestConstants.NO_SE_ENCONTRO_EL_LECTOR_CON_EL_ID + id, LocalDateTime.now())).build();
    }


    @GET
    @Path(AplicationConstants.PATH_NEWS + AplicationConstants.PATH_PARAM_ID + AplicationConstants.PATH_OLDEST)
    public List<String> getOldestSuscribersNames(@PathParam(AplicationConstants.ID) String id) {
        return readerServices.getOldestReaders(id);
    }
    @GET
    @Path(AplicationConstants.PATH_TIPO + AplicationConstants.PATH_PARAM_TIPO)
    public List<Reader> getReadersByType(@PathParam(AplicationConstants.TIPO) String tipo) {
        return readerServices.getReadersByType(tipo);
    }

    @GET
    @Path(AplicationConstants.PATH_NEWS + AplicationConstants.PATH_PARAM_ID)
    public List<Reader> getReadersByNewspapers(@PathParam(AplicationConstants.ID) String id) {
        return readerServices.getReadersByNewspaper(id);
    }
}
