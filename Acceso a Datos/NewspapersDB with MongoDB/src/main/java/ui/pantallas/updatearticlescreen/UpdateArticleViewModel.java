package ui.pantallas.updatearticlescreen;

import jakarta.inject.Inject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Article;
import servicios.ServicesArticlesSQL;

public class UpdateArticleViewModel {

    private final ServicesArticlesSQL servicesArticlesSQL;

    @Inject
    public UpdateArticleViewModel(ServicesArticlesSQL servicesArticlesSQL) {
        this.servicesArticlesSQL = servicesArticlesSQL;
    }

    public ObservableList<Article> getArticles() {
        return FXCollections.observableArrayList(servicesArticlesSQL.getArticlesAndTypes());
    }

    public Integer updateArticle(Article article) {
        return servicesArticlesSQL.updateArticle(article);
    }
}
