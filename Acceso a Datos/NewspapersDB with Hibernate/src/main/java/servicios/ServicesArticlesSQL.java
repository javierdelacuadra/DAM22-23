package servicios;

import data.DaoArticles;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import model.*;

import java.util.List;

public class ServicesArticlesSQL {

    private final DaoArticles daoArticles;

    @Inject
    public ServicesArticlesSQL(DaoArticles daoArticles) {
        this.daoArticles = daoArticles;
    }

    public Either<Integer, List<Article>> getArticles() {
        return daoArticles.getAll();
    }

    public Either<Integer, List<Article>> getArticlesByReaderID(Reader reader) {
        return daoArticles.getAll(reader.getId());
    }

    public Either<Integer, List<Query1>> getArticlesQuery() {
        return daoArticles.getArticlesQuery();
    }

    public Integer addArticle(Article article) {
        return daoArticles.add(article);
    }

    public Integer deleteArticle(Article article) {
        return daoArticles.delete(article);
    }

    public Integer updateArticle(Article article) {
        return daoArticles.update(article);
    }

    public Either<Integer, List<Article>> getArticlesByType(String type) {
        return daoArticles.getAll(type);
    }

    public Either<Integer, List<Query2>> getArticlesByTypeAndNameNewspaper(String type, String nameNewspaper) {
        return daoArticles.getArticlesByTypeAndNameNewspaper(type, nameNewspaper);
    }

    public Either<Integer, List<Query3>> getArticlesByNewspaperWithBadRatings(String idNewspaper) {
        return daoArticles.getArticlesByNewspaperWithBadRatings(idNewspaper);
    }

    public List<Article> getArticlesAndTypes() {
        return daoArticles.getArticlesAndTypes();
    }
}
