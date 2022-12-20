package ui.pantallas.deletearticlescreen;

import jakarta.inject.Inject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Article;
import servicios.ServicesArticlesSQL;

public class DeleteArticleViewModel {
    private final ServicesArticlesSQL servicesArticlesSQL;

    @Inject
    public DeleteArticleViewModel(ServicesArticlesSQL servicesArticlesSQL) {
        this.servicesArticlesSQL = servicesArticlesSQL;
    }

    public ObservableList<Article> getArticles() {
        return FXCollections.observableArrayList(servicesArticlesSQL.getArticles().get());
    }

    public Integer deleteArticle(Integer id) {
        return servicesArticlesSQL.deleteArticle(id);
    }
}