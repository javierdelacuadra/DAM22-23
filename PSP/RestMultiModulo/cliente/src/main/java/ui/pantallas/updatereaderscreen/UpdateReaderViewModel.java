package ui.pantallas.updatereaderscreen;

import io.vavr.control.Either;
import jakarta.inject.Inject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Reader;
import servicios.ServicesReadersSQL;

public class UpdateReaderViewModel {

    private final ServicesReadersSQL servicesReadersSQL;

    @Inject
    public UpdateReaderViewModel(ServicesReadersSQL servicesReadersSQL) {
        this.servicesReadersSQL = servicesReadersSQL;
    }

    public ObservableList<Reader> getReaders() {
        return FXCollections.observableArrayList(servicesReadersSQL.getAllReaders().blockingGet().get());
    }

    public Either<String, Reader> updateReader(Reader reader) {
        return servicesReadersSQL.updateReader(reader).blockingGet();
    }

}