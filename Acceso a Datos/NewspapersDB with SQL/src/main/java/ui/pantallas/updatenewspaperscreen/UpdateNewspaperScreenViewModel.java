package ui.pantallas.updatenewspaperscreen;

import io.vavr.control.Either;
import jakarta.inject.Inject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Newspaper;
import servicios.ServicesNewspaperSQL;

import java.util.List;

public class UpdateNewspaperScreenViewModel {

    private final ServicesNewspaperSQL servicesNewspaperSQL;

    @Inject
    public UpdateNewspaperScreenViewModel(ServicesNewspaperSQL servicesNewspaperSQL) {
        this.servicesNewspaperSQL = servicesNewspaperSQL;
    }

    public ObservableList<Newspaper> getNewspapers() {
        return FXCollections.observableArrayList(servicesNewspaperSQL.getNewspapers().get());
    }

    public Either<Integer, List<Newspaper>> updateNewspaper(Newspaper newspaper) {
        return servicesNewspaperSQL.updateNewspaper(newspaper);
    }
}
