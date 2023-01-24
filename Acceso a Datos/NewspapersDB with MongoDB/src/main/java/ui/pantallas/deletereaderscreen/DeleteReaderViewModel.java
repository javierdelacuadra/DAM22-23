package ui.pantallas.deletereaderscreen;

import io.vavr.control.Either;
import jakarta.inject.Inject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Reader;
import model.Subscription;
import servicios.ServicesReadersSQL;
import servicios.ServicesSubscription;

import java.util.List;

public class DeleteReaderViewModel {

    private final ServicesReadersSQL servicesReadersSQL;
    private final ServicesSubscription servicesSubscription;

    @Inject
    public DeleteReaderViewModel(ServicesReadersSQL servicesReadersSQL, ServicesSubscription servicesSubscription) {
        this.servicesReadersSQL = servicesReadersSQL;
        this.servicesSubscription = servicesSubscription;
    }

    public ObservableList<Reader> getReaders() {
        return FXCollections.observableArrayList(servicesReadersSQL.getAllReaders().get());
    }

    public int deleteReader(Reader reader, boolean delete) {
        return servicesReadersSQL.deleteReader(reader, delete);
    }

    public Either<Integer, List<Subscription>> getSubscriptions(Reader reader) {
        return servicesSubscription.get(reader);
    }
}
