package ui.pantallas.updatenewspaperscreen;

import jakarta.inject.Inject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Newspaper;
import servicios.ServicesNewspaperSQL;

public class UpdateNewspaperScreenViewModel {

    private final ServicesNewspaperSQL servicesNewspaperSQL;

    @Inject
    public UpdateNewspaperScreenViewModel(ServicesNewspaperSQL servicesNewspaperSQL) {
        this.servicesNewspaperSQL = servicesNewspaperSQL;
    }

    public ObservableList<Newspaper> getNewspapers() {
        return FXCollections.observableArrayList(servicesNewspaperSQL.getNewspapers().get());
    }

    public Integer updateNewspaper(Newspaper newspaper) {
        return servicesNewspaperSQL.updateNewspaper(newspaper);
    }
}
