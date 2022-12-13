package servicios;

import data.DaoArticlesSQL;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import model.*;

import java.util.List;

public class ServicesArticlesSQL {

    private final DaoArticlesSQL daoArticlesSQL;

    @Inject
    public ServicesArticlesSQL(DaoArticlesSQL daoArticlesSQL) {
        this.daoArticlesSQL = daoArticlesSQL;
    }

    public Either<Integer, List<Article>> getArticles() {
        return daoArticlesSQL.getAll();
    }

    public Either<Integer, List<Article>> getArticlesByReaderID(Reader reader) {
        return daoArticlesSQL.getAll(reader.getId());
    }

    public Either<Integer, List<ArticleType>> getArticleTypes() {
        return daoArticlesSQL.getAllArticleTypes();
    }

    public Either<Integer, List<Article>> addRating(Article article, Integer rating, Integer idReader) {
        return daoArticlesSQL.saveReadArticle(article, rating, idReader);
    }

    public Either<Integer, List<Query1>> getArticlesQuery() {
        return daoArticlesSQL.getArticlesQuery();
    }

    public Either<Integer, List<Article>> addArticle(Article article) {
        return daoArticlesSQL.add(article);
    }

    public Either<Integer, List<Article>> getArticlesByType(String type) {
        return daoArticlesSQL.getAll(type);
    }

    public Either<Integer, List<Query2>> getArticlesByTypeAndNameNewspaper(String type, String nameNewspaper) {
        return daoArticlesSQL.getArticlesByTypeAndNameNewspaper(type, nameNewspaper);
    }

    public Either<Integer, List<Query3>> getArticlesByNewspaperWithBadRatings(String idNewspaper) {
        return daoArticlesSQL.getArticlesByNewspaperWithBadRatings(idNewspaper);
    }
}
