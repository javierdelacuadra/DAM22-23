package ui.pantallas.listreaderscreen;

import jakarta.inject.Inject;
import jakarta.xml.bind.JAXBException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import modelo.Newspaper;
import modelo.Reader;
import servicios.ServicesNewspaper;
import servicios.ServicesReaders;

public class ListReadersScreenViewModel {

    private final ServicesReaders servicesReaders;
    private final ServicesNewspaper servicesNewspaper;

    @Inject
    public ListReadersScreenViewModel(ServicesReaders servicesReaders, ServicesNewspaper servicesNewspaper) {
        this.servicesReaders = servicesReaders;
        this.servicesNewspaper = servicesNewspaper;
    }

    public ObservableList<Reader> getReaders() throws JAXBException {
        return FXCollections.observableArrayList(servicesReaders.getReaders().get());
    }

    public ObservableList<Newspaper> getNewspapers() {
        return FXCollections.observableArrayList(servicesNewspaper.getNewspapers());
    }

    public ObservableList<Reader> getReadersByNewspaper(Newspaper newspaper) throws JAXBException {
        return FXCollections.observableArrayList(servicesReaders.getReadersByNewspaper(newspaper.getId()));
    }

}
