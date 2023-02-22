package ui.screens.newspapers.addNews;

import io.vavr.control.Either;
import jakarta.inject.Inject;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import model.Newspaper;
import model.Reader;
import serivces.impl.NewspapersServicesImpl;

import java.util.List;

public class AddNewsViewModel {

    private NewspapersServicesImpl newspapersServices;

    @Inject
    public AddNewsViewModel(NewspapersServicesImpl newspapersServices) {
        this.newspapersServices = newspapersServices;
        _state = new SimpleObjectProperty<>(new AddNewsState(null, null, null));
    }

    private final ObjectProperty<AddNewsState> _state;

    public ReadOnlyObjectProperty<AddNewsState> getState() {
        return _state;
    }

    public void addNews(Newspaper newspaper) {
        AddNewsState ar;

        Either<String, String> newsAdd = newspapersServices.add(newspaper);
        if (newsAdd.isRight()) {
            List<Newspaper> allNews = _state.get().getAllNews();
            allNews.add(newspaper);
            ar = new AddNewsState(null, allNews, newsAdd.get());
        } else {
            ar = new AddNewsState(newsAdd.getLeft(), _state.get().getAllNews(), null);
        }
        _state.setValue(ar);
    }


    public void clearState() {
        _state.setValue(new AddNewsState(null, _state.get().getAllNews(), null));
    }

    public void loadScreen() {
        Either<String, List<Newspaper>> allReaders = newspapersServices.getAll();
        if (allReaders.isRight()) {
            _state.setValue(new AddNewsState(null, allReaders.get(), null));
        }
        else {
            _state.setValue(new AddNewsState(allReaders.getLeft(), null, null));
        }
    }
}
