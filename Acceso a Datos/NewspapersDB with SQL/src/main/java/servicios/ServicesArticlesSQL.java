package servicios;

import data.DaoArticlesSQL;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import model.Article;
import model.ArticleType;
import model.Query1;
import model.Reader;

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
}
