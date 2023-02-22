package ui.screens.readers.updReaders;

import model.Login;
import model.Reader;
import serivces.impl.ReaderServicesImpl;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import ui.screens.common.ScreenConstants;

import java.time.LocalDate;
import java.util.List;

public class UpdReaderViewModel {

    private ReaderServicesImpl readerServicesImpl;

    @Inject
    public UpdReaderViewModel(ReaderServicesImpl readerServicesImpl) {
        this.readerServicesImpl = readerServicesImpl;
        _state = new SimpleObjectProperty<>(new UpdReaderState(null, null, null, null, null));
    }

    private final ObjectProperty<UpdReaderState> _state;

    public ReadOnlyObjectProperty<UpdReaderState> getState() {
        return _state;
    }


    public void loadScreen() {
        UpdReaderState ur;
        Either<String, List<Reader>> either = readerServicesImpl.getAll();
        if (either.isRight()) {
            ur = new UpdReaderState(null, either.get().get(0), 0, either.get(), null);
        } else {
            ur = new UpdReaderState(either.getLeft(), null,  null, null, null);
        }
        _state.setValue(ur);
    }

    public void getReaderDetails(Reader selectedReader, int position) {
        if (_state.getValue().getAllReaders().contains(selectedReader)) {
            _state.setValue(new UpdReaderState(null, selectedReader, position, _state.get().getAllReaders(), null));
        } else {
            _state.setValue(new UpdReaderState(ScreenConstants.INVALID_READER, null, null, _state.get().getAllReaders(), null));
        }
    }

    public void clearMessages() {
        _state.setValue(new UpdReaderState(null, _state.getValue().getReaderSelected(), _state.getValue().getReaderPosition(), _state.get().getAllReaders(), null));
    }

    public void updateReader(String name, LocalDate birthDate, String user, String pass) {
        UpdReaderState ur;
        int idCurrentReader =  _state.getValue().getReaderSelected().getLogin().getIdReader();
        Reader finalNewReader = new Reader(idCurrentReader, name, birthDate, new Login(user, pass, idCurrentReader));
        Either<String, Reader> readerAdd = readerServicesImpl.update(finalNewReader);
        if (readerAdd.isRight()) {
            // change the new reader
            List<Reader> currentReaders = _state.get().getAllReaders();
            currentReaders.set(_state.getValue().getReaderPosition(), finalNewReader);
            ur = new UpdReaderState(null, finalNewReader, _state.getValue().getReaderPosition(), currentReaders, finalNewReader.getName() + ScreenConstants.UPDATED_SUCCESSFULLY);
        } else {
            ur = new UpdReaderState(readerAdd.getLeft(), _state.get().getReaderSelected(),_state.getValue().getReaderPosition(),  _state.get().getAllReaders(), null);
        }
        _state.setValue(ur);
    }
}
