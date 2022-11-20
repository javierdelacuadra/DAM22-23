package ui.pantallas.listnewspaperscreen;

import io.reactivex.rxjava3.schedulers.Schedulers;
import jakarta.inject.Inject;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import modelo.Newspaper;
import servicios.ServicesNewspaperSQL;

import java.util.concurrent.atomic.AtomicReference;

public class ListNewspaperScreenViewModel {

    private final ServicesNewspaperSQL servicesNewspaperSQL;

    @Inject
    public ListNewspaperScreenViewModel(ServicesNewspaperSQL servicesNewspaperSQL) {
        this.servicesNewspaperSQL = servicesNewspaperSQL;
    }

    public ObservableList<Newspaper> getNewspapers() {
        AtomicReference<ObservableList<Newspaper>> newspapers = new AtomicReference<>(FXCollections.observableArrayList());
        servicesNewspaperSQL.getNewspapers()
                .observeOn(Schedulers.from(Platform::runLater))
                .subscribe(either -> {
                    if (either.isRight()) {
                        newspapers.get().addAll(either.get());
                    } else {
                        newspapers.set(FXCollections.observableArrayList());
                    }
                });
        return newspapers.get();
    }
}
