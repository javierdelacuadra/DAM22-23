package servicios;

import data.DaoReadersSQL;
import io.reactivex.rxjava3.core.Single;
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

    public Single<Either<String, Reader>> saveReader(Reader reader) {
        return daoReadersSQL.add(reader);
    }

    public Single<Either<String, List<Reader>>> getAllReaders() {
        return daoReadersSQL.getAll();
    }

    public Single<Either<String, Reader>> deleteReader(String id) {
        return daoReadersSQL.delete(id);
    }

    public Single<Either<String, Reader>> updateReader(Reader reader) {
        return daoReadersSQL.update(reader);
    }

//    public Either<Integer, List<Reader>> getReadersByNewspaper(int id) {
//        return daoReadersSQL.getAll(id);
//    }
//
//    public Either<Integer, List<Reader>> getReadersByArticleType(String articleType) {
//        return daoReadersSQL.getAll(articleType);
//    }
//
//
//    public Reader getReadersById(int id) {
//        return daoReadersSQL.get(id);
//    }
//
//    public Either<Integer, List<Reader>> getOldestSubscribers() {
//        return daoReadersSQL.getOldestSubscribers();
//    }
}