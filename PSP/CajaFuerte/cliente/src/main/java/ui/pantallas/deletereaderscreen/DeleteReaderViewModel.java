package ui.pantallas.deletereaderscreen;

import io.reactivex.rxjava3.schedulers.Schedulers;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import modelo.Reader;
import servicios.ServicesReadersSQL;

import java.util.List;

public class DeleteReaderViewModel {

    private final ServicesReadersSQL servicesReadersSQL;
    private final ObjectProperty<DeleteReaderState> state;

    @Inject
    public DeleteReaderViewModel(ServicesReadersSQL servicesReadersSQL) {
        this.servicesReadersSQL = servicesReadersSQL;
        state = new SimpleObjectProperty<>(new DeleteReaderState(null, null));
    }

    public ObjectProperty<DeleteReaderState> getState() {
        return state;
    }

    public void getReaders() {
        servicesReadersSQL.getAllReaders()
                .observeOn(Schedulers.single())
                .subscribe(either -> {
                    if (either.isRight()) {
                        state.set(new DeleteReaderState(null, either.get()));
                    } else {
                        state.set(new DeleteReaderState(either.getLeft(), null));
                    }
                });
    }

    public Either<String, Boolean> deleteReader(String id) {
        servicesReadersSQL.deleteReader(id)
                .observeOn(Schedulers.single())
                .subscribe(either -> {
                    if (either.isRight()) {
                        List<Reader> readers = state.get().readers;
                        readers.removeIf(reader -> reader.getId() == Integer.parseInt(id));
                        state.set(new DeleteReaderState(null, readers));
                    } else {
                        state.set(new DeleteReaderState(either.getLeft(), null));
                    }
                });
        return Either.right(true);
    }
}
