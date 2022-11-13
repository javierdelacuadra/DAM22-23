package ui.pantallas.addnewspaperscreen;

import io.vavr.control.Either;
import jakarta.inject.Inject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Newspaper;
import servicios.ServicesNewspaperSQL;

import java.time.LocalDate;
import java.util.List;

public class AddNewspaperScreenViewModel {

    private final ServicesNewspaperSQL servicesNewspaperSQL;

    @Inject
    public AddNewspaperScreenViewModel(ServicesNewspaperSQL servicesNewspaperSQL) {
        this.servicesNewspaperSQL = servicesNewspaperSQL;
    }

    public ObservableList<Newspaper> getNewspapers() {
        return FXCollections.observableArrayList(servicesNewspaperSQL.getNewspapers().get());
    }

    public Either<Integer, List<Newspaper>> addNewspaper(String name, LocalDate date) {
        Newspaper newspaper = new Newspaper(-1, name, date.toString());
        return servicesNewspaperSQL.addNewspaper(newspaper);
    }
}
