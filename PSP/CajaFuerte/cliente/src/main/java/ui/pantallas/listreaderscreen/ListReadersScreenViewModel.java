package ui.pantallas.listreaderscreen;

import io.reactivex.rxjava3.schedulers.Schedulers;
import jakarta.inject.Inject;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Newspaper;
import servicios.ServicesNewspaperSQL;
import servicios.ServicesReadersSQL;

public class ListReadersScreenViewModel {

    private final ServicesNewspaperSQL servicesNewspaperSQL;
    private final ServicesReadersSQL servicesReadersSQL;
    private final ObjectProperty<ListReadersState> state;

    @Inject
    public ListReadersScreenViewModel(ServicesNewspaperSQL servicesNewspaperSQL, ServicesReadersSQL servicesReadersSQL) {
        this.servicesNewspaperSQL = servicesNewspaperSQL;
        this.servicesReadersSQL = servicesReadersSQL;
        state = new SimpleObjectProperty<>(new ListReadersState(null, null));
    }

    public ObjectProperty<ListReadersState> getState() {
        return state;
    }

    public void getReaders() {
        servicesReadersSQL.getAllReaders()
                .observeOn(Schedulers.from(Platform::runLater))
                .subscribe(either -> {
                    if (either.isRight()) {
                        state.set(new ListReadersState(null, FXCollections.observableArrayList(either.get())));
                    } else {
                        state.set(new ListReadersState(either.getLeft(), null));

                    }
                });
    }

    public ObservableList<Newspaper> getNewspapers() {
        return FXCollections.observableArrayList(servicesNewspaperSQL.getNewspapers().blockingGet().get());
    }
}
