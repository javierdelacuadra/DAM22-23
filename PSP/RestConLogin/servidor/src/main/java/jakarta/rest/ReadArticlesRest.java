package jakarta.rest;

import domain.servicios.ServiciosReadArticles;
import jakarta.common.ConstantesREST;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path(ConstantesREST.READARTICLES_PATH)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ReadArticlesRest {

    private final ServiciosReadArticles servicios;

    @Inject
    public ReadArticlesRest(ServiciosReadArticles servicios) {
        this.servicios = servicios;
    }

    @POST
    public Response saveReadArticle(@QueryParam(ConstantesREST.ID_ARTICLE) String idArticle,
                                    @QueryParam(ConstantesREST.ID_READER) String idReader,
                                    @QueryParam(ConstantesREST.RATING) String rating) {
        if (servicios.saveReadArticle(idArticle, idReader, rating)) {
            return Response.status(Response.Status.CREATED).build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
}