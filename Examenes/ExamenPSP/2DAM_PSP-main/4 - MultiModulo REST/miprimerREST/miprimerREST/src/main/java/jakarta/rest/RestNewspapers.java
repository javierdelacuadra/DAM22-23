package jakarta.rest;

import domain.modelo.Newspaper;
import domain.modelo.errores.InvalidFieldsException;
import domain.modelo.errores.NotFoundException;
import domain.servicios.impl.NewspapersServicesImpl;
import jakarta.inject.Inject;
import jakarta.rest.common.RestConstants;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

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
    @Path(RestConstants.PATH_PARAM_ID)
    public Newspaper get(@PathParam(RestConstants.ID) String id) {
        return newspapersServices.get(id);
    }

    @POST
    public Response create(Newspaper newspaper) {
        return Response.status(Response.Status.CREATED).entity(newspapersServices.add(newspaper)).build();
    }

    @PUT
    @Path(RestConstants.PATH_UPDATE)
    public Newspaper update(Newspaper newspaper) {
        return newspapersServices.update(newspaper);
    }

    @DELETE
    @Path(RestConstants.PATH_PARAM_ID)
    public Response delete(@PathParam(RestConstants.ID) String id) {
        if (newspapersServices.delete(id))
            return Response.status(Response.Status.NO_CONTENT).build();
        else
            throw new NotFoundException(RestConstants.NO_SE_ENCONTRO_EL_NEWSPAPER_CON_EL_ID + id);
    }

    @GET
    @Path(RestConstants.PATH_SUSCRIPCIONES + RestConstants.PATH_PARAM_ID)
    public List<Newspaper> getAllNewsReaderSuscribed(@PathParam(RestConstants.ID) String idReader, @QueryParam(RestConstants.SUSC) String susc) {
        if (susc != null) {
            if (susc.equalsIgnoreCase(RestConstants.TRUE))
                return newspapersServices.getAllSuscribed(idReader);
            if (susc.equalsIgnoreCase(RestConstants.FALSE))
                return newspapersServices.getAllUnsuscribed(idReader);
        }
        throw new InvalidFieldsException(RestConstants.QUERY_PARAM_INVALIDO + RestConstants.EL_VALOR_DE_SUSC_DEBE_SER_TRUE_O_FALSE);
    }

}
