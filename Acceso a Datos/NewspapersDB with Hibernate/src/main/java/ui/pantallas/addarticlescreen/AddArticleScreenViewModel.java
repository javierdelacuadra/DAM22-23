package ui.pantallas.addarticlescreen;

import io.vavr.control.Either;
import jakarta.inject.Inject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Article;
import model.ArticleType;
import model.Newspaper;
import servicios.ServicesArticlesSQL;
import servicios.ServicesNewspaperSQL;

import java.util.List;

public class AddArticleScreenViewModel {
    private final ServicesArticlesSQL servicesArticlesSQL;
    private final ServicesNewspaperSQL servicesNewspaperSQL;

    @Inject
    public AddArticleScreenViewModel(ServicesArticlesSQL servicesArticlesSQL, ServicesNewspaperSQL servicesNewspaperSQL) {
        this.servicesArticlesSQL = servicesArticlesSQL;
        this.servicesNewspaperSQL = servicesNewspaperSQL;
    }

    public ObservableList<Article> getArticles() {
        return FXCollections.observableArrayList(servicesArticlesSQL.getArticles().get());
    }

    public Either<Integer, List<Article>> addArticle(Article article) {
        return servicesArticlesSQL.addArticle(article);
    }

    public ObservableList<ArticleType> getArticleTypes() {
        return FXCollections.observableArrayList(servicesArticlesSQL.getArticleTypes().get());
    }

    public ObservableList<Newspaper> getNewspapers() {
        return FXCollections.observableArrayList(servicesNewspaperSQL.getNewspapers().get());
    }
}