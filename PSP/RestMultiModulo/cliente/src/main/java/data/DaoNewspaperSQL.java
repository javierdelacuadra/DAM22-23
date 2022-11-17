package data;

import data.retrofit.NewspapersApi;
import io.reactivex.rxjava3.core.Single;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import model.Newspaper;

import java.util.List;

public class DaoNewspaperSQL extends DaoGenerics {
    private final NewspapersApi newspapersApi;

    @Inject
    public DaoNewspaperSQL(NewspapersApi newspapersApi) {
        this.newspapersApi = newspapersApi;
    }

    public Single<Either<String, List<Newspaper>>> getAll() {
        return createSafeSingleApiCall(newspapersApi.getNewspapers());
    }

    public Single<Either<String, Newspaper>> add(Newspaper newspaper) {
        return createSafeSingleApiCall(newspapersApi.addNewspaper(newspaper));
    }

    public Single<Either<String, Newspaper>> update(Newspaper newspaper) {
        return createSafeSingleApiCall(newspapersApi.updateNewspaper(newspaper));
    }

    public Single<Either<String, Newspaper>> delete(String id) {
        return createSafeSingleApiCall(newspapersApi.deleteNewspaper(id));
    }
}