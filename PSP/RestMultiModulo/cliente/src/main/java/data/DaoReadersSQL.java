package data;

import common.Constantes;
import data.retrofit.NewspapersApi;
import io.reactivex.rxjava3.core.Single;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import model.Reader;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DaoReadersSQL extends DaoGenerics {
    private final NewspapersApi newspapersApi;

    @Inject
    public DaoReadersSQL(NewspapersApi newspapersApi) {
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

    public Single<Either<String, Reader>> delete(String id) {
        return createSafeSingleApiCall(newspapersApi.deleteReader(id));
    }
}