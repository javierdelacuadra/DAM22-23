package ui.pantallas.addarticlescreen;

import jakarta.inject.Inject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Article;
import model.Newspaper;
import servicios.ServicesArticlesSQL;
import servicios.ServicesNewspaperSQL;

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

    public Integer addArticle(Article article, Newspaper newspaper) {
        return servicesArticlesSQL.addArticle(article, newspaper);
    }

    public ObservableList<Newspaper> getNewspapers() {
        return FXCollections.observableArrayList(servicesNewspaperSQL.getNewspapers().get());
    }
}