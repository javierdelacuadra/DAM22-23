package servicios;

import data.DaoReaders;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import model.Login;
import model.Reader;

import java.util.List;

public class ServicesReadersSQL {

    private final DaoReaders daoReaders;

    @Inject
    public ServicesReadersSQL(DaoReaders daoReaders) {
        this.daoReaders = daoReaders;
    }

    public int saveReader(Reader reader) {
        return daoReaders.save(reader);
    }

    public Either<Integer, List<Reader>> getAllReaders() {
        return daoReaders.getAll();
    }

    public int deleteReader(Reader reader) {
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

    public Integer login(Login login) {
        return daoReaders.login(login);
    }

    public Reader getReadersById(int id) {
        return daoReaders.get(id);
    }

    public Either<Integer, List<Reader>> getOldestSubscribers() {
        return daoReaders.getOldestSubscribers();
    }
}