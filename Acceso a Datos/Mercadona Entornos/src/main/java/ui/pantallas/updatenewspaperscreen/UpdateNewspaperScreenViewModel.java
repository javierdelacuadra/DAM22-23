package ui.pantallas.updatenewspaperscreen;

import jakarta.inject.Inject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import modelo.Newspaper;
import servicios.ServicesNewspaper;

public class UpdateNewspaperScreenViewModel {
    private final ServicesNewspaper servicesNewspaper;

    @Inject
    public UpdateNewspaperScreenViewModel(ServicesNewspaper servicesNewspaper) {
        this.servicesNewspaper = servicesNewspaper;
    }

    public ObservableList<Newspaper> getNewspapers() {
        return FXCollections.observableArrayList(servicesNewspaper.getNewspapers());
    }
}
