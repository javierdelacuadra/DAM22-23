package jakarta.rest;

import dao.modelo.Query1;
import dao.modelo.Query2;
import dao.modelo.Query3;
import dao.modelo.Reader;
import domain.servicios.ServiciosQueries;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.util.List;

@Path("/queries")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class QueriesRest {

    private final ServiciosQueries servicios;

    @Inject
    public QueriesRest(ServiciosQueries servicios) {
        this.servicios = servicios;
    }

    @GET
    @Path("/query1")
    public List<Query1> getArticlesQuery() {
        return servicios.getArticlesQuery();
    }

    @GET
    @Path("/query2")
    public List<Reader> getOldestSubscribers() {
        return servicios.getOldestSubscribers();
    }

    @GET
    @Path("/query3")
    public List<Query2> getArticlesByTypeAndNameNewspaper(@QueryParam("type") String type) {
        return servicios.getArticlesByTypeAndNameNewspaper(type);
    }

    @GET
    @Path("/query4")
    public List<Query3> getArticlesByNewspaperWithBadRatings(@QueryParam("idNewspaper") String idNewspaper) {
        return servicios.getArticlesByNewspaperWithBadRatings(idNewspaper);
    }
}