package ui.screens.readers.listReaders;

import model.Newspaper;
import model.Reader;
import model.TypeArt;
import serivces.impl.*;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.util.List;

public class ListReadersViewModel {

    ReaderServicesImpl readerServicesImpl;
    TypeServicesImpl typeServicesImpl;

    ArticleServicesImpl articleServicesImpl;

    NewspapersServicesImpl newspapersServicesImpl;

    @Inject
    public ListReadersViewModel(ReaderServicesImpl readerServicesImpl, TypeServicesImpl typeServicesImpl, ArticleServicesImpl articleServicesImpl, NewspapersServicesImpl newspapersServicesImpl) {
        this.readerServicesImpl = readerServicesImpl;
        this.typeServicesImpl = typeServicesImpl;
        this.articleServicesImpl = articleServicesImpl;
        this.newspapersServicesImpl = newspapersServicesImpl;
        _state = new SimpleObjectProperty<>(new ListReadersState(null, null, null, null, null, null));
    }

    private final ObjectProperty<ListReadersState> _state;

    public ReadOnlyObjectProperty<ListReadersState> getState() {
        return _state;
    }

    public void loadScreen() {
        ListReadersState es;
        Either<String, List<Reader>> list = readerServicesImpl.getAll();
        if (list.isRight()) {
            Either<String, List<TypeArt>> listTypeArts = typeServicesImpl.getAllTypes();
            if (listTypeArts.isRight()) {
                Either<String, List<Reader>> listReadersFilteredType = readerServicesImpl.getReadersByType(listTypeArts.get().get(0));
                Either<String, List<Newspaper>> newspapers = newspapersServicesImpl.getAll();
                if (newspapers.isRight()) {
                    if (listReadersFilteredType.isRight()) {
                        Either<String, List<Reader>> listReadersFilteredNews = readerServicesImpl.getReadersByNewspaper(newspapers.get().get(0));
                        if (listReadersFilteredNews.isRight()){
                            es = new ListReadersState(null, list.get(), listTypeArts.get(), listReadersFilteredType.get(), newspapers.get(), listReadersFilteredNews.get());
                        }
                        else {
                            es = new ListReadersState(listReadersFilteredNews.getLeft(), list.get(), listTypeArts.get(), listReadersFilteredType.get(), newspapers.get(), null);
                        }
                    } else {
                        es = new ListReadersState(listReadersFilteredType.getLeft(), list.get(), listTypeArts.get(), null, newspapers.get(), null);
                    }
                } else {
                    es = new ListReadersState(newspapers.getLeft(), null, null, null, null, null);
                }
            } else {
                es = new ListReadersState(listTypeArts.getLeft(), null, null, null, null, null);
            }
        } else {
            es = new ListReadersState(list.getLeft(), null, null, null, null, null);
        }
        _state.setValue(es);
    }

    public void loadReadersByType(TypeArt typeArt) {
        ListReadersState lr;
        Either<String, List<Reader>> list = readerServicesImpl.getReadersByType(typeArt);
        if (list.isRight()) {
            lr = new ListReadersState(null, _state.get().getReaders(), _state.get().getTypeArts(), list.get(), _state.get().getNewspaperList(), _state.getValue().getReadersFilteredNewspaper());
        } else {
            lr = new ListReadersState(list.getLeft(), _state.get().getReaders(), _state.get().getTypeArts(), null, _state.get().getNewspaperList(), _state.get().getReadersFilteredNewspaper());
        }
        _state.setValue(lr);
    }

    public void loadReadersByNews(Newspaper newspaper) {
        ListReadersState es;
        Either<String, List<Reader>> list = readerServicesImpl.getReadersByNewspaper(newspaper);
        if (list.isRight()) {
            es = new ListReadersState(null, _state.get().getReaders(), _state.get().getTypeArts(), _state.get().getReadersFilteredType(), _state.get().getNewspaperList(), list.get());
        } else {
            es = new ListReadersState(list.getLeft(), _state.get().getReaders(), _state.get().getTypeArts(), _state.get().getReadersFilteredType(), _state.get().getNewspaperList(), null);
        }
        _state.setValue(es);
    }
}
