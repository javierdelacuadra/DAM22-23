package servicios;

import data.DaoReaders;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import model.Reader;

import java.util.List;

public class ServicesReadersSQL {

    private final DaoReaders daoReaders;

    @Inject
    public ServicesReadersSQL(DaoReaders daoReaders) {
        this.daoReaders = daoReaders;
    }

    public Either<Integer, List<Reader>> saveReader(Reader reader, String password) {
        return daoReaders.save(reader, password);
    }

    public Either<Integer, List<Reader>> getAllReaders() {
        return daoReaders.getAll();
    }

    public Either<Integer, List<Reader>> deleteReader(Reader reader) {
        return daoReaders.delete(reader);
    }

    public Integer updateReader(Reader reader) {
        return daoReaders.update(reader);
    }

    public Either<Integer, List<Reader>> getReadersByNewspaper(int id) {
        return daoReaders.getAll(id);
    }

    public Either<Integer, List<Reader>> getReadersByArticleType(String articleType) {
        return daoReaders.getAll(articleType);
    }

    public Integer login(String name, String password) {
        return daoReaders.login(name, password);
    }

    public Reader getReadersById(int id) {
        return daoReaders.get(id);
    }

    public Either<Integer, List<Reader>> getOldestSubscribers() {
        return daoReaders.getOldestSubscribers();
    }
}