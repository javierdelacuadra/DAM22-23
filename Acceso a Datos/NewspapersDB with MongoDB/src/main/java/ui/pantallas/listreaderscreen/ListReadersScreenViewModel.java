package ui.pantallas.listreaderscreen;

import io.vavr.control.Either;
import jakarta.inject.Inject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Newspaper;
import model.Reader;
import servicios.ServicesNewspaperSQL;
import servicios.ServicesReadersSQL;

import java.util.List;

public class ListReadersScreenViewModel {

    private final ServicesNewspaperSQL servicesNewspaperSQL;
    private final ServicesReadersSQL servicesReadersSQL;

    @Inject
    public ListReadersScreenViewModel(ServicesNewspaperSQL servicesNewspaperSQL, ServicesReadersSQL servicesReadersSQL) {
        this.servicesNewspaperSQL = servicesNewspaperSQL;
        this.servicesReadersSQL = servicesReadersSQL;
    }

    public ObservableList<Reader> getReaders() {
        return FXCollections.observableArrayList(servicesReadersSQL.getAllReaders().get());
    }

    public ObservableList<Newspaper> getNewspapers() {
        return FXCollections.observableArrayList(servicesNewspaperSQL.getNewspapers().get());
    }

    public Either<Integer, List<Reader>> getReadersByNewspaper(Newspaper newspaper) {
        return servicesReadersSQL.getReadersByNewspaper(newspaper);
    }
}