package ui.pantallas.listreaderscreen;

import io.vavr.control.Either;
import jakarta.inject.Inject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.ArticleType;
import model.Newspaper;
import model.Reader;
import servicios.ServicesArticlesSQL;
import servicios.ServicesNewspaperSQL;
import servicios.ServicesReadersSQL;

public class ListReadersScreenViewModel {

    private final ServicesNewspaperSQL servicesNewspaperSQL;
    private final ServicesArticlesSQL servicesArticlesSQL;
    private final ServicesReadersSQL servicesReadersSQL;

    @Inject
    public ListReadersScreenViewModel(ServicesNewspaperSQL servicesNewspaperSQL, ServicesArticlesSQL servicesArticlesSQL, ServicesReadersSQL servicesReadersSQL) {
        this.servicesNewspaperSQL = servicesNewspaperSQL;
        this.servicesArticlesSQL = servicesArticlesSQL;
        this.servicesReadersSQL = servicesReadersSQL;
    }

    public ObservableList<Reader> getReaders() {
        return FXCollections.observableArrayList(servicesReadersSQL.getAllReaders().get());
    }

    public ObservableList<Newspaper> getNewspapers() {
        return FXCollections.observableArrayList(servicesNewspaperSQL.getNewspapers().get());
    }

    public Either<Integer, ObservableList<Reader>> getReadersByNewspaper(Newspaper newspaper) {
        return servicesReadersSQL.getReadersByNewspaper(newspaper.getId()).map(FXCollections::observableArrayList);
    }

    public Either<Integer, ObservableList<Reader>> getReadersByArticleType(String articleType) {
        return servicesReadersSQL.getReadersByArticleType(articleType).map(FXCollections::observableArrayList);
    }

    public ObservableList<ArticleType> getArticleTypes() {
        return FXCollections.observableArrayList(servicesArticlesSQL.getArticleTypes().get());
    }

    public Either<Integer, ObservableList<Reader>> getOldestSubscribers() {
        return servicesReadersSQL.getOldestSubscribers().map(FXCollections::observableArrayList);
    }
}
