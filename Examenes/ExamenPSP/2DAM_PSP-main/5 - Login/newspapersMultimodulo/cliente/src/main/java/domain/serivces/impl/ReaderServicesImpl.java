package domain.serivces.impl;

import dao.impl.ReadersDAOImpl;
import domain.serivces.ReaderServices;
import io.reactivex.rxjava3.core.Single;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import org.miutils.domain.modelo.Newspaper;
import org.miutils.domain.modelo.Reader;
import org.miutils.domain.modelo.TypeArt;
import retrofit2.Response;

import java.util.List;

public class ReaderServicesImpl implements ReaderServices {
    private final ReadersDAOImpl readersDAO;

    @Inject
    public ReaderServicesImpl(ReadersDAOImpl readersDAO) {
        this.readersDAO = readersDAO;
    }

    @Override
    public Single<Either<String, List<Reader>>> getAll() {
        return readersDAO.getAll();
    }

    @Override
    public Single<Either<String, Reader>> get(Reader reader) {
        return readersDAO.get(reader.getId());
    }


    @Override
    public Single<Either<String, Response<Object>>> delete(Reader readerSelected) {
        return readersDAO.delete(readerSelected.getId());
    }

    @Override
    public Single<Either<String, Reader>> add(Reader newReader) {
        return readersDAO.add(newReader);
    }

    @Override
    public Single<Either<String, Reader>> update(Reader newReader) {
        return readersDAO.update(newReader);

    }

    @Override
    public Single<Either<String, List<Reader>>> getReadersByType(TypeArt typeArt) {
        return readersDAO.getAllByType(typeArt);
    }

    @Override
    public Single<Either<String, List<Reader>>> getReadersByNewspaper(Newspaper newspaper) {
        return readersDAO.getAllByNews(newspaper);
    }

    @Override
    public Single<Either<String, List<String>>> getOldestReaders(Newspaper newspaperSelected) {
        return readersDAO.getListOfReadersNamesQuery(newspaperSelected);
    }

}

