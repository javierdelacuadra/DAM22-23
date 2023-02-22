package ui.screens.newspapers.listnews;

import domain.serivces.impl.NewspapersServicesImpl;
import domain.serivces.impl.ReaderServicesImpl;
import io.reactivex.rxjava3.schedulers.Schedulers;
import jakarta.inject.Inject;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import org.miutils.domain.modelo.Newspaper;
import ui.screens.common.ScreenConstants;

import java.util.List;


public class ListNewsViewModel {

    NewspapersServicesImpl newspaperServices;
    ReaderServicesImpl readerServices;


    @Inject
    public ListNewsViewModel(NewspapersServicesImpl newspaperServices, ReaderServicesImpl readerServices) {
        this.newspaperServices = newspaperServices;
        this.readerServices = readerServices;
        _state = new SimpleObjectProperty<>(new ListNewsState(null, null, null, null, false, null, null));
    }


    private final ObjectProperty<ListNewsState> _state;

    public ReadOnlyObjectProperty<ListNewsState> getState() {
        return _state;
    }


    public void loadNewspapers() {
        setLoadingTrue();
        newspaperServices.getAll()
                .observeOn(Schedulers.single())
                .subscribe(newspapers -> {
                    if (newspapers.isRight()) {
                        _state.setValue(new ListNewsState(null, newspapers.get(), null, null, false, null, null));
                    } else {
                        _state.setValue(new ListNewsState(newspapers.getLeft(), null, null, null, false, null, null));
                    }
                });
    }

    private void setLoadingTrue() {
        ListNewsState ls = _state.getValue();
        ls = new ListNewsState(ls.getError(), ls.getNewspapers(), ls.getNewspaperSelected(), ls.getOldestReaders(), true, null, null);
        _state.setValue(ls);
    }

    public void setNewsSelected(Newspaper newsNewSelected) {
        if (newsNewSelected != null) {
            ListNewsState an = _state.getValue();
            an.setNewspaperSelected(newsNewSelected);
            _state.setValue(an);
        }
    }

    public void getOldestReaders() {
        setLoadingTrue();
        if (_state.getValue().getNewspaperSelected() != null) {
            readerServices.getOldestReaders(_state.getValue().getNewspaperSelected())
                    .observeOn(Schedulers.single())
                    .subscribe(readers -> {
                        if (readers.isRight()) {
                            if (!readers.get().isEmpty())
                                _state.setValue(new ListNewsState(null, _state.getValue().getNewspapers(), _state.getValue().getNewspaperSelected(), readers.get(), false, null, null));
                            else
                                _state.setValue(new ListNewsState(ScreenConstants.NO_READERS, _state.getValue().getNewspapers(), _state.getValue().getNewspaperSelected(), null, false, null, null));
                        } else {
                            _state.setValue(new ListNewsState(readers.getLeft(), _state.getValue().getNewspapers(), _state.getValue().getNewspaperSelected(), null, false, null, null));
                        }
                    });
        } else {
            _state.setValue(new ListNewsState(ScreenConstants.YOU_MUST_SELECT_A_NEWS, _state.getValue().getNewspapers(), _state.getValue().getNewspaperSelected(), null, false, null, null));
        }

    }

    public void deleteNewspaper(boolean confirmed) {
        Newspaper newspaper = _state.getValue().getNewspaperSelected();
        if (newspaper != null) {
            if (confirmed) {
                setLoadingTrue();
                newspaperServices.delete(_state.getValue().getNewspaperSelected())
                        .observeOn(Schedulers.single())
                        .subscribe(responses -> {
                            ListNewsState state = _state.getValue();
                            ListNewsState newState;
                            if (responses.isRight()) {
                                List<Newspaper> currentNews = state.getNewspapers();
                                Newspaper currentSelected = state.getNewspaperSelected();
                                currentNews.remove(currentSelected);
                                newState = (new ListNewsState(null, currentNews, null, state.getOldestReaders(), false, null, currentSelected.getName_newspaper() + ScreenConstants.HAS_BEEN_DELETED));
                            } else {
                                String error = responses.getLeft();
                                newState = new ListNewsState(error, state.getNewspapers(), state.getNewspaperSelected(),
                                        state.getOldestReaders(), false, null, null);
                            }
                            _state.setValue(newState);
                        });


            } else {
                _state.setValue(new ListNewsState(null, _state.getValue().getNewspapers(), _state.getValue().getNewspaperSelected(), _state.getValue().getOldestReaders(), false, ScreenConstants.ARE_YOU_SURE_YOU_WANT_TO_DELETE_THIS_NEWSPAPER_WITH_ALL_ITS_ARTICLES, null));
            }
        }
        else {
            _state.setValue(new ListNewsState(ScreenConstants.PLEASE_SELECT_A_NEWSPAPER, _state.getValue().getNewspapers(), _state.getValue().getNewspaperSelected(), null, false, null, null));
        }
    }
    public void clearMessages() {
        _state.setValue(new ListNewsState(null, _state.getValue().getNewspapers(), _state.getValue().getNewspaperSelected(), _state.get().getOldestReaders(),false, null, null));
    }
}





