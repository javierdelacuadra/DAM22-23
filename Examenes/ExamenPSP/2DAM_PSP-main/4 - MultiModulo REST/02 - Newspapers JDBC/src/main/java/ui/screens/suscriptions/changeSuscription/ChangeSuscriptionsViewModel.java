package ui.screens.suscriptions.changeSuscription;

import io.vavr.control.Either;
import jakarta.enterprise.inject.New;
import jakarta.inject.Inject;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import model.Newspaper;
import model.Reader;
import serivces.impl.NewspapersServicesImpl;
import serivces.impl.SuscriptionsServicesImpl;
import ui.screens.common.ScreenConstants;
import ui.screens.readers.listReaders.ListReadersState;

import java.util.ArrayList;
import java.util.List;

public class ChangeSuscriptionsViewModel {

    private NewspapersServicesImpl newspapersServices;
    private SuscriptionsServicesImpl suscriptionsServices;

    @Inject
    public ChangeSuscriptionsViewModel(NewspapersServicesImpl newspapersServices , SuscriptionsServicesImpl suscriptionsServices) {
        this.newspapersServices = newspapersServices;
        this.suscriptionsServices = suscriptionsServices;
        _state = new SimpleObjectProperty<>(new ChangeSuscriptionsState(null, null, null, null, null, null, null));
    }

    private final ObjectProperty<ChangeSuscriptionsState> _state;

    public ReadOnlyObjectProperty<ChangeSuscriptionsState> getState() {
        return _state;
    }

    public void loadScreen(Reader currentReader) {
        ChangeSuscriptionsState es;
        Either<String, List<Newspaper>> suscribedList = newspapersServices.getAll(currentReader.getId(), true);
        Either<String, List<Newspaper>> unsuscribedList = newspapersServices.getAll(currentReader.getId(), false);
        if (suscribedList.isRight() && unsuscribedList.isRight()) {
            es = new ChangeSuscriptionsState(null, suscribedList.get(), unsuscribedList.get(), currentReader, null, null, null);
        } else {
            if (suscribedList.isLeft()){
                es = new ChangeSuscriptionsState(suscribedList.getLeft(), new ArrayList<>(), unsuscribedList.getOrElse(new ArrayList<>()), currentReader, null, null, null);
            }
            else {
                es = new ChangeSuscriptionsState(unsuscribedList.getLeft(), suscribedList.getOrElse(new ArrayList<>()), new ArrayList<>(), currentReader, null, null, null);
            }
        }
        _state.setValue(es);
    }

    public void getNewsSelected(Newspaper newspaper, boolean isSuscribed) {
        ChangeSuscriptionsState es = _state.getValue();
        if (newspaper != null) {
            es = new ChangeSuscriptionsState(null, es.getNewsSuscribed(), es.getNewsNotSuscribed(), es.getCurrentReader(), newspaper, isSuscribed, null);
            _state.setValue(es);
        }
    }

    public void changeNewsState() {
        ChangeSuscriptionsState es = _state.getValue();
        if (es.getNewspaperSelected() != null) {
            Newspaper newspaperSelected = es.getNewspaperSelected();
            boolean isSuscribed = es.getIsSuscribedToNewsSelected();
            Either<String, String> result = suscriptionsServices.changeSuscription(es.getCurrentReader().getId(), newspaperSelected.getId(), isSuscribed);
            if (result.isRight()) {

                // UPDATE TABLES LISTS
                List<Newspaper> suscribedNews = es.getNewsSuscribed();
                List<Newspaper> unsuscribedNews = es.getNewsNotSuscribed();
                if (isSuscribed){
                    suscribedNews.remove(newspaperSelected);
                    unsuscribedNews.add(newspaperSelected);
                } else {
                    unsuscribedNews.remove(newspaperSelected);
                    suscribedNews.add(newspaperSelected);
                }
                // UPDATE STATE
                es = new ChangeSuscriptionsState(null, suscribedNews, unsuscribedNews, es.getCurrentReader(), null, null, result.get() + ScreenConstants.SPACE + newspaperSelected.getName_newspaper());
            } else {
                es = new ChangeSuscriptionsState(result.getLeft(), es.getNewsSuscribed(), es.getNewsNotSuscribed(), es.getCurrentReader(), null, null, null);
            }
        } else {
            es = new ChangeSuscriptionsState(ScreenConstants.THE_NEWSPAPER_SELECTED_IS_NOT_VALID, es.getNewsSuscribed(), es.getNewsNotSuscribed(), es.getCurrentReader(), null, null, null);
        }
        _state.setValue(es);
    }

    public void clearMessages() {
        ChangeSuscriptionsState es = _state.getValue();
        es = new ChangeSuscriptionsState(null, es.getNewsSuscribed(), es.getNewsNotSuscribed(), es.getCurrentReader(), es.getNewspaperSelected(), es.getIsSuscribedToNewsSelected(), null);
        _state.setValue(es);
    }
}
