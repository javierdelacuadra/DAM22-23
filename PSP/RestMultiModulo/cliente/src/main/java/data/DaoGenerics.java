package data;

import com.google.gson.Gson;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.vavr.control.Either;
import jakarta.inject.Inject;
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
            try (ResponseBody responseBody = Objects.requireNonNull(httpException.response()).errorBody()) {
                if (Objects.equals(Objects.requireNonNull(responseBody).contentType(),
                        MediaType.get("application/json"))) {
                    either = Either.left(gson.fromJson(responseBody.string(), String.class));
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