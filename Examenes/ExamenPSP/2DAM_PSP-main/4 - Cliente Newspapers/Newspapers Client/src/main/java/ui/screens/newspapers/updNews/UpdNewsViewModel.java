package ui.screens.newspapers.updNews;

import io.vavr.control.Either;
import jakarta.inject.Inject;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import model.Newspaper;
import serivces.impl.NewspapersServicesImpl;
import ui.screens.common.ScreenConstants;

import java.time.LocalDate;
import java.util.List;

public class UpdNewsViewModel {

    private NewspapersServicesImpl newspapersServices;

    @Inject
    public UpdNewsViewModel(NewspapersServicesImpl newspapersServices) {
        this.newspapersServices = newspapersServices;
        _state = new SimpleObjectProperty<>(new UpdNewsState(null, null, null, null, null));
    }

    private final ObjectProperty<UpdNewsState> _state;

    public ReadOnlyObjectProperty<UpdNewsState> getState() {
        return _state;
    }

    public void loadScreen() {
        UpdNewsState ur;
        Either<String, List<Newspaper>> either = newspapersServices.getAll();
        if (either.isRight()) {
            ur = new UpdNewsState(null, either.get().get(0), 0, either.get(), null);
        } else {
            ur = new UpdNewsState(either.getLeft(), null,  null, null, null);
        }
        _state.setValue(ur);
    }

    public void getNewsDetails(Newspaper selectedNews, int position) {
        if (_state.getValue().getAllNewspapers().contains(selectedNews)) {
            _state.setValue(new UpdNewsState(null, selectedNews, position, _state.get().getAllNewspapers(), null));
        } else {
            _state.setValue(new UpdNewsState(ScreenConstants.INVALID_READER, null, null, _state.get().getAllNewspapers(), null));
        }
    }

    public void clearMessages() {
        _state.setValue(new UpdNewsState(null, _state.getValue().getNewsSelected(), _state.getValue().getNewsPosition(), _state.get().getAllNewspapers(), null));
    }

    public void updateReader(String name, LocalDate releaseDate) {
        UpdNewsState ur;
        int idCurrentNews =  _state.getValue().getNewsSelected().getId();
        Newspaper finalNews = new Newspaper(idCurrentNews, name, releaseDate);
        Either<String, Newspaper> newsAdd = newspapersServices.update(finalNews);
        if (newsAdd.isRight()) {
            List<Newspaper> currentNews = _state.get().getAllNewspapers();
            currentNews.set(_state.getValue().getNewsPosition(), finalNews);
            ur = new UpdNewsState(null, finalNews, _state.getValue().getNewsPosition(), currentNews, finalNews.getName_newspaper() + ScreenConstants.UPDATED_SUCCESSFULLY);
        } else {
            ur = new UpdNewsState(newsAdd.getLeft(), _state.get().getNewsSelected(),_state.getValue().getNewsPosition(),  _state.get().getAllNewspapers(), null);
        }
        _state.setValue(ur);
    }

}
