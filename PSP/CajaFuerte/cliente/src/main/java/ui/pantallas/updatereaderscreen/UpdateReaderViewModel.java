package ui.pantallas.updatereaderscreen;

import io.reactivex.rxjava3.schedulers.Schedulers;
import jakarta.inject.Inject;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import modelo.Reader;
import servicios.ServicesReadersSQL;

import java.util.List;

public class UpdateReaderViewModel {

    private final ServicesReadersSQL servicesReadersSQL;
    private final ObjectProperty<UpdateReaderState> state;

    @Inject
    public UpdateReaderViewModel(ServicesReadersSQL servicesReadersSQL) {
        this.servicesReadersSQL = servicesReadersSQL;
        state = new SimpleObjectProperty<>(new UpdateReaderState(null, null));
    }

    public ObjectProperty<UpdateReaderState> getState() {
        return state;
    }

    public void getReaders() {
        servicesReadersSQL.getAllReaders()
                .observeOn(Schedulers.single())
                .subscribe(either -> {
                    if (either.isRight()) {
                        state.set(new UpdateReaderState(null, either.get()));
                    } else {
                        state.set(new UpdateReaderState(either.getLeft(), null));
                    }
                });
    }

    public void updateReader(Reader reader) {
        servicesReadersSQL.updateReader(reader)
                .subscribeOn(Schedulers.single())
                .subscribe(either -> {
                    if (either.isRight()) {
                        List<Reader> readers = state.get().readers;
                        readers.stream()
                                .filter(r -> r.getId() == (reader.getId()))
                                .findFirst()
                                .ifPresent(r -> {
                                    r.setName(reader.getName());
                                    r.setDateOfBirth(reader.getDateOfBirth());
                                });
                        state.set(new UpdateReaderState(null, readers));
                    } else {
                        state.set(new UpdateReaderState(either.getLeft(), null));
                    }
                });
    }

}