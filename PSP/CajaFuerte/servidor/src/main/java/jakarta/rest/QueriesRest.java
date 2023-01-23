package jakarta.rest;

import domain.servicios.ServiciosQueries;
import jakarta.common.ConstantesREST;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import modelo.Query1;
import modelo.Query2;
import modelo.Query3;
import modelo.Reader;

import java.util.List;

@Path(ConstantesREST.QUERIES_PATH)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class QueriesRest {

    private final ServiciosQueries servicios;

    @Inject
    public QueriesRest(ServiciosQueries servicios) {
        this.servicios = servicios;
    }

    @GET
    @Path(ConstantesREST.QUERY_1_PATH)
    public List<Query1> getArticlesQuery() {
        return servicios.getArticlesQuery();
    }

    @GET
    @Path(ConstantesREST.QUERY_2_PATH)
    public List<Reader> getOldestSubscribers() {
        return servicios.getOldestSubscribers();
    }

    @GET
    @Path(ConstantesREST.QUERY_3_PATH)
    public List<Query2> getArticlesByTypeAndNameNewspaper(@QueryParam(ConstantesREST.TYPE) String type) {
        return servicios.getArticlesByTypeAndNameNewspaper(type);
    }

    @GET
    @Path(ConstantesREST.QUERY_4_PATH)
    public List<Query3> getArticlesByNewspaperWithBadRatings(@QueryParam(ConstantesREST.ID_NEWSPAPER) String idNewspaper) {
        return servicios.getArticlesByNewspaperWithBadRatings(idNewspaper);
    }
}