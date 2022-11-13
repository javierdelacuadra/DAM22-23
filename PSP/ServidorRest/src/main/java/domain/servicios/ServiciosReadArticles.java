package domain.servicios;

import dao.DaoReadArticles;
import jakarta.inject.Inject;

public class ServiciosReadArticles {

    private final DaoReadArticles dao;

    @Inject
    public ServiciosReadArticles(DaoReadArticles dao) {
        this.dao = dao;
    }

    public boolean saveReadArticle(String idArticle, String idReader, String rating) {
        return dao.saveReadArticle(idArticle, idReader, rating);
    }
}
