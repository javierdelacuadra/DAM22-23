package ui.pantallas.addarticlescreen;

import jakarta.inject.Inject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Article;
import model.ArticleType;
import model.Newspaper;
import servicios.ServicesArticlesSQL;
import servicios.ServicesNewspaperSQL;
import servicios.ServicesTypes;

public class AddArticleScreenViewModel {
    private final ServicesArticlesSQL servicesArticlesSQL;
    private final ServicesNewspaperSQL servicesNewspaperSQL;
    private final ServicesTypes servicesTypes;

    @Inject
    public AddArticleScreenViewModel(ServicesArticlesSQL servicesArticlesSQL, ServicesNewspaperSQL servicesNewspaperSQL, ServicesTypes servicesTypes) {
        this.servicesArticlesSQL = servicesArticlesSQL;
        this.servicesNewspaperSQL = servicesNewspaperSQL;
        this.servicesTypes = servicesTypes;
    }

    public ObservableList<Article> getArticles() {
        return FXCollections.observableArrayList(servicesArticlesSQL.getArticles().get());
    }

    public Integer addArticle(Article article, Newspaper newspaper) {
        return servicesArticlesSQL.addArticle(article, newspaper);
    }

    public ObservableList<ArticleType> getArticleTypes() {
        return FXCollections.observableArrayList(servicesTypes.getArticleTypes().get());
    }

    public ObservableList<Newspaper> getNewspapers() {
        return FXCollections.observableArrayList(servicesNewspaperSQL.getNewspapers().get());
    }
}