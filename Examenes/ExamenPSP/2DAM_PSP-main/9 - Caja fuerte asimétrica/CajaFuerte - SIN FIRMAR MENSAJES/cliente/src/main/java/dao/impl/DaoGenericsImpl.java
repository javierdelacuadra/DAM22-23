package dao.impl;

import com.squareup.moshi.Moshi;
import dao.DaoGenerics;
import dao.common.APIConstants;
import dao.retrofit.produces.LocalDateMoshiAdapter;
import dao.retrofit.produces.LocalDateTimeMoshiAdapter;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.vavr.control.Either;
import lombok.extern.log4j.Log4j2;
import okhttp3.MediaType;
import org.utils.domain.modelo.ApiError;
import retrofit2.HttpException;
import retrofit2.Response;

import java.io.IOException;
import java.util.Objects;

@Log4j2
public abstract class DaoGenericsImpl implements DaoGenerics {


    @Override
    public <T> Single<Either<String, T>> safeSingleApicall(Single<T> call) {
        return call.map(t -> Either.right(t).mapLeft(Object::toString))
                .subscribeOn(Schedulers.io())
                .onErrorReturn(throwable -> {
                    Either<String, T> error = Either.left(APIConstants.ERROR_DE_COMUNICACION);
                    error = getErrorResponse(throwable, error);
                    return error;
                });
    }
    @Override
    public Single<Either<String, Response<Object>>> safeSingleResponseApicall(Single<Response<Object>> call) {
        return call.map(t -> {

                    Either<String, Response<Object>> error = Either.left(APIConstants.ERROR_DE_COMUNICACION);
                    if (t.code() < 300) {
                        // es un 2xx, ok
                        error = Either.right(t);
                    } else {
                        // es un 3xx 4xx o 5 xx,
                        error = getErrorFromJson(error, t);
                    }
                    return error;
                })
                .subscribeOn(Schedulers.io())
                .onErrorReturn(throwable -> {
                    log.error("Error en la llamada a la API", throwable);
                    Either<String, Response<Object>> error = Either.left(APIConstants.ERROR_DE_COMUNICACION);
                    error = getErrorResponse(throwable, error);
                    return error;
                });
    }

    private <T> Either<String, T> getErrorResponse(Throwable throwable, Either<String, T> error) throws IOException {
        if (throwable instanceof HttpException excepcion) {
            error = getErrorFromJson(error, Objects.requireNonNull(excepcion.response()));
        }
        return error;
    }

    private static <T> Either<String, T> getErrorFromJson(Either<String, T> error, Response<?> response) throws IOException {
        if (Objects.equals(Objects.requireNonNull(response.errorBody()).contentType(), MediaType.get(APIConstants.APPLICATION_JSON))) {
            Moshi m = new Moshi.Builder().add(new LocalDateMoshiAdapter()).add(new LocalDateTimeMoshiAdapter()).build();
            ApiError apierror = m.adapter(ApiError.class).fromJson(response.errorBody().string());
            if (apierror != null) {
                error = Either.left(apierror.getMessage());
            }
        } else {
            error = Either.left((response.message()));
        }
        return error;
    }
}
