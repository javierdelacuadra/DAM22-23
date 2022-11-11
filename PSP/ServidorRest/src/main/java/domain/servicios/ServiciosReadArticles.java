package domain.servicios;

import dao.DaoReadArticles;
import dao.modelo.Article;
import jakarta.inject.Inject;

public class ServiciosReadArticles {

    private final DaoReadArticles dao;

    @Inject
    public ServiciosReadArticles(DaoReadArticles dao) {
        this.dao = dao;
    }

    public boolean saveReadArticle(Article article, Integer rating) {
        return dao.saveReadArticle(article, rating);
    }
}
