package ui.pantallas.deletearticlescreen;

import jakarta.inject.Inject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import modelo.Article;
import servicios.ServicesArticles;

public class DeleteArticleScreenViewModel {
    private final ServicesArticles servicesArticles;

    @Inject
    public DeleteArticleScreenViewModel(ServicesArticles servicesArticles) {
        this.servicesArticles = servicesArticles;
    }

    public ObservableList<Article> getArticles() {
        return FXCollections.observableArrayList(servicesArticles.getArticles());
    }
}
