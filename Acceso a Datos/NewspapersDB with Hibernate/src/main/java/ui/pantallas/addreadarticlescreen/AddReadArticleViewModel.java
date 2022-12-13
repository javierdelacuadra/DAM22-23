package ui.pantallas.addreadarticlescreen;

import io.vavr.control.Either;
import jakarta.inject.Inject;
import model.Article;
import model.Reader;
import servicios.ServicesArticlesSQL;

import java.util.List;

public class AddReadArticleViewModel {

    private final ServicesArticlesSQL servicesArticlesSQL;

    @Inject
    public AddReadArticleViewModel(ServicesArticlesSQL servicesArticlesSQL) {
        this.servicesArticlesSQL = servicesArticlesSQL;
    }

    public Either<Integer, List<Article>> getArticles(Reader reader) {
        return servicesArticlesSQL.getArticlesByReaderID(reader);
    }

    public Either<Integer, List<Article>> addRating(Article article, Integer rating, Integer idReader) {
        return servicesArticlesSQL.addRating(article, rating, idReader);
    }
}