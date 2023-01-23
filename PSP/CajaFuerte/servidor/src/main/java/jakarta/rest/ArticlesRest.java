package jakarta.rest;

import domain.servicios.ServiciosArticles;
import jakarta.common.ConstantesREST;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import modelo.Article;

import java.util.List;

@Path(ConstantesREST.ARTICLES_PATH)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ArticlesRest {

    private final ServiciosArticles servicios;

    @Inject
    public ArticlesRest(ServiciosArticles servicios) {
        this.servicios = servicios;
    }

    @GET
    public List<Article> getAllArticles() {
        return servicios.getAllArticles();
    }

    @POST
    public Response addArticle(Article article) {
        if (servicios.addArticle(article)) {
            return Response.status(Response.Status.CREATED).build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @PUT
    public Response updateArticle(Article article) {
        if (servicios.updateArticle(article)) {
            return Response.status(Response.Status.OK).build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @DELETE
    @Path(ConstantesREST.PATH_ID)
    public Response deleteArticle(@PathParam(ConstantesREST.ID) String id) {
        if (servicios.deleteArticle(id)) {
            return Response.status(Response.Status.OK).build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @GET
    @Path(ConstantesREST.PATH_ID)
    public Article getArticle(@PathParam(ConstantesREST.ID) String id) {
        return servicios.getArticle(id);
    }

    @GET
    @Path(ConstantesREST.TYPE_PATH)
    public List<Article> getArticlesByType(@PathParam(ConstantesREST.TYPE) String type) {
        return servicios.getArticlesByType(type);
    }

}