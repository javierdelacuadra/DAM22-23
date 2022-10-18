package ui.pantallas.addarticlescreen;

import jakarta.inject.Inject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Article;
import servicios.ServicesArticles;

public class AddArticleScreenViewModel {
    private final ServicesArticles servicesArticles;

    @Inject
    public AddArticleScreenViewModel(ServicesArticles servicesArticles) {
        this.servicesArticles = servicesArticles;
    }

    public ObservableList<Article> getArticles() {
        return FXCollections.observableArrayList(servicesArticles.getArticles());
    }

    public boolean addArticle(Article article) {
        return servicesArticles.addArticle(article);
    }
}