package ui.screens.readers.updreaders;

import domain.serivces.impl.ReaderServicesImpl;
import io.reactivex.rxjava3.schedulers.Schedulers;
import jakarta.inject.Inject;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import org.miutils.domain.modelo.Login;
import org.miutils.domain.modelo.Reader;
import ui.screens.common.ScreenConstants;

import java.time.LocalDate;
import java.util.List;

public class UpdReaderViewModel {

    private final ReaderServicesImpl readerServicesImpl;

    @Inject
    public UpdReaderViewModel(ReaderServicesImpl readerServicesImpl) {
        this.readerServicesImpl = readerServicesImpl;
        _state = new SimpleObjectProperty<>(new UpdReaderState(null, null, null, null, false, null));
    }

    private final ObjectProperty<UpdReaderState> _state;

    public ReadOnlyObjectProperty<UpdReaderState> getState() {
        return _state;
    }


    public void loadScreen() {
        setLoadingTrue();
        readerServicesImpl.getAll()
                .observeOn(Schedulers.single())
                .subscribe(either -> {
                    UpdReaderState ur;
                    if (either.isRight()) {
                        ur = new UpdReaderState(null, null, null, either.get(), false, null);
                    } else {
                        ur = new UpdReaderState(either.getLeft(), null, null, null, false, null);
                    }
                    _state.setValue(ur);
                });
    }

    public void getReaderDetails(Reader selectedReader, int position) {
        if (_state.getValue().getAllReaders().contains(selectedReader)) {
            _state.setValue(new UpdReaderState(null, selectedReader, position, _state.get().getAllReaders(), false, null));
        } else {
            _state.setValue(new UpdReaderState(ScreenConstants.INVALID_READER, null, null, _state.get().getAllReaders(), false, null));
        }
    }

    public void clearMessages() {
        _state.setValue(new UpdReaderState(null, _state.getValue().getReaderSelected(), _state.getValue().getReaderPosition(), _state.get().getAllReaders(), false, null));
    }

    public void updateReader(String name, LocalDate birthDate, String user, String pass) {
        Reader current = _state.getValue().getReaderSelected();
        if (current != null && current.getId() > 0) {
            int idCurrentReader =current.getLogin().getIdReader();
            Reader finalNewReader = new Reader(idCurrentReader, name, birthDate, new Login(user, pass, idCurrentReader));
            setLoadingTrue();
            readerServicesImpl.update(finalNewReader)
                    .observeOn(Schedulers.single())
                    .subscribe(readerUpd -> {
                        UpdReaderState ur;
                        if (readerUpd.isRight()) {
                            // change the new reader
                            List<Reader> currentReaders = _state.get().getAllReaders();
                            currentReaders.set(_state.getValue().getReaderPosition(), finalNewReader);
                            ur = new UpdReaderState(null, finalNewReader, _state.getValue().getReaderPosition(), currentReaders, false, finalNewReader.getName() + ScreenConstants.UPDATED_SUCCESSFULLY);
                        } else {
                            ur = new UpdReaderState(readerUpd.getLeft(), _state.get().getReaderSelected(), _state.getValue().getReaderPosition(), _state.get().getAllReaders(), false, null);
                        }
                        _state.setValue(ur);
                    });
        } else {
            _state.setValue(new UpdReaderState(ScreenConstants.INVALID_READER, null, null, _state.get().getAllReaders(), false, null));
        }
    }

    public void addReader(Reader newReader) {
        setLoadingTrue();
        readerServicesImpl.add(newReader)
                .observeOn(Schedulers.single())
                .subscribe(readerAdd -> {
                    UpdReaderState ar;
                    if (readerAdd.isRight()) {
                        List<Reader> currentReaders = _state.get().getAllReaders();
                        currentReaders.add(readerAdd.get());
                        ar = new UpdReaderState(null, newReader, currentReaders.size() - 1, currentReaders, false, ScreenConstants.WELCOME + readerAdd.get().getName());
                    } else {
                        ar = new UpdReaderState(readerAdd.getLeft(), null, null, _state.get().getAllReaders(), false, null);
                    }
                    _state.setValue(ar);
                });
    }

    private void setLoadingTrue() {
        UpdReaderState ls = _state.getValue();
        ls = new UpdReaderState(ls.getError(), ls.getReaderSelected(), ls.getReaderPosition(), ls.getAllReaders(), true, ls.getConfirmation());
        _state.setValue(ls);
    }
}
