package ui.pantallas.addreadarticlescreen;

import io.vavr.control.Either;
import jakarta.inject.Inject;
import model.Article;
import model.ReadArticle;
import model.Reader;
import servicios.ServicesArticlesSQL;
import servicios.ServicesReadArticles;

import java.util.List;

public class AddReadArticleViewModel {

    private final ServicesArticlesSQL servicesArticlesSQL;
    private final ServicesReadArticles servicesReadArticles;

    @Inject
    public AddReadArticleViewModel(ServicesArticlesSQL servicesArticlesSQL, ServicesReadArticles servicesReadArticles) {
        this.servicesArticlesSQL = servicesArticlesSQL;
        this.servicesReadArticles = servicesReadArticles;
    }

    public Either<Integer, List<Article>> getArticles(Reader reader) {
        return servicesArticlesSQL.getArticlesByReaderID(reader);
    }

    public int addRating(ReadArticle readArticle, Article article) {
        return servicesReadArticles.addRating(readArticle, article);
    }

    public int updateRating(ReadArticle readArticle, Article article) {
        return servicesReadArticles.updateRating(readArticle, article);
    }
}