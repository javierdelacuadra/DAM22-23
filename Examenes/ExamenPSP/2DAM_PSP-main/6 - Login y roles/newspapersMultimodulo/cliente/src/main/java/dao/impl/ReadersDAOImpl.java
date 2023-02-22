package dao.impl;

import dao.ReadersDAO;
import dao.retrofit.llamadas.NewspapersApi;
import io.reactivex.rxjava3.core.Single;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import org.miutils.domain.modelo.Newspaper;
import org.miutils.domain.modelo.Reader;
import org.miutils.domain.modelo.TypeArt;
import retrofit2.Response;

import java.util.List;

public class ReadersDAOImpl extends DaoGenericsImpl implements ReadersDAO {

    NewspapersApi newspapersApi;
    @Inject
    public ReadersDAOImpl( NewspapersApi newspapersApi) {
        this.newspapersApi = newspapersApi;
    }
    @Override
    public Single<Either<String, List<Reader>>> getAll() {
        return safeSingleApicall(newspapersApi.getAllReadersAsync());
    }

    @Override
    public Single<Either<String, List<Reader>>> getAllByType(TypeArt typeArt) {
        return safeSingleApicall(newspapersApi.getReadersByType(typeArt.getName()));
    }

    @Override
    public Single<Either<String, List<Reader>>> getAllByNews(Newspaper newspaper) {
        return safeSingleApicall(newspapersApi.getReadersByNews(newspaper.getId()+""));
    }


    @Override
    public Single<Either<String, List<String>>> getListOfReadersNamesQuery(Newspaper newspaper) {
        return safeSingleApicall(newspapersApi.getOldestSuscriptorsNews(newspaper.getId()+""));
    }
    @Override
    public Single<Either<String, Reader>> get(int id) {
        return safeSingleApicall(newspapersApi.getOneReader(id+""));
    }

    @Override
    public Single<Either<String, Response<Object>>> delete(int id) {
        return safeSingleResponseApicall(newspapersApi.deleteReaderAsync(id+""));
    }
    @Override
    public Single<Either<String, Reader>> add(Reader newReader) {
        return safeSingleApicall(newspapersApi.addReaderAsync(newReader));
    }
    @Override
    public Single<Either<String, Reader>> update(Reader newReader){
        return safeSingleApicall(newspapersApi.updateReaderAsync(newReader));
    }
}
