package domain.servicios;

import dao.DaoReaders;
import dao.modelo.Reader;
import io.vavr.control.Either;
import jakarta.inject.Inject;

import java.util.List;

public class ServiciosReaders {

    private final DaoReaders dao;

    @Inject
    public ServiciosReaders(DaoReaders dao) {
        this.dao = dao;
    }


    public Either<Integer,List<Reader>> getAllReaders() {
        return dao.getAll();
    }

    public Either<Integer,List<Reader>> addReader(Reader reader) {
        return dao.save(reader);
    }

//    public Either<Integer,Reader> updateReader(Reader reader) {
//        return dao.update(reader);
//    }

    public boolean deleteReader(String id) {
        return dao.delete(id);
    }

    public Reader getReader(String id) {
        return dao.get(id);
    }
}
