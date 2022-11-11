package jakarta.rest;

import dao.modelo.Article;
import domain.servicios.ServiciosArticles;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/articles")
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
    @Path("/{id}")
    public Response deleteArticle(@PathParam("id") String id) {
        if (servicios.deleteArticle(id)) {
            return Response.status(Response.Status.OK).build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @GET
    @Path("/{id}")
    public Article getArticle(@PathParam("id") String id) {
        return servicios.getArticle(id);
    }


}