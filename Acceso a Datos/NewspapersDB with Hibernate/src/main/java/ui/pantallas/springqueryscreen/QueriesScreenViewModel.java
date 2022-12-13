package ui.pantallas.springqueryscreen;

import io.vavr.control.Either;
import jakarta.inject.Inject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.ArticleType;
import model.Newspaper;
import model.Query2;
import model.Query3;
import servicios.ServicesArticlesSQL;
import servicios.ServicesNewspaperSQL;

import java.util.List;

public class QueriesScreenViewModel {

    private final ServicesArticlesSQL servicesArticlesSQL;
    private final ServicesNewspaperSQL servicesNewspaperSQL;

    @Inject
    public QueriesScreenViewModel(ServicesArticlesSQL servicesArticlesSQL, ServicesNewspaperSQL servicesNewspaperSQL) {
        this.servicesArticlesSQL = servicesArticlesSQL;
        this.servicesNewspaperSQL = servicesNewspaperSQL;
    }

    public ObservableList<Query2> getArticlesByTypeAndNameNewspaper(String type, String nameNewspaper) {
        Either<Integer, List<Query2>> result = servicesArticlesSQL.getArticlesByTypeAndNameNewspaper(type, nameNewspaper);
        if (result.isRight()) {
            return FXCollections.observableArrayList(result.get());
        } else {
            return FXCollections.emptyObservableList();
        }
    }

    public ObservableList<Query3> getArticlesByNewspaperWithBadRatings(String idNewspaper) {
        Either<Integer, List<Query3>> result = servicesArticlesSQL.getArticlesByNewspaperWithBadRatings(idNewspaper);
        if (result.isRight()) {
            return FXCollections.observableArrayList(result.get());
        } else {
            return FXCollections.emptyObservableList();
        }
    }

    public ObservableList<ArticleType> getArticleTypes() {
        return FXCollections.observableArrayList(servicesArticlesSQL.getArticleTypes().get());
    }

    public ObservableList<Newspaper> getNewspapers() {
        return FXCollections.observableArrayList(servicesNewspaperSQL.getNewspapers().get());
    }
}
