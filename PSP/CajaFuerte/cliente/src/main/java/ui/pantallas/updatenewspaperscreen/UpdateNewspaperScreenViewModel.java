package ui.pantallas.updatenewspaperscreen;

import io.reactivex.rxjava3.schedulers.Schedulers;
import jakarta.inject.Inject;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import modelo.Newspaper;
import servicios.ServicesNewspaperSQL;

import java.util.List;

public class UpdateNewspaperScreenViewModel {

    private final ServicesNewspaperSQL servicesNewspaperSQL;
    private final ObjectProperty<UpdateNewspaperState> state;

    @Inject
    public UpdateNewspaperScreenViewModel(ServicesNewspaperSQL servicesNewspaperSQL) {
        this.servicesNewspaperSQL = servicesNewspaperSQL;
        state = new SimpleObjectProperty<>(new UpdateNewspaperState(null, null));
    }

    public ObjectProperty<UpdateNewspaperState> getState() {
        return state;
    }

    public void getNewspapers() {
        servicesNewspaperSQL.getNewspapers()
                .observeOn(Schedulers.single())
                .subscribe(either -> {
                    if (either.isRight()) {
                        state.set(new UpdateNewspaperState(null, either.get()));
                    } else {
                        state.set(new UpdateNewspaperState(either.getLeft(), null));
                    }
                });
    }

    public void updateNewspaper(Newspaper newspaper) {
        servicesNewspaperSQL.updateNewspaper(newspaper)
                .subscribeOn(Schedulers.single())
                .subscribe(either -> {
                    if (either.isRight()) {
                        List<Newspaper> newspapers = state.get().newspapers;
                        newspapers.stream()
                                .filter(n -> n.getId() == (newspaper.getId()))
                                .findFirst()
                                .ifPresent(n -> {
                                    n.setName(newspaper.getName());
                                    n.setRelease_date(newspaper.getRelease_date());
                                });
                        state.set(new UpdateNewspaperState(null, newspapers));
                    } else {
                        state.set(new UpdateNewspaperState(either.getLeft(), null));
                    }
                });
    }
}
