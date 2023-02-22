package dao.impl;

import dao.NewspapersDAO;
import dao.retrofit.llamadas.NewsApi;
import io.reactivex.rxjava3.core.Single;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import org.miutils.domain.modelo.Newspaper;
import retrofit2.Response;

import java.util.List;

public class NewspapersDAOImpl extends DaoGenericsImpl implements NewspapersDAO {

    private final NewsApi newsApi;

    @Inject
    public NewspapersDAOImpl(NewsApi newsApi) {
        this.newsApi = newsApi;
    }


    @Override
    public Single<Either<String, List<Newspaper>>> getAll() {
        return safeSingleApicall(newsApi.getAllNewsAsync());
    }


    @Override
    public Single<Either<String, Newspaper>> update(Newspaper news) {
        return safeSingleApicall(newsApi.updateNewsAsync(news));
    }

    @Override
    public Single<Either<String, Response<Object>>> delete(int id) {
        return safeSingleResponseApicall(newsApi.deleteNewsAsync(id + ""));
    }

    @Override
    public Single<Either<String, Newspaper>> add(Newspaper newspaper) {
        return safeSingleApicall(newsApi.addNewsAsync(newspaper));
    }
}
