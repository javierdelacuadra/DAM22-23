package ui.pantallas.deletereaderscreen;

import jakarta.inject.Inject;
import jakarta.xml.bind.JAXBException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Reader;
import servicios.ServicesReaders;
import servicios.ServicesReadersSQL;

public class DeleteReaderViewModel {

    private final ServicesReaders servicesReaders;
    private final ServicesReadersSQL servicesReadersSQL;

    @Inject
    public DeleteReaderViewModel(ServicesReaders servicesReaders, ServicesReadersSQL servicesReadersSQL) {
        this.servicesReaders = servicesReaders;
        this.servicesReadersSQL = servicesReadersSQL;
    }

    public ObservableList<Reader> getReaders() {
        return FXCollections.observableArrayList(servicesReadersSQL.getAllReaders().get());
    }

    public void deleteReader(Reader reader) throws JAXBException {
        servicesReaders.deleteReader(reader);
    }
}
