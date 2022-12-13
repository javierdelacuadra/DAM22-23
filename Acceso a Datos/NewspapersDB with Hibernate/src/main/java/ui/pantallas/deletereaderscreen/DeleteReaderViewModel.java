package ui.pantallas.deletereaderscreen;

import io.vavr.control.Either;
import jakarta.inject.Inject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Reader;
import servicios.ServicesReadersSQL;

import java.util.List;

public class DeleteReaderViewModel {

    private final ServicesReadersSQL servicesReadersSQL;

    @Inject
    public DeleteReaderViewModel(ServicesReadersSQL servicesReadersSQL) {
        this.servicesReadersSQL = servicesReadersSQL;
    }

    public ObservableList<Reader> getReaders() {
        return FXCollections.observableArrayList(servicesReadersSQL.getAllReaders().get());
    }

    public Either<Integer, List<Reader>> deleteReader(Reader reader) {
        return servicesReadersSQL.deleteReader(reader);
    }
}
