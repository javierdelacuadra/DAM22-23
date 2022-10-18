package ui.pantallas.listreaderscreen;

import jakarta.inject.Inject;
import jakarta.xml.bind.JAXBException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Newspaper;
import model.Reader;
import servicios.ServicesNewspaper;
import servicios.ServicesReaders;
import servicios.ServicesReadersSQL;

public class ListReadersScreenViewModel {

    private final ServicesReaders servicesReaders;
    private final ServicesNewspaper servicesNewspaper;
    private final ServicesReadersSQL servicesReadersSQL;

    @Inject
    public ListReadersScreenViewModel(ServicesReaders servicesReaders, ServicesNewspaper servicesNewspaper, ServicesReadersSQL servicesReadersSQL) {
        this.servicesReaders = servicesReaders;
        this.servicesNewspaper = servicesNewspaper;
        this.servicesReadersSQL = servicesReadersSQL;
    }

    public ObservableList<Reader> getReaders() {
        return FXCollections.observableArrayList(servicesReadersSQL.getAllReaders().get());
    }

    public ObservableList<Newspaper> getNewspapers() {
        return FXCollections.observableArrayList(servicesNewspaper.getNewspapers());
    }

    public ObservableList<Reader> getReadersByNewspaper(Newspaper newspaper) throws JAXBException {
        return FXCollections.observableArrayList(servicesReaders.getReadersByNewspaper(newspaper.getId()));
    }

}
