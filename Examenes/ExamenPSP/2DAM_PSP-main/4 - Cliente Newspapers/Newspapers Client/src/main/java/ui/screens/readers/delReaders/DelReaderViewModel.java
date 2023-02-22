package ui.screens.readers.delReaders;

import model.Reader;
import serivces.impl.ReaderServicesImpl;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import ui.screens.common.ScreenConstants;

import java.util.List;

public class DelReaderViewModel {
    private ReaderServicesImpl readerServicesImpl;

    @Inject
    public DelReaderViewModel(ReaderServicesImpl readerServicesImpl) {
        this.readerServicesImpl = readerServicesImpl;
        _state = new SimpleObjectProperty<>(new DelReaderState(null, null, null, null, null));
    }

    private final ObjectProperty<DelReaderState> _state;

    public ReadOnlyObjectProperty<DelReaderState> getState() {
        return _state;
    }

    public void loadScreen() {
        DelReaderState dr;
        Either<String, List<Reader>> readerList = readerServicesImpl.getAll();
        if (readerList.isRight()) {
            dr = new DelReaderState(null, readerList.get(), null, null, null);
        } else {
            dr = new DelReaderState(readerList.getLeft(), null, null, null, null);
        }
        _state.setValue(dr);
    }

    public void validateReader(Reader reader) {
        Either<String, Reader> readerDelete = readerServicesImpl.get(reader);
        if (readerDelete.isRight()) {
            _state.setValue(new DelReaderState(null, _state.getValue().getReaderList(), readerDelete.get(), ScreenConstants.ARE_YOU_SURE_YOU_WANT_TO_DELETE_THIS_READER_WITH_ALL_ITS_READ_ARTICLES_AND_LOGIN, null));
        } else {
            _state.setValue(new DelReaderState(readerDelete.getLeft(), _state.get().getReaderList(), null, null, null));
        }
    }

    public void deleteReader() {
        Either<String, String> readerDeleteSuccess = readerServicesImpl.delete(_state.getValue().getReaderSelected());
        if (readerDeleteSuccess.isRight()) {
            _state.setValue(new DelReaderState(null, null, null, null, readerDeleteSuccess.get()));
            loadScreen();
        } else {
            _state.setValue(new DelReaderState(readerDeleteSuccess.getLeft(), null, null, null, null));
        }
    }
}


