package ui.screens.newspapers.listNews;

import io.vavr.control.Either;
import jakarta.inject.Inject;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import model.Newspaper;
import serivces.impl.NewspapersServicesImpl;
import serivces.impl.ReaderServicesImpl;
import ui.screens.common.ScreenConstants;

import java.util.List;


public class ListNewsViewModel {

    NewspapersServicesImpl newspaperServices;
    ReaderServicesImpl readerServices;



    @Inject
    public ListNewsViewModel(NewspapersServicesImpl newspaperServices, ReaderServicesImpl readerServices) {
        this.newspaperServices = newspaperServices;
        this.readerServices = readerServices;
        _state = new SimpleObjectProperty<>(new ListNewsState(null,null, null, null));
    }


    private final ObjectProperty<ListNewsState> _state;
    public ReadOnlyObjectProperty<ListNewsState> getState() {
        return _state;
    }


    public void loadNewspapers() {
        ListNewsState an;
        Either<String, List<Newspaper>> newspapers = newspaperServices.getAll();
        if (newspapers.isRight()) {
            an = new ListNewsState(null, newspapers.get(), newspapers.get().get(0), null);
        } else {
            an = new ListNewsState(newspapers.getLeft(), null, null, null);
        }
        _state.setValue(an);
    }

    public void setNewsSelected(Newspaper newsNewSelected) {
        if (newsNewSelected != null) {
            ListNewsState an = _state.getValue();
            an.setNewspaperSelected(newsNewSelected);
            _state.setValue(an);
        }
    }

    public void getOldestReaders() {
        ListNewsState an;
        if (_state.getValue().getNewspaperSelected() != null) {
            Either<String, List<String>> readers = readerServices.getOldestReaders(_state.getValue().getNewspaperSelected());
            if (readers.isRight()) {
                an = new ListNewsState(null, _state.getValue().getNewspapers(), _state.getValue().getNewspaperSelected(), readers.get());
            } else {
                an = new ListNewsState(readers.getLeft(), _state.getValue().getNewspapers(), _state.getValue().getNewspaperSelected(), null);
            }
        } else {
            an = new ListNewsState(ScreenConstants.YOU_MUST_SELECT_A_NEWS, _state.getValue().getNewspapers(), _state.getValue().getNewspaperSelected(), null);
        }
        _state.setValue(an);
    }
}
