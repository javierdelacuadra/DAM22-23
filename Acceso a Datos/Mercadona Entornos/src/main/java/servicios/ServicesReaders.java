package servicios;

import data.DaoReaders;
import jakarta.inject.Inject;
import javafx.collections.ObservableList;
import modelo.Reader;

import java.util.List;

public class ServicesReaders {

    private final DaoReaders daoReaders;

    @Inject
    public ServicesReaders(DaoReaders daoReaders) {
        this.daoReaders = daoReaders;
    }

    public List<Reader> getReaders() {
        return daoReaders.getReaders();
    }
}
