package ui.pantallas.listreaderscreen;

import io.reactivex.rxjava3.schedulers.Schedulers;
import jakarta.inject.Inject;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import modelo.Newspaper;
import modelo.Reader;
import servicios.ServicesNewspaperSQL;
import servicios.ServicesReadersSQL;

import java.util.concurrent.atomic.AtomicReference;

public class ListReadersScreenViewModel {

    private final ServicesNewspaperSQL servicesNewspaperSQL;
    private final ServicesReadersSQL servicesReadersSQL;

    @Inject
    public ListReadersScreenViewModel(ServicesNewspaperSQL servicesNewspaperSQL, ServicesReadersSQL servicesReadersSQL) {
        this.servicesNewspaperSQL = servicesNewspaperSQL;
        this.servicesReadersSQL = servicesReadersSQL;
    }

    public ObservableList<Reader> getReaders() {
        AtomicReference<ObservableList<Reader>> readers = new AtomicReference<>(FXCollections.observableArrayList());
        servicesReadersSQL.getAllReaders()
                .observeOn(Schedulers.from(Platform::runLater))
                .subscribe(either -> {
                    if (either.isRight()) {
                        readers.get().addAll(either.get());
                    } else {
                        readers.set(FXCollections.observableArrayList());
                    }
                });
        return readers.get();
    }

    public ObservableList<Newspaper> getNewspapers() {
        return FXCollections.observableArrayList(servicesNewspaperSQL.getNewspapers().blockingGet().get());
    }
}
