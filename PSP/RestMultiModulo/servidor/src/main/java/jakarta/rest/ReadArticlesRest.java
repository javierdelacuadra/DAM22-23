package jakarta.rest;

import domain.servicios.ServiciosReadArticles;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/readarticles")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ReadArticlesRest {

    private final ServiciosReadArticles servicios;

    @Inject
    public ReadArticlesRest(ServiciosReadArticles servicios) {
        this.servicios = servicios;
    }

    @POST
    public Response saveReadArticle(@QueryParam("idArticle") String idArticle, @QueryParam("idReader") String idReader, @QueryParam("rating") String rating) {
        if (servicios.saveReadArticle(idArticle, idReader, rating)) {
            return Response.status(Response.Status.CREATED).build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
}