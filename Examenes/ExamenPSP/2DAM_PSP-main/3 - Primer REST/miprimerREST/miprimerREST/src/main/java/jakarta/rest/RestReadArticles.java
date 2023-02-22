package jakarta.rest;

import domain.modelo.QueryBadRatedArticles;
import domain.modelo.ReadArticle;
import domain.modelo.errores.InvalidFieldsException;
import domain.servicios.impl.ReadArticleServicesImpl;
import jakarta.inject.Inject;
import jakarta.rest.common.RestConstants;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path(RestConstants.PATH_READARTS)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RestReadArticles {

    private final ReadArticleServicesImpl readArticleServices;

    @Inject
    public RestReadArticles(ReadArticleServicesImpl readArticleServices) {
        this.readArticleServices = readArticleServices;
    }

    @GET
    @Path(RestConstants.PATH_PARAM_ID)
    public int getRatingOfReadArticle(@PathParam(RestConstants.ID) String idReader, @QueryParam(RestConstants.ART_ID) String artID) {
        if (artID != null) {
            return readArticleServices.getRating(idReader, artID);
        }
        throw new InvalidFieldsException(RestConstants.QUERY_PARAM_INVALIDO + RestConstants.EL_PARAMETRO_ART_ID_DEBE_SER_EL_ID_DEL_ARTICULO_QUE_HA_LEIDO_EL_LECTOR + idReader);
    }

    @GET
    @Path(RestConstants.PATH_NEWS + RestConstants.PATH_PARAM_ID)
    public List<QueryBadRatedArticles> getBadRatedArticlesByNewspaper(@PathParam(RestConstants.ID) String idNews) {
        return readArticleServices.getAllDifficultSpringQuery(idNews);
    }

    @POST
    public Response create(ReadArticle readArticle) {
        return Response.status(Response.Status.CREATED).entity(readArticleServices.add(readArticle)).build();
    }

    @PUT
    public ReadArticle update(ReadArticle readArticle) {
        return readArticleServices.update(readArticle);
    }

    @GET
    public List<ReadArticle> getAll() {
        return readArticleServices.getAll();
    }
}
