package dao.impl;

import dao.ReadersDAO;
import dao.retrofit.llamadas.ReadersApi;
import io.reactivex.rxjava3.core.Single;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import org.miutils.domain.modelo.Newspaper;
import org.miutils.domain.modelo.Reader;
import org.miutils.domain.modelo.TypeArt;
import retrofit2.Response;

import java.util.List;

public class ReadersDAOImpl extends DaoGenericsImpl implements ReadersDAO {

    ReadersApi readersApi;

    @Inject
    public ReadersDAOImpl( ReadersApi readersApi) {
        this.readersApi = readersApi;
    }
    @Override
    public Single<Either<String, List<Reader>>> getAll() {
        return safeSingleApicall(readersApi.getAllReadersAsync());
    }

    @Override
    public Single<Either<String, List<Reader>>> getAllByType(TypeArt typeArt) {
        return safeSingleApicall(readersApi.getReadersByType(typeArt.getName()));
    }

    @Override
    public Single<Either<String, List<Reader>>> getAllByNews(Newspaper newspaper) {
        return safeSingleApicall(readersApi.getReadersByNews(newspaper.getId()+""));
    }


    @Override
    public Single<Either<String, List<String>>> getListOfReadersNamesQuery(Newspaper newspaper) {
        return safeSingleApicall(readersApi.getOldestSuscriptorsNews(newspaper.getId()+""));
    }
    @Override
    public Single<Either<String, Reader>> get(int id) {
        return safeSingleApicall(readersApi.getOneReader(id+""));
    }

    @Override
    public Single<Either<String, Response<Object>>> delete(int id) {
        return safeSingleResponseApicall(readersApi.deleteReaderAsync(id+""));
    }
    @Override
    public Single<Either<String, Reader>> add(Reader newReader) {
        return safeSingleApicall(readersApi.addReaderAsync(newReader));
    }
    @Override
    public Single<Either<String, Reader>> update(Reader newReader){
        return safeSingleApicall(readersApi.updateReaderAsync(newReader));
    }
}
