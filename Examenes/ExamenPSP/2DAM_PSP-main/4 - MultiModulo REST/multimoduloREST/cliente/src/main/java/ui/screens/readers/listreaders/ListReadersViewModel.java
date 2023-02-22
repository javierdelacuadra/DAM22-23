package ui.screens.readers.listreaders;

import domain.serivces.impl.NewspapersServicesImpl;
import domain.serivces.impl.ReaderServicesImpl;
import domain.serivces.impl.TypeServicesImpl;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import org.miutils.domain.modelo.Newspaper;
import org.miutils.domain.modelo.Reader;
import org.miutils.domain.modelo.TypeArt;
import ui.screens.common.ScreenConstants;

import java.util.List;

public class ListReadersViewModel {

    ReaderServicesImpl readerServicesImpl;
    TypeServicesImpl typeServicesImpl;


    NewspapersServicesImpl newspapersServicesImpl;

    @Inject
    public ListReadersViewModel(ReaderServicesImpl readerServicesImpl, TypeServicesImpl typeServicesImpl, NewspapersServicesImpl newspapersServicesImpl) {
        this.readerServicesImpl = readerServicesImpl;
        this.typeServicesImpl = typeServicesImpl;
        this.newspapersServicesImpl = newspapersServicesImpl;
        _state = new SimpleObjectProperty<>(ListReadersState.builder()

                .build());
    }

    private final ObjectProperty<ListReadersState> _state;

    public ReadOnlyObjectProperty<ListReadersState> getState() {
        return _state;
    }

    public void loadScreen() {
        //DEJARLO ASI FEO
        setLoadingTrue();
        readerServicesImpl.getAll()
                .subscribeOn(Schedulers.io())
                .subscribe(list -> {
                    if (list.isRight()) {
                        typeServicesImpl.getAllTypes()
                                .observeOn(Schedulers.single())
                                .subscribe(listTypeArts -> getTypes(list, listTypeArts));
                    } else {
                        _state.setValue(ListReadersState.builder().error(list.getLeft()).readers(null).typeArts(null).newspaperList(null).readersFilteredType(null).readersFilteredNewspaper(null).readerSelected(null).isLoading(false).warningMessage(null).successMessage(null).build());
                    }
                });
    }

    private void getTypes(Either<String, List<Reader>> list, Either<String, List<TypeArt>> listTypeArts) {
        if (listTypeArts.isRight()) {
            newspapersServicesImpl.getAll()
                    .observeOn(Schedulers.single())
                    .subscribe(newspapers -> {
                        if (newspapers.isRight()) {
                            getFiltered(list, listTypeArts, newspapers);
                        } else {
                            _state.setValue(ListReadersState.builder().error(newspapers.getLeft()).readers(list.get()).typeArts(listTypeArts.get()).newspaperList(null).readersFilteredType(null).readersFilteredNewspaper(null).readerSelected(_state.getValue().getReaderSelected()).isLoading(false).warningMessage(null).successMessage(null).build());
                        }
                    });
        } else {
            _state.setValue(ListReadersState.builder().error(listTypeArts.getLeft()).readers(list.get()).typeArts(null).newspaperList(null).readersFilteredType(null).readersFilteredNewspaper(null).readerSelected(_state.getValue().getReaderSelected()).isLoading(false).warningMessage(null).successMessage(null).build());
        }
    }

    private void getFiltered(Either<String, List<Reader>> list, Either<String, List<TypeArt>> listTypeArts, Either<String, List<Newspaper>> newspapers) {
        readerServicesImpl.getReadersByType(listTypeArts.get().get(0))
                .subscribeOn(Schedulers.io())
                .subscribe(listReadersFilteredType -> {
                    if (listReadersFilteredType.isRight()) {
                        readerServicesImpl.getReadersByNewspaper(newspapers.get().get(0))
                                .observeOn(Schedulers.single())
                                .subscribe(listReadersFilteredNews -> {
                                    if (listReadersFilteredNews.isRight()) {
                                        ListReadersState ls;
                                        if (!listReadersFilteredNews.get().isEmpty()) {
                                            ls = ListReadersState.builder()
                                                    .error(null)
                                                    .readers(list.get()).typeArts(listTypeArts.get()).newspaperList(newspapers.get()).readersFilteredType(listReadersFilteredType.get()).readersFilteredNewspaper(listReadersFilteredNews.get()).readerSelected(_state.getValue().getReaderSelected()).isLoading(false).warningMessage(null).successMessage(null
                                                    ).build();
                                        } else {
                                            ls = (ListReadersState.builder().error(ScreenConstants.THERE_ARE_NO_READERS_SUSCRIBED_TO_THIS_NEWS).readers(list.get()).typeArts(listTypeArts.get()).newspaperList(newspapers.get()).readersFilteredType(listReadersFilteredType.get()).readersFilteredNewspaper(null).readerSelected(_state.getValue().getReaderSelected()).isLoading(false).warningMessage(null).successMessage(null).build());
                                        }
                                        _state.setValue(ls);
                                    } else {
                                        _state.setValue(ListReadersState.builder()
                                                .error(listReadersFilteredNews.getLeft())
                                                .readers(list.get())
                                                .typeArts(listTypeArts.get())
                                                .newspaperList(newspapers.get())
                                                .readersFilteredType(listReadersFilteredType.get())
                                                .readersFilteredNewspaper(null)
                                                .readerSelected(_state.getValue().getReaderSelected())
                                                .isLoading(false)
                                                .warningMessage(null)
                                                .successMessage(null).build());
                                    }
                                });
                    } else {
                        _state.setValue(ListReadersState.builder()
                                .error(listReadersFilteredType.getLeft())
                                .readers(list.get())
                                .typeArts(listTypeArts.get())
                                .newspaperList(newspapers.get())
                                .readersFilteredType(null)
                                .readersFilteredNewspaper(null)
                                .readerSelected(_state.getValue().getReaderSelected())
                                .isLoading(false)
                                .warningMessage(null)
                                .successMessage(null)
                                .build());
                    }
                });
    }

    public void loadReadersByType(TypeArt typeArt) {
        setLoadingTrue();
        readerServicesImpl.getReadersByType(typeArt)
                .observeOn(Schedulers.single())
                .subscribe(list -> {
                    ListReadersState lr;
                    if (list.isRight()) {
                        lr = ListReadersState.builder()
                                .error(null)
                                .readers(_state.get().getReaders()).typeArts(_state.get().getTypeArts()).newspaperList(_state.get().getNewspaperList()).readersFilteredType(list.get()).readersFilteredNewspaper(_state.getValue().getReadersFilteredNewspaper()).readerSelected(_state.getValue().getReaderSelected()).isLoading(false).warningMessage(null).successMessage(null
                                ).build();
                    } else {
                        lr = ListReadersState.builder().error(list.getLeft()).readers(_state.get().getReaders()).typeArts(_state.get().getTypeArts()).newspaperList(_state.get().getNewspaperList()).readersFilteredType(null).readersFilteredNewspaper(_state.get().getReadersFilteredNewspaper()).readerSelected(_state.getValue().getReaderSelected()).isLoading(false).warningMessage(null).successMessage(null).build();
                    }
                    _state.setValue(lr);
                });
    }

    public void loadReadersByNews(Newspaper newspaper) {
        setLoadingTrue();
        readerServicesImpl.getReadersByNewspaper(newspaper)
                .observeOn(Schedulers.single())
                .subscribe(list -> {
                    ListReadersState es = ListReadersState.builder()
                            .error(null)
                            .readers(_state.get().getReaders()).typeArts(_state.get().getTypeArts()).newspaperList(_state.get().getNewspaperList()).readersFilteredType(_state.get().getReadersFilteredType()).readersFilteredNewspaper(null).readerSelected(_state.getValue().getReaderSelected()).isLoading(false).warningMessage(null).successMessage(null)
                            .build();
                    if (list.isRight()) {
                        if (!list.get().isEmpty())
                            es.setReadersFilteredNewspaper(list.get());
                        else
                            es.setError(ScreenConstants.THERE_ARE_NO_READERS_SUSCRIBED_TO_THIS_NEWS);
                    } else {
                        es = ListReadersState.builder().error(list.getLeft()).readers(_state.get().getReaders()).typeArts(_state.get().getTypeArts()).newspaperList(_state.get().getNewspaperList()).readersFilteredType(_state.get().getReadersFilteredType()).readersFilteredNewspaper(null).readerSelected(_state.getValue().getReaderSelected()).isLoading(false).warningMessage(null).successMessage(null).build();
                    }
                    _state.setValue(es);
                });
    }


    public void validateReader(Reader reader) {
        if (reader.getId() < 0) {
            ListReadersState current = _state.getValue();
            _state.setValue(ListReadersState.builder().error(ScreenConstants.INVALID_READER).readers(current.getReaders()).typeArts(current.getTypeArts()).newspaperList(current.getNewspaperList()).readersFilteredType(current.getReadersFilteredType()).readersFilteredNewspaper(current.getReadersFilteredNewspaper()).readerSelected(null).isLoading(false).warningMessage(null).successMessage(null).build());
        } else {
            setLoadingTrue();
            readerServicesImpl.get(reader)
                    .observeOn(Schedulers.single())
                    .subscribe(readerDelete -> {
                        ListReadersState current = _state.getValue();
                        if (readerDelete.isRight()) {
                            _state.setValue(ListReadersState.builder()
                                    .error(null)
                                    .readers(current.getReaders()).typeArts(current.getTypeArts()).newspaperList(current.getNewspaperList()).readersFilteredType(current.getReadersFilteredType()).readersFilteredNewspaper(current.getReadersFilteredNewspaper()).readerSelected(readerDelete.get()).isLoading(false).warningMessage(ScreenConstants.ARE_YOU_SURE_YOU_WANT_TO_DELETE_THIS_READER_WITH_ALL_ITS_READ_ARTICLES_AND_LOGIN).successMessage(null)
                                    .build());
                        } else {
                            _state.setValue(ListReadersState.builder().error(readerDelete.getLeft()).readers(current.getReaders()).typeArts(current.getTypeArts()).newspaperList(current.getNewspaperList()).readersFilteredType(current.getReadersFilteredType()).readersFilteredNewspaper(current.getReadersFilteredNewspaper()).readerSelected(null).isLoading(false).warningMessage(null).successMessage(null).build());
                        }
                    });
        }
    }


    public void deleteReader(Reader killedReader) {
        if (killedReader != null) {
            setLoadingTrue();
            readerServicesImpl.delete(_state.getValue().getReaderSelected())
                    .observeOn(Schedulers.single())
                    .subscribe(readerDeleteSuccess -> {
                        ListReadersState currentStatus = _state.getValue();
                        if (readerDeleteSuccess.isRight()) {
                            List<Reader> currentAll = currentStatus.getReaders();
                            currentAll.remove(killedReader);
                            List<Reader> currentByType = currentStatus.getReadersFilteredType();
                            if (currentByType != null) {
                                currentByType.remove(killedReader);
                            }
                            List<Reader> currentByNews = currentStatus.getReadersFilteredNewspaper();
                            if (currentByNews != null) {
                                currentByNews.remove(killedReader);
                            }
                            _state.setValue(ListReadersState.builder()
                                    .error(null)
                                    .readers(currentAll)
                                    .typeArts(currentStatus.getTypeArts())
                                    .newspaperList(currentStatus.getNewspaperList())
                                    .readersFilteredType(currentByType)
                                    .readersFilteredNewspaper(currentByNews)
                                    .readerSelected(null)
                                    .isLoading(false)
                                    .warningMessage(null)
                                    .successMessage(ScreenConstants.SE_ELIMINO_EL_LECTOR)
                                    .build());
                        } else {
                            _state.setValue(ListReadersState.builder().error(readerDeleteSuccess.getLeft()).readers(null).typeArts(currentStatus.getTypeArts()).newspaperList(currentStatus.getNewspaperList()).readersFilteredType(currentStatus.getReadersFilteredType()).readersFilteredNewspaper(currentStatus.getReadersFilteredNewspaper()).readerSelected(null).isLoading(false).warningMessage(null).successMessage(null).build());
                        }
                    });
        } else {
            ListReadersState currentStatus = _state.getValue();
            _state.setValue(ListReadersState.builder().error(ScreenConstants.AN_UNEXPECTED_ERROR_OCCURRED_PLEASE_TRY_AGAIN)
                    .readers(null).typeArts(currentStatus.getTypeArts())
                    .newspaperList(currentStatus.getNewspaperList())
                    .readersFilteredType(currentStatus.getReadersFilteredType())
                    .readersFilteredNewspaper(currentStatus.getReadersFilteredNewspaper()).readerSelected(null)
                    .isLoading(false)
                    .warningMessage(null).successMessage(null).build());
        }
    }


    public void clearMessages() {
        ListReadersState currentStatus = _state.getValue();
        _state.setValue(ListReadersState.builder()
                .error(null)
                .readers(currentStatus.getReaders()).typeArts(currentStatus.getTypeArts()).newspaperList(currentStatus.getNewspaperList()).readersFilteredType(currentStatus.getReadersFilteredType()).readersFilteredNewspaper(currentStatus.getReadersFilteredNewspaper()).readerSelected(currentStatus.getReaderSelected()).isLoading(false).warningMessage(null).successMessage(null)
                .build());
    }

    private void setLoadingTrue() {
        ListReadersState ls = _state.getValue();
        // Si habia un mensaje previo (como un warning) lo borro para que no salga dos veces
        _state.setValue(ListReadersState.builder()
                .error(null)
                .readers(ls.getReaders())
                .typeArts(ls.getTypeArts())
                .newspaperList(ls.getNewspaperList())
                .readersFilteredType(ls.getReadersFilteredType())
                .readersFilteredNewspaper(ls.getReadersFilteredNewspaper())
                .readerSelected(ls.getReaderSelected())
                .isLoading(true)
                .warningMessage(null)
                .successMessage(null)
                .build());
    }
}
