package servicios;

import data.DaoReadersSQL;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import model.Reader;

import java.util.List;

public class ServicesReadersSQL {

    private final DaoReadersSQL daoReadersSQL;

    @Inject
    public ServicesReadersSQL(DaoReadersSQL daoReadersSQL) {
        this.daoReadersSQL = daoReadersSQL;
    }

    public Either<Integer, List<Reader>> saveReader(Reader reader, String password) {
        return daoReadersSQL.save(reader, password);
    }

    public Either<Integer, List<Reader>> getAllReaders() {
        return daoReadersSQL.getAll();
    }

    public Either<Integer, List<Reader>> deleteReader(Reader reader) {
        return daoReadersSQL.delete(reader);
    }

    public Either<Integer, List<Reader>> updateReader(Reader reader) {
        return daoReadersSQL.update(reader);
    }

    public Either<Integer, List<Reader>> getReadersByNewspaper(int id) {
        return daoReadersSQL.getAll(id);
    }

    public Either<Integer, List<Reader>> getReadersByArticleType(String articleType) {
        return daoReadersSQL.getAll(articleType);
    }

    public Integer login(String name, String password) {
        return daoReadersSQL.login(name, password);
    }

    public Reader getReadersById(int id) {
        return daoReadersSQL.get(id);
    }

    public Either<Integer, List<Reader>> getOldestSubscribers() {
        return daoReadersSQL.getOldestSubscribers();
    }
}