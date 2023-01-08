package ui.pantallas.listnewspaperscreen;

import io.vavr.control.Either;
import jakarta.inject.Inject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Article;
import model.Newspaper;
import servicios.ServicesArticlesSQL;
import servicios.ServicesNewspaperSQL;

import java.util.List;

public class ListNewspaperScreenViewModel {

    private final ServicesNewspaperSQL servicesNewspaperSQL;
    private final ServicesArticlesSQL servicesArticlesSQL;

    @Inject
    public ListNewspaperScreenViewModel(ServicesNewspaperSQL servicesNewspaperSQL, ServicesArticlesSQL servicesArticlesSQL) {
        this.servicesNewspaperSQL = servicesNewspaperSQL;
        this.servicesArticlesSQL = servicesArticlesSQL;
    }

    public ObservableList<Newspaper> getNewspapers() {
        return FXCollections.observableArrayList(servicesNewspaperSQL.getNewspapers().get());
    }

    public Either<Integer, List<Article>> getArticlesFromNewspaper(Newspaper newspaper) {
        return servicesArticlesSQL.getArticlesByNameNewspaper(newspaper);
    }
}
