package domain.servicios;

import dao.DaoQueries;
import jakarta.inject.Inject;
import modelo.Query1;
import modelo.Query2;
import modelo.Query3;
import modelo.Reader;

import java.util.List;

public class ServiciosQueries {

    private final DaoQueries dao;

    @Inject
    public ServiciosQueries(DaoQueries dao) {
        this.dao = dao;
    }

    public List<Query1> getArticlesQuery() {
        return dao.getArticlesQuery();
    }

    public List<Reader> getOldestSubscribers() {
        return dao.getOldestSubscribers();
    }

    public List<Query2> getArticlesByTypeAndNameNewspaper(String type) {
        return dao.getArticlesByTypeAndNameNewspaper(type);
    }

    public List<Query3> getArticlesByNewspaperWithBadRatings(String idNewspaper) {
        return dao.getArticlesByNewspaperWithBadRatings(idNewspaper);
    }
}