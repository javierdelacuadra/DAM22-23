package servicios;

import data.DaoArticles;
import jakarta.inject.Inject;
import modelo.Article;

import java.util.List;

public class ServicesArticles {
    private final DaoArticles daoArticles;

    @Inject
    public ServicesArticles(DaoArticles daoArticles) {
        this.daoArticles = daoArticles;
    }

    public List<Article> getArticles() {
        return daoArticles.getArticles();
    }

    public boolean addArticle(Article article) {
        return daoArticles.saveArticle(article);
    }
}
