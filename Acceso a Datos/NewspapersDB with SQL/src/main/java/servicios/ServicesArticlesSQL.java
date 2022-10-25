package servicios;

import data.DaoArticlesSQL;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import model.Article;
import model.ArticleType;

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

    public Either<Integer, List<ArticleType>> getArticleTypes() {
        return daoArticlesSQL.getAllArticleTypes();
    }
}
