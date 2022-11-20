package data;

import com.google.gson.Gson;
import data.retrofit.NewspapersApi;
import io.reactivex.rxjava3.core.Single;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import modelo.Reader;

import java.util.List;

public class DaoReadersSQL extends DaoGenerics {
    private final NewspapersApi newspapersApi;

    @Inject
    public DaoReadersSQL(NewspapersApi newspapersApi, Gson gson) {
        super(gson);
        this.newspapersApi = newspapersApi;
    }

    public Single<Either<String, List<Reader>>> getAll() {
        return createSafeSingleApiCall(newspapersApi.getReaders());
    }

    public Single<Either<String, Reader>> add(Reader reader) {
        return createSafeSingleApiCall(newspapersApi.addReader(reader));
    }

    public Single<Either<String, Reader>> update(Reader reader) {
        return createSafeSingleApiCall(newspapersApi.updateReader(reader));
    }

    public Single<Either<String, Boolean>> delete(String id) {
        return createSafeSingleDeleteCall(newspapersApi.deleteReader(id));
    }
}