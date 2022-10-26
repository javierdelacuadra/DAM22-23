package ui.pantallas.addreadarticlescreen;

import io.vavr.control.Either;
import jakarta.inject.Inject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

    public ObservableList<Article> getArticles(Reader reader) {
        return FXCollections.observableArrayList(servicesArticlesSQL.getArticlesByReaderID(reader).get());
    }

    public Either<Integer, List<Article>> addRating(Article article, Integer rating, Integer idReader) {
        return servicesArticlesSQL.addRating(article, rating, idReader);
    }
}