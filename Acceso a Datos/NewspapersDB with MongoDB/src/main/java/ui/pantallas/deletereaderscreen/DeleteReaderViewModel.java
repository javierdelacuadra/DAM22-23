package ui.pantallas.deletereaderscreen;

import jakarta.inject.Inject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Reader;
import servicios.ServicesReadersSQL;

public class DeleteReaderViewModel {

    private final ServicesReadersSQL servicesReadersSQL;

    @Inject
    public DeleteReaderViewModel(ServicesReadersSQL servicesReadersSQL) {
        this.servicesReadersSQL = servicesReadersSQL;
    }

    public ObservableList<Reader> getReaders() {
        return FXCollections.observableArrayList(servicesReadersSQL.getAllReaders().get());
    }

    public int deleteReader(Reader reader, boolean delete) {
        return servicesReadersSQL.deleteReader(reader, delete);
    }
}
