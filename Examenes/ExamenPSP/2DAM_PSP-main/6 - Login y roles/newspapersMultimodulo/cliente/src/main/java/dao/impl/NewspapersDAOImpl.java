package dao.impl;

import dao.NewspapersDAO;
import dao.retrofit.llamadas.NewspapersApi;
import io.reactivex.rxjava3.core.Single;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import org.miutils.domain.modelo.Newspaper;
import retrofit2.Response;

import java.util.List;

public class NewspapersDAOImpl extends DaoGenericsImpl implements NewspapersDAO {

    private final NewspapersApi newspapersApi;

    @Inject
    public NewspapersDAOImpl(NewspapersApi newspapersApi) {
        this.newspapersApi = newspapersApi;
    }


    @Override
    public Single<Either<String, List<Newspaper>>> getAll() {
        return safeSingleApicall(newspapersApi.getAllNewsAsync());
    }


    @Override
    public Single<Either<String, Newspaper>> update(Newspaper news) {
        return safeSingleApicall(newspapersApi.updateNewsAsync(news));
    }

    @Override
    public Single<Either<String, Response<Object>>> delete(int id) {
        return safeSingleResponseApicall(newspapersApi.deleteNewsAsync(id + ""));
    }

    @Override
    public Single<Either<String, Newspaper>> add(Newspaper newspaper) {
        return safeSingleApicall(newspapersApi.addNewsAsync(newspaper));
    }
}
