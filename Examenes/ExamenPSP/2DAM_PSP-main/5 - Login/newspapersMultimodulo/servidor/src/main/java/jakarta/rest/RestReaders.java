package jakarta.rest;

import domain.modelo.errores.NotFoundException;
import domain.servicios.impl.ReaderServicesImpl;
import jakarta.common.RestConstants;
import jakarta.filtros.LoggedIn;
import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.miutils.domain.modelo.Reader;

import java.util.List;

@Path(RestConstants.PATH_READERS)
@LoggedIn
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
//@LoggedIn
public class RestReaders {
    @Context
    HttpServletRequest request;
    ReaderServicesImpl readerServices;

    @Inject
    public RestReaders(ReaderServicesImpl readerServices) {
        this.readerServices = readerServices;
    }

    @GET
    public List<Reader> getAllReaders()  {
        return readerServices.getAll();
    }

    @GET
    @Path(RestConstants.PATH_PARAM_ID)
    public Reader getUnReader(@PathParam(RestConstants.ID) String id) {
        return readerServices.get(id);
    }

    @POST
    public Response createReader(Reader reader) {
        return Response.status(Response.Status.CREATED).entity(readerServices.add(reader)).build();
    }

    @PUT
    @Path(RestConstants.PATH_UPDATE)
    public Reader updateReader(Reader reader) {
        return readerServices.update(reader);
    }

    @DELETE
    @Path(RestConstants.PATH_PARAM_ID)
    public Response deleteReader(@PathParam(RestConstants.ID) String id) {
        if (readerServices.delete(id))
            return Response.status(Response.Status.NO_CONTENT).build();
        else
            throw new NotFoundException(RestConstants.NO_SE_ENCONTRO_EL_LECTOR_CON_EL_ID + id);
    }


    @GET
    @Path(RestConstants.PATH_NEWS + RestConstants.PATH_PARAM_ID + RestConstants.PATH_OLDEST)
    public List<String> getOldestSuscribersNames(@PathParam(RestConstants.ID) String id) {
        return readerServices.getOldestReaders(id);
    }
    @GET
    @Path(RestConstants.PATH_TIPO + RestConstants.PATH_PARAM_TIPO)
    public List<Reader> getReadersByType(@PathParam(RestConstants.TIPO) String tipo) {
        return readerServices.getReadersByType(tipo);
    }

    @GET
    @Path(RestConstants.PATH_NEWS + RestConstants.PATH_PARAM_ID)
    public List<Reader> getReadersByNewspapers(@PathParam(RestConstants.ID) String id) {
        return readerServices.getReadersByNewspaper(id);
    }
}
