package ui.pantallas.listreaderscreen;

import jakarta.inject.Inject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import modelo.Reader;
import servicios.ServicesReaders;

public class ListReadersScreenViewModel {

    private final ServicesReaders servicesReaders;

    @Inject
    public ListReadersScreenViewModel(ServicesReaders servicesReaders) {
        this.servicesReaders = servicesReaders;
    }
    public ObservableList<Reader> getReaders() {
        return FXCollections.observableArrayList(servicesReaders.getReaders());
    }
}
