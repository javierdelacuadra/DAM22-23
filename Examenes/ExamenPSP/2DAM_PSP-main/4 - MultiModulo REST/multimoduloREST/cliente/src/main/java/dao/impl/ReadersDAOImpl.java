package dao.impl;

import dao.ReadersDAO;
import dao.retrofit.llamadas.NewsApi;
import io.reactivex.rxjava3.core.Single;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import org.miutils.domain.modelo.Newspaper;
import org.miutils.domain.modelo.Reader;
import org.miutils.domain.modelo.TypeArt;
import retrofit2.Response;

import java.util.List;

public class ReadersDAOImpl extends DaoGenericsImpl implements ReadersDAO {

    NewsApi newsApi;
    @Inject
    public ReadersDAOImpl( NewsApi newsApi) {
        this.newsApi = newsApi;
    }
    @Override
    public Single<Either<String, List<Reader>>> getAll() {
        return safeSingleApicall(newsApi.getAllReadersAsync());
    }

    @Override
    public Single<Either<String, List<Reader>>> getAllByType(TypeArt typeArt) {
        return safeSingleApicall(newsApi.getReadersByType(typeArt.getName()));
    }

    @Override
    public Single<Either<String, List<Reader>>> getAllByNews(Newspaper newspaper) {
        return safeSingleApicall(newsApi.getReadersByNews(newspaper.getId()+""));
    }


    @Override
    public Single<Either<String, List<String>>> getListOfReadersNamesQuery(Newspaper newspaper) {
        return safeSingleApicall(newsApi.getOldestSuscriptorsNews(newspaper.getId()+""));
    }
    @Override
    public Single<Either<String, Reader>> get(int id) {
        return safeSingleApicall(newsApi.getOneReader(id+""));
    }

    @Override
    public Single<Either<String, Response<Object>>> delete(int id) {
        return safeSingleDeleteApicall(newsApi.deleteReaderAsync(id+""));
    }
    @Override
    public Single<Either<String, Reader>> add(Reader newReader) {
        return safeSingleApicall(newsApi.addReaderAsync(newReader));
    }
    @Override
    public Single<Either<String, Reader>> update(Reader newReader){
        return safeSingleApicall(newsApi.updateReaderAsync(newReader));
    }
}
