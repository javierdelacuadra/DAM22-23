package ui.pantallas.addreaderscreen;

import io.reactivex.rxjava3.schedulers.Schedulers;
import jakarta.inject.Inject;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import modelo.Reader;
import servicios.ServicesReadersSQL;

import java.util.List;

public class AddReaderScreenViewModel {

    private final ServicesReadersSQL servicesReadersSQL;
    private final ObjectProperty<AddReaderState> state;

    @Inject
    public AddReaderScreenViewModel(ServicesReadersSQL servicesReadersSQL) {
        this.servicesReadersSQL = servicesReadersSQL;
        state = new SimpleObjectProperty<>(new AddReaderState(null, null));
    }

    public ObjectProperty<AddReaderState> getState() {
        return state;
    }

    public void getReaders() {
        servicesReadersSQL.getAllReaders()
                .observeOn(Schedulers.single())
                .subscribe(either -> {
                    if (either.isRight()) {
                        state.set(new AddReaderState(null, either.get()));
                    } else {
                        state.set(new AddReaderState(either.getLeft(), null));
                    }
                });
    }

    public void addReader(Reader reader) {
        servicesReadersSQL.saveReader(reader)
                .subscribeOn(Schedulers.single())
                .subscribe(either -> {
                    if (either.isRight()) {
                        List<Reader> readers = state.get().readers;
                        readers.add(either.get());
                        state.set(new AddReaderState(null, readers));
                    } else {
                        state.set(new AddReaderState(either.getLeft(), null));
                    }
                });
    }
}