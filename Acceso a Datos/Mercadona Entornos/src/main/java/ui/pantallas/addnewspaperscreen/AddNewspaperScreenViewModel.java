package ui.pantallas.addnewspaperscreen;

import jakarta.inject.Inject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import modelo.Newspaper;
import servicios.ServicesNewspaper;

public class AddNewspaperScreenViewModel {
    private final ServicesNewspaper servicesNewspaper;

    @Inject
    public AddNewspaperScreenViewModel(ServicesNewspaper servicesNewspaper) {
        this.servicesNewspaper = servicesNewspaper;
    }

    public ObservableList<Newspaper> getNewspapers() {
        return FXCollections.observableArrayList(servicesNewspaper.getNewspapers());
    }

    public boolean addNewspaper(Newspaper newspaper) {
        return servicesNewspaper.addNewspaper(newspaper);
    }
}
