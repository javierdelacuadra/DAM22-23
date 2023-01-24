package data;

import com.google.gson.Gson;
import data.retrofit.NewspapersApi;
import io.reactivex.rxjava3.core.Single;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import modelo.Newspaper;

public class DaoNewspaperSQL extends DaoGenerics {
    private final NewspapersApi newspapersApi;

    @Inject
    public DaoNewspaperSQL(NewspapersApi newspapersApi, Gson gson) {
        super(gson);
        this.newspapersApi = newspapersApi;
    }

    public Single<Either<String, Newspaper>> add(Newspaper newspaper) {
        return createSafeSingleApiCall(newspapersApi.addNewspaper(newspaper));
    }
}