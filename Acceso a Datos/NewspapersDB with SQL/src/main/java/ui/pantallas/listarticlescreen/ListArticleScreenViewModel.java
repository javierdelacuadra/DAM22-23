package ui.pantallas.listarticlescreen;

import jakarta.inject.Inject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import modelo.Article;
import servicios.ServicesArticles;

public class ListArticleScreenViewModel {
    private final ServicesArticles servicesArticles;

    @Inject
    public ListArticleScreenViewModel(ServicesArticles servicesArticles) {
        this.servicesArticles = servicesArticles;
    }

    public ObservableList<Article> getArticles() {
        return FXCollections.observableArrayList(servicesArticles.getArticles());
    }
}
