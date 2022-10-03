package ui.pantallas.updatearticlescreen;

import jakarta.inject.Inject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import modelo.Article;
import servicios.ServicesArticles;

public class UpdateArticleScreenViewModel {
    private final ServicesArticles servicesArticles;

    @Inject
    public UpdateArticleScreenViewModel(ServicesArticles servicesArticles) {
        this.servicesArticles = servicesArticles;
    }

    public ObservableList<Article> getArticles() {
        return FXCollections.observableArrayList(servicesArticles.getArticles());
    }
}
