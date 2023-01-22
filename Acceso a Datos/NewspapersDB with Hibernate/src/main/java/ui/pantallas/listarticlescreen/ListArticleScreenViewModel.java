package ui.pantallas.listarticlescreen;

import io.vavr.control.Either;
import jakarta.inject.Inject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Article;
import model.ArticleType;
import servicios.ServicesArticlesSQL;
import servicios.ServicesTypes;

public class ListArticleScreenViewModel {
    private final ServicesArticlesSQL servicesArticlesSQL;
    private final ServicesTypes servicesTypes;

    @Inject
    public ListArticleScreenViewModel(ServicesArticlesSQL servicesArticlesSQL, ServicesTypes servicesTypes) {
        this.servicesArticlesSQL = servicesArticlesSQL;
        this.servicesTypes = servicesTypes;
    }

    public ObservableList<Article> getArticles() {
        return FXCollections.observableArrayList(servicesArticlesSQL.getArticles().get());
    }

    public ObservableList<Article> getArticlesByType(String type) {
        return FXCollections.observableArrayList(servicesArticlesSQL.getArticlesByType(type).get());
    }

    public ObservableList<ArticleType> getArticleTypes() {
        return FXCollections.observableArrayList(servicesTypes.getArticleTypes().get());
    }

    public Either<Integer, ArticleType> getMostReadType() {
        return servicesTypes.getMostReadType();
    }
}
