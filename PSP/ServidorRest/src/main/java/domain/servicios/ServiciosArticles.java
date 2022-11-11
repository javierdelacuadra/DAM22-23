package domain.servicios;

import dao.DaoArticles;
import dao.modelo.Article;
import jakarta.inject.Inject;

import java.util.List;

public class ServiciosArticles {

    private final DaoArticles dao;

    @Inject
    public ServiciosArticles(DaoArticles dao) {
        this.dao = dao;
    }

    public List<Article> getAllArticles() {
        return dao.getAll();
    }

    public boolean addArticle(Article article) {
        return dao.save(article);
    }

    public boolean updateArticle(Article article) {
        return dao.update(article);
    }

    public boolean deleteArticle(String id) {
        return dao.delete(id);
    }

    public Article getArticle(String id) {
        return dao.get(id);
    }
}
