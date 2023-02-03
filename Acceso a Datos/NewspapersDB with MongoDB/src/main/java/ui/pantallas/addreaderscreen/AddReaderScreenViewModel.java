package ui.pantallas.addreaderscreen;

import jakarta.inject.Inject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Newspaper;
import model.Reader;
import servicios.ServicesNewspaperSQL;
import servicios.ServicesReadersSQL;

public class AddReaderScreenViewModel {

    private final ServicesReadersSQL servicesReadersSQL;
    private final ServicesNewspaperSQL servicesNewspaperSQL;

    @Inject
    public AddReaderScreenViewModel(ServicesReadersSQL servicesReadersSQL, ServicesNewspaperSQL servicesNewspaperSQL) {
        this.servicesReadersSQL = servicesReadersSQL;
        this.servicesNewspaperSQL = servicesNewspaperSQL;
    }

    public ObservableList<Reader> getReaders() {
        return FXCollections.observableArrayList(servicesReadersSQL.getAllReaders().get());
    }

    public int addReader(Reader reader, Newspaper newspaper) {
        return servicesReadersSQL.saveReader(reader, newspaper);
    }

    public ObservableList<Newspaper> getNewspapers() {
        return FXCollections.observableArrayList(servicesNewspaperSQL.getNewspapers().get());
    }
}