package ui.pantallas.addreaderscreen;

import io.vavr.control.Either;
import jakarta.inject.Inject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Reader;
import servicios.ServicesReadersSQL;

import java.util.List;

public class AddReaderScreenViewModel {

    private final ServicesReadersSQL servicesReadersSQL;

    @Inject
    public AddReaderScreenViewModel(ServicesReadersSQL servicesReadersSQL) {
        this.servicesReadersSQL = servicesReadersSQL;
    }

    public ObservableList<Reader> getReaders() {
        return FXCollections.observableArrayList(servicesReadersSQL.getAllReaders().get());
    }

    public Either<Integer, List<Reader>> addReader(Reader reader, String password) {
        return servicesReadersSQL.saveReader(reader, password);
    }
}