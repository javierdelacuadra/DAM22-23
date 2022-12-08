package domain.servicios;

import dao.DaoQueries;
import jakarta.inject.Inject;
import model.Query1;
import model.Query2;
import model.Query3;
import model.Reader;

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