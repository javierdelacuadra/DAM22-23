package data;

import com.google.gson.Gson;
import data.common.ConstantesDao;
import data.retrofit.common.ConstantesAPI;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import model.ClientAPIError;
import okhttp3.MediaType;
import okhttp3.ResponseBody;
import retrofit2.HttpException;
import retrofit2.Response;

import java.io.IOException;
import java.util.Objects;

public abstract class DaoGenerics {
    private final Gson gson;

    @Inject
    public DaoGenerics(Gson gson) {
        this.gson = gson;
    }

    public <T> Single<Either<String, T>> createSafeSingleApiCall(Single<T> call) {
        return call.map(t -> Either.right(t).mapLeft(Object::toString))
                .subscribeOn(Schedulers.io())
                .onErrorReturn(this::createError);
    }


    public Single<Either<String, Boolean>> createSafeSingleDeleteCall(Single<Response<Object>> apiCall) {
        return apiCall.map(objectResponse -> objectResponse.isSuccessful() ?
                        Either.right(true).mapLeft(Object::toString) :
                        Either.right(false).mapLeft(Object::toString))
                .subscribeOn(Schedulers.io())
                .onErrorReturn(this::createError);
    }

    private <T> Either<String, T> createError(Throwable throwable) {
        Either<String, T> either = Either.left(throwable.getMessage());
        if (throwable instanceof HttpException httpException) {
            int code = httpException.code();
            if (code == 401) {
                either = Either.left(ConstantesAPI.ACCION_NO_AUTORIZADA);
            } else if (code == 403) {
                either = Either.left(ConstantesAPI.ERROR_403);
            } else
                try (ResponseBody responseBody = Objects.requireNonNull(httpException.response()).errorBody()) {
                    if (Objects.equals(Objects.requireNonNull(responseBody).contentType(),
                            MediaType.get(ConstantesDao.APPLICATION_JSON))) {
                        ClientAPIError clientAPIError = gson.fromJson(responseBody.string(), ClientAPIError.class);
                        either = Either.left(clientAPIError.getMensaje());
                    } else {
                        either = Either.left(responseBody.string());
                    }
                } catch (IOException e) {
                    either = Either.left(e.getMessage());
                }
        }
        return either;
    }
}