package data;

import com.google.gson.Gson;
import data.retrofit.ReadersApi;
import io.reactivex.rxjava3.core.Single;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import model.Reader;

import java.util.List;

public class DaoReadersSQL extends DaoGenerics {
    private final ReadersApi readersApi;

    @Inject
    public DaoReadersSQL(ReadersApi readersApi, Gson gson) {
        super(gson);
        this.readersApi = readersApi;
    }

    public Single<Either<String, List<Reader>>> getAll() {
        return createSafeSingleApiCall(readersApi.getReaders());
    }

    public Single<Either<String, Reader>> add(Reader reader) {
        return createSafeSingleApiCall(readersApi.addReader(reader));
    }

    public Single<Either<String, Reader>> update(Reader reader) {
        return createSafeSingleApiCall(readersApi.updateReader(reader));
    }

    public Single<Either<String, Boolean>> delete(String id) {
        return createSafeSingleDeleteCall(readersApi.deleteReader(id));
    }
}