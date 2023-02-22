package jakarta.rest;

import domain.modelo.Article;
import domain.modelo.QueryArticlesAndNews;
import domain.modelo.QueryDescAndReaders;
import domain.servicios.impl.ArticleServicesImpl;
import jakarta.inject.Inject;
import jakarta.rest.common.RestConstants;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path(RestConstants.PATH_ARTICLES)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RestArticles {

    ArticleServicesImpl articleServices;

    @Inject
    public RestArticles(ArticleServicesImpl articleServices) {
        this.articleServices = articleServices;
    }


    @GET
    public List<Article> getAllArticles() {
        return articleServices.getAllArticles();
    }

    @GET
    @Path(RestConstants.PATH_NEWS + RestConstants.PATH_PARAM_ID)
    public List<Article> getArticlesByNewspaper(@PathParam(RestConstants.ID) String idNewspaper) {
        return articleServices.getArticlesByNewspaper(idNewspaper);
    }

    @GET
    @Path(RestConstants.PATH_TYPE + RestConstants.PATH_PARAM_ID)
    public List<Article> getArticlesByType(@PathParam(RestConstants.ID) String idType) {
        return articleServices.getArticlesByType(idType);
    }

    @GET
    @Path(RestConstants.DESCANDREADERS + RestConstants.PATH_PARAM_ID)
    public QueryDescAndReaders getDescAndNumberOfReaders(@PathParam(RestConstants.ID) String artID) {
        return articleServices.getQuery(artID);
    }

    @POST
    public Response create(Article article) {
        return Response.status(Response.Status.CREATED).entity(articleServices.addArticle(article)).build();
    }

    @GET
    @Path(RestConstants.NAMEARTANDNEWS + RestConstants.PATH_PARAM_ID)
    public List<QueryArticlesAndNews> getNamesByType(@PathParam(RestConstants.ID) String idType) {
        return articleServices.getAllQuerySpring(idType);
    }
}
