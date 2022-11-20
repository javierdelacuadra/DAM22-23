package domain.servicios;

import dao.DaoArticles;
import jakarta.inject.Inject;
import modelo.Article;

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

    public List<Article> getArticlesByType(String type) {
        return dao.getArticlesByType(type);
    }
}
