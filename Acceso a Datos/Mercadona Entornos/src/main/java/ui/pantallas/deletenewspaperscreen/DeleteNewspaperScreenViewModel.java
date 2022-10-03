package ui.pantallas.deletenewspaperscreen;

import jakarta.inject.Inject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import modelo.Newspaper;
import servicios.ServicesNewspaper;

public class DeleteNewspaperScreenViewModel {
    private final ServicesNewspaper servicesNewspaper;

    @Inject
    public DeleteNewspaperScreenViewModel(ServicesNewspaper servicesNewspaper) {
        this.servicesNewspaper = servicesNewspaper;
    }

    public ObservableList<Newspaper> getNewspapers() {
        return FXCollections.observableArrayList(servicesNewspaper.getNewspapers());
    }

    public boolean deleteNewspaper(Newspaper newspaper) {
        return servicesNewspaper.deleteNewspaper(newspaper);
    }
}
