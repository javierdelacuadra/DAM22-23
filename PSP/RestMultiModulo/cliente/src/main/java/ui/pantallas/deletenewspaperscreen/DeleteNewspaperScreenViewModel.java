package ui.pantallas.deletenewspaperscreen;

import io.reactivex.rxjava3.schedulers.Schedulers;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import modelo.Newspaper;
import servicios.ServicesNewspaperSQL;

import java.util.List;

public class DeleteNewspaperScreenViewModel {
    private final ServicesNewspaperSQL servicesNewspaperSQL;
    private final ObjectProperty<DeleteNewspaperState> state;

    @Inject
    public DeleteNewspaperScreenViewModel(ServicesNewspaperSQL servicesNewspaperSQL) {
        this.servicesNewspaperSQL = servicesNewspaperSQL;
        state = new SimpleObjectProperty<>(new DeleteNewspaperState(null, null));
    }

    public ObjectProperty<DeleteNewspaperState> getState() {
        return state;
    }

    public void getNewspapers() {
        servicesNewspaperSQL.getNewspapers()
                .observeOn(Schedulers.single())
                .subscribe(either -> {
                    if (either.isRight()) {
                        state.set(new DeleteNewspaperState(null, either.get()));
                    } else {
                        state.set(new DeleteNewspaperState(either.getLeft(), null));
                    }
                });
    }

    public Either<String, Boolean> deleteNewspaper(Newspaper newspaper) {
        servicesNewspaperSQL.deleteNewspaper(String.valueOf(newspaper.getId()))
                .observeOn(Schedulers.single())
                .subscribe(either -> {
                    if (either.isRight()) {
                        List<Newspaper> newspapers = state.get().newspapers;
                        newspapers.removeIf(n -> n.getId() == newspaper.getId());
                        state.set(new DeleteNewspaperState(null, newspapers));
                    } else {
                        state.set(new DeleteNewspaperState(either.getLeft(), null));
                    }
                });
        return Either.right(true);
    }
}
