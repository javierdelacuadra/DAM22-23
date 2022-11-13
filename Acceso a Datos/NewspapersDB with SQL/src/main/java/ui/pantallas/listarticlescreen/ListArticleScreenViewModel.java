package ui.pantallas.listarticlescreen;

import jakarta.inject.Inject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Article;
import model.ArticleType;
import model.Query1;
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

    public ObservableList<Article> getArticlesByType(String type) {
        return FXCollections.observableArrayList(servicesArticlesSQL.getArticlesByType(type).get());
    }

    public ObservableList<Query1> showArticlesQuery() {
        return FXCollections.observableArrayList(servicesArticlesSQL.getArticlesQuery().get());
    }

    public ObservableList<ArticleType> getArticleTypes() {
        return FXCollections.observableArrayList(servicesArticlesSQL.getArticleTypes().get());
    }
}
