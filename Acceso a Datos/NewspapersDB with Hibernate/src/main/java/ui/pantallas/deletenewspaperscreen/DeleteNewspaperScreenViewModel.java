package ui.pantallas.deletenewspaperscreen;

import jakarta.inject.Inject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Newspaper;
import servicios.ServicesNewspaperSQL;

public class DeleteNewspaperScreenViewModel {
    private final ServicesNewspaperSQL servicesNewspaperSQL;

    @Inject
    public DeleteNewspaperScreenViewModel(ServicesNewspaperSQL servicesNewspaperSQL) {
        this.servicesNewspaperSQL = servicesNewspaperSQL;
    }

    public ObservableList<Newspaper> getNewspapers() {
        return FXCollections.observableArrayList(servicesNewspaperSQL.getNewspapers().get());
    }

    public void deleteNewspaper(Newspaper newspaper) {
        servicesNewspaperSQL.deleteNewspaper(newspaper.getId());
    }
}
