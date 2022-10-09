package servicios;

import data.DaoReaders;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import jakarta.xml.bind.JAXBException;
import modelo.Reader;

import java.util.List;
import java.util.stream.Collectors;

public class ServicesReaders {

    private final DaoReaders daoReaders;

    @Inject
    public ServicesReaders(DaoReaders daoReaders) {
        this.daoReaders = daoReaders;
    }

    public Either<String, List<Reader>> getReaders() throws JAXBException {
        return daoReaders.getReaders();
    }

    public List<Reader> getReadersByNewspaper(int idNewspaper) throws JAXBException {
        return daoReaders.getReaders().get().stream()
                .filter(reader -> reader.getSubscriptions().stream()
                        .anyMatch(subscription -> subscription.getId_newspaper() == idNewspaper))
                .collect(Collectors.toList());
    }

    public void deleteReader(Reader reader) throws JAXBException {
        List<Reader> readers = daoReaders.getReaders().get();
        readers.remove(reader);
        daoReaders.saveReaders(readers);
    }
}
