package ui.pantallas.listarticlescreen;

import jakarta.inject.Inject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Article;
import servicios.ServicesArticlesSQL;

public class ListArticleScreenViewModel {
    private final ServicesArticlesSQL servicesArticlesSQL;

    @Inject
    public ListArticleScreenViewModel(ServicesArticlesSQL servicesArticlesSQL) {
        this.servicesArticlesSQL = servicesArticlesSQL;
    }

    public ObservableList<Article> getArticles() {
        return FXCollections.observableArrayList(servicesArticlesSQL.getArticles().get());
    }
}
