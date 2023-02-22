package ui.screens.newspapers.updnews;

import domain.serivces.impl.NewspapersServicesImpl;
import io.reactivex.rxjava3.schedulers.Schedulers;
import jakarta.inject.Inject;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import org.miutils.domain.modelo.Newspaper;
import ui.screens.common.ScreenConstants;

import java.time.LocalDate;
import java.util.List;

public class UpdNewsViewModel {

    private final NewspapersServicesImpl newspapersServices;

    @Inject
    public UpdNewsViewModel(NewspapersServicesImpl newspapersServices) {
        this.newspapersServices = newspapersServices;
        _state = new SimpleObjectProperty<>(new UpdNewsState(null, null, null, null,false, null));
    }

    private final ObjectProperty<UpdNewsState> _state;

    public ReadOnlyObjectProperty<UpdNewsState> getState() {
        return _state;
    }

    public void loadScreen() {
        setLoadingTrue();
        newspapersServices.getAll()
                .observeOn(Schedulers.single())
                .subscribe(either -> {
                    UpdNewsState ur;
                    if (either.isRight()) {
                        ur = new UpdNewsState(null, null, null, either.get(), false, null);
                    } else {
                        ur = new UpdNewsState(either.getLeft(), null, null, null, false, null);
                    }
                    _state.setValue(ur);
                });
    }

    public void getNewsDetails(Newspaper selectedNews, int position) {
        if (_state.getValue().getAllNewspapers().contains(selectedNews)) {
            _state.setValue(new UpdNewsState(null, selectedNews, position, _state.get().getAllNewspapers(), false, null));
        } else {
            _state.setValue(new UpdNewsState(ScreenConstants.INVALID_READER, null, null, _state.get().getAllNewspapers(), false, null));
        }
    }

    public void clearMessages() {
        _state.setValue(new UpdNewsState(null, _state.getValue().getNewsSelected(), _state.getValue().getNewsPosition(), _state.get().getAllNewspapers(),false, null));
    }

    public void updateNews(String name, LocalDate releaseDate) {
        Newspaper current = _state.getValue().getNewsSelected();
        if (current != null && current.getId() > 0) {
            int idCurrentNews = current.getId();
            Newspaper finalNews = new Newspaper(idCurrentNews, name, releaseDate);
            setLoadingTrue();
            newspapersServices.update(finalNews)
                    .observeOn(Schedulers.single())
                    .subscribe(newsAdd -> {
                        UpdNewsState ur;
                        if (newsAdd.isRight()) {
                            List<Newspaper> currentNews = _state.get().getAllNewspapers();
                            currentNews.set(_state.getValue().getNewsPosition(), finalNews);
                            ur = new UpdNewsState(null, finalNews, _state.getValue().getNewsPosition(), currentNews, false, finalNews.getName_newspaper() + ScreenConstants.UPDATED_SUCCESSFULLY);
                        } else {
                                ur = new UpdNewsState(newsAdd.getLeft(), _state.get().getNewsSelected(), _state.getValue().getNewsPosition(), _state.get().getAllNewspapers(), false, null);
                            }
                            _state.setValue(ur);
                    });
        } else {
            _state.setValue(new UpdNewsState(ScreenConstants.PLEASE_SELECT_A_NEWSPAPER, null, null, _state.get().getAllNewspapers(), false, null));
        }
    }

    public void addNews(Newspaper newspaper) {
        setLoadingTrue();
        newspapersServices.add(newspaper)
                .observeOn(Schedulers.single())
                .subscribe(newsAdd -> {
                    UpdNewsState ur;
                    if (newsAdd.isRight()) {
                        List<Newspaper> allNews = _state.get().getAllNewspapers();
                        allNews.add(newsAdd.get());
                        ur = new UpdNewsState(null, newspaper, allNews.size() - 1, allNews, false, ScreenConstants.SE_AGREGO + newsAdd.get().getName_newspaper());
                    } else {
                        ur = new UpdNewsState(newsAdd.getLeft(), null, null, _state.get().getAllNewspapers(), false, null);
                    }
                    _state.setValue(ur);
                });
    }

    private void setLoadingTrue() {
        UpdNewsState ls = _state.getValue();
        ls = new UpdNewsState(ls.getError(), ls.getNewsSelected(), ls.getNewsPosition(), ls.getAllNewspapers(), true, null);
        _state.setValue(ls);
    }
}
