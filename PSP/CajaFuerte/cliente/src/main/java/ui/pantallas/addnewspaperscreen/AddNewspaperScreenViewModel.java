package ui.pantallas.addnewspaperscreen;

import io.reactivex.rxjava3.schedulers.Schedulers;
import jakarta.inject.Inject;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import modelo.Newspaper;
import servicios.ServicesNewspaperSQL;

import java.time.LocalDate;
import java.util.List;

public class AddNewspaperScreenViewModel {

    private final ServicesNewspaperSQL servicesNewspaperSQL;
    private final ObjectProperty<AddNewspaperState> state;

    @Inject
    public AddNewspaperScreenViewModel(ServicesNewspaperSQL servicesNewspaperSQL) {
        this.servicesNewspaperSQL = servicesNewspaperSQL;
        state = new SimpleObjectProperty<>(new AddNewspaperState(null, null));
    }

    public ObjectProperty<AddNewspaperState> getState() {
        return state;
    }

    public void getNewspapers() {
        servicesNewspaperSQL.getNewspapers()
                .observeOn(Schedulers.single())
                .subscribe(either -> {
                    if (either.isRight()) {
                        state.set(new AddNewspaperState(null, either.get()));
                    } else {
                        state.set(new AddNewspaperState(either.getLeft(), null));
                    }
                });
    }

    public void addNewspaper(String name, LocalDate date) {
        Newspaper newspaper = new Newspaper(-1, name, date.toString());
        servicesNewspaperSQL.addNewspaper(newspaper)
                .subscribeOn(Schedulers.single())
                .subscribe(either -> {
                    if (either.isRight()) {
                        List<Newspaper> newspapers = state.get().newspapers;
                        newspapers.add(either.get());
                        state.set(new AddNewspaperState(null, newspapers));
                    } else {
                        state.set(new AddNewspaperState(either.getLeft(), null));
                    }
                });
    }
}
