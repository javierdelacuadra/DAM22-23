package ui.pantallas.listreaderscreen;

import io.vavr.control.Either;
import jakarta.inject.Inject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.ArticleType;
import model.Newspaper;
import model.Reader;
import servicios.ServicesNewspaperSQL;
import servicios.ServicesReadersSQL;

public class ListReadersScreenViewModel {

    private final ServicesNewspaperSQL servicesNewspaperSQL;
    private final ServicesReadersSQL servicesReadersSQL;

    @Inject
    public ListReadersScreenViewModel(ServicesNewspaperSQL servicesNewspaperSQL, ServicesReadersSQL servicesReadersSQL) {
        this.servicesNewspaperSQL = servicesNewspaperSQL;
        this.servicesReadersSQL = servicesReadersSQL;
    }

    public ObservableList<Reader> getReaders() {
        return FXCollections.observableArrayList(servicesReadersSQL.getAllReaders().blockingGet().get());
    }

    public ObservableList<Newspaper> getNewspapers() {
        return FXCollections.observableArrayList(servicesNewspaperSQL.getNewspapers().blockingGet().get());
    }

    public Either<Integer, ObservableList<Reader>> getReadersByNewspaper(Newspaper newspaper) {
        //return servicesReadersSQL.getReadersByNewspaper(newspaper.getId()).map(FXCollections::observableArrayList);
        return Either.right(FXCollections.observableArrayList());
    }

    public Either<Integer, ObservableList<Reader>> getReadersByArticleType(String articleType) {
//        return servicesReadersSQL.getReadersByArticleType(articleType).map(FXCollections::observableArrayList);
        return Either.right(FXCollections.observableArrayList());
    }

    public Either<Integer, ObservableList<Reader>> getOldestSubscribers() {
//        return servicesReadersSQL.getOldestSubscribers().map(FXCollections::observableArrayList);
        return Either.right(FXCollections.observableArrayList());

    }
}
