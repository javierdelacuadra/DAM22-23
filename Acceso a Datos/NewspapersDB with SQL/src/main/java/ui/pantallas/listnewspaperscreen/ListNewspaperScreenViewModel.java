package ui.pantallas.listnewspaperscreen;

import jakarta.inject.Inject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Newspaper;
import servicios.ServicesNewspaper;

public class ListNewspaperScreenViewModel {

    private final ServicesNewspaper servicesNewspaper;

    @Inject
    public ListNewspaperScreenViewModel(ServicesNewspaper servicesNewspaper) {
        this.servicesNewspaper = servicesNewspaper;
    }

    public ObservableList<Newspaper> getNewspapers() {
        return FXCollections.observableArrayList(servicesNewspaper.getNewspapers());
    }
}
