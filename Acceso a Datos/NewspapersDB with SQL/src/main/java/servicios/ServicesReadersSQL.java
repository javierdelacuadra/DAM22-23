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

    public Either<Integer, List<Reader>> saveReader(Reader reader) {
        return daoReadersSQL.save(reader);
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
}