package ui.pantallas.listreaderscreen;

import io.vavr.control.Either;
import jakarta.inject.Inject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Newspaper;
import model.Reader;
import servicios.ServicesNewspaper;
import servicios.ServicesReadersSQL;

public class ListReadersScreenViewModel {

    private final ServicesNewspaper servicesNewspaper;
    private final ServicesReadersSQL servicesReadersSQL;

    @Inject
    public ListReadersScreenViewModel(ServicesNewspaper servicesNewspaper, ServicesReadersSQL servicesReadersSQL) {
        this.servicesNewspaper = servicesNewspaper;
        this.servicesReadersSQL = servicesReadersSQL;
    }

    public ObservableList<Reader> getReaders() {
        return FXCollections.observableArrayList(servicesReadersSQL.getAllReaders().get());
    }

    public ObservableList<Newspaper> getNewspapers() {
        return FXCollections.observableArrayList(servicesNewspaper.getNewspapers());
    }

    public Either<Integer, ObservableList<Reader>> getReadersByNewspaper(Newspaper newspaper) {
        return servicesReadersSQL.getReadersByNewspaper(newspaper.getId()).map(FXCollections::observableArrayList);
    }

}
