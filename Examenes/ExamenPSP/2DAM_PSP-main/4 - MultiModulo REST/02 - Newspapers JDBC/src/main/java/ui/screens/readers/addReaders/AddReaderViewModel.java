package ui.screens.readers.addReaders;

import model.Reader;
import serivces.impl.ReaderServicesImpl;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import ui.screens.common.ScreenConstants;

import java.util.List;

public class AddReaderViewModel {

    private ReaderServicesImpl readerServices;

    @Inject
    public AddReaderViewModel(ReaderServicesImpl readerServices) {
        this.readerServices = readerServices;
        _state = new SimpleObjectProperty<>(new AddReaderState(null, null, null));
    }

    private final ObjectProperty<AddReaderState> _state;

    public ReadOnlyObjectProperty<AddReaderState> getState() {
        return _state;
    }

    public void addReader(Reader newReader) {
        AddReaderState ar;

        Either<String, Reader> readerAdd = readerServices.add(newReader);
        if (readerAdd.isRight()) {
            List<Reader> currentReaders = _state.get().getAllReaders();
            currentReaders.add(readerAdd.get());
            ar = new AddReaderState(null, currentReaders, ScreenConstants.WELCOME + readerAdd.get().getName());
        } else {
            ar = new AddReaderState(readerAdd.getLeft(), _state.get().getAllReaders(), null);
        }
        _state.setValue(ar);
    }


    public void clearState() {
        _state.setValue(new AddReaderState(null, _state.get().getAllReaders(), null));
    }

    public void loadScreen() {
        Either<String, List<Reader>> allReaders = readerServices.getAll();
        if (allReaders.isRight()) {
            _state.setValue(new AddReaderState(null, allReaders.get(), null));
        }
        else {
            _state.setValue(new AddReaderState(allReaders.getLeft(), null, null));
        }
    }
}
