package data.impl;

import jakarta.inject.Inject;
import modelo.Article;

import java.util.List;

public class DaoArticles {
    private final Database db;

    @Inject
    public DaoArticles(Database db) {
        this.db = db;
    }

    public boolean addArticle() {
        List<Article> articles = db.loadArticles();
        articles.add(new Article());
        return db.saveArticles(articles);
    }
}