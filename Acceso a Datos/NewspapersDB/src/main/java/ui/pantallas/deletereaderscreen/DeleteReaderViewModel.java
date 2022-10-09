package ui.pantallas.deletereaderscreen;

import jakarta.inject.Inject;
import jakarta.xml.bind.JAXBException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import modelo.Reader;
import servicios.ServicesReaders;

public class DeleteReaderViewModel {

    private final ServicesReaders servicesReaders;

    @Inject
    public DeleteReaderViewModel(ServicesReaders servicesReaders) {
        this.servicesReaders = servicesReaders;
    }

    public ObservableList<Reader> getReaders() throws JAXBException {
        return FXCollections.observableArrayList(servicesReaders.getReaders().get());
    }

    public void deleteReader(Reader reader) throws JAXBException {
        servicesReaders.deleteReader(reader);
    }
}
