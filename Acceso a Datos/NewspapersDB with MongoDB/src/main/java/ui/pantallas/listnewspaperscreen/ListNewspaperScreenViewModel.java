package ui.pantallas.listnewspaperscreen;

import jakarta.inject.Inject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Newspaper;
import servicios.ServicesArticlesSQL;
import servicios.ServicesNewspaperSQL;

import java.util.Map;

public class ListNewspaperScreenViewModel {

    private final ServicesNewspaperSQL servicesNewspaperSQL;
    private final ServicesArticlesSQL servicesArticlesSQL;

    @Inject
    public ListNewspaperScreenViewModel(ServicesNewspaperSQL servicesNewspaperSQL, ServicesArticlesSQL servicesArticlesSQL) {
        this.servicesNewspaperSQL = servicesNewspaperSQL;
        this.servicesArticlesSQL = servicesArticlesSQL;
    }

    public ObservableList<Newspaper> getNewspapers() {
        return FXCollections.observableArrayList(servicesNewspaperSQL.getNewspapers().get());
    }

    public Newspaper getArticlesFromNewspaper(Newspaper newspaper) {
        return servicesNewspaperSQL.getArticlesByNameNewspaper(newspaper);
    }

    public int deleteArticlesFromNewspaper(Newspaper newspaper) {
        return servicesArticlesSQL.deleteArticleFromNewspaper(newspaper.getId());
    }

    public Map<String, Integer> getNbrArticles(int idNewspaper) {
        return servicesNewspaperSQL.getNbrArticles(idNewspaper);
    }
}
