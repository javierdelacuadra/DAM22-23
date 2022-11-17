package data;

import com.google.gson.Gson;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.vavr.control.Either;
import retrofit2.Call;
import retrofit2.HttpException;
import retrofit2.Response;

import java.util.Objects;

public abstract class DaoGenerics {
    public <T> Either<String, T> createSafeApiCall(Call<T> call) {
        Either<String, T> respuesta;

        try {
            Response<T> response = call.execute();
            if (response.isSuccessful()) {
                respuesta = Either.right(response.body());
            } else {
                assert response.errorBody() != null;
                respuesta = Either.left(response.errorBody().string());
            }
        } catch (Exception e) {
            respuesta = Either.left(e.getMessage());
        }
        return respuesta;
    }

    public <T> Single<Either<String, T>> createSafeSingleApiCall(Single<T> call) {
        return call.map(t -> Either.right(t).mapLeft(Object::toString))
                .subscribeOn(Schedulers.io())
                .onErrorReturn(throwable -> {
                    Either<String, T> either = Either.left(throwable.getMessage());
                    if (throwable instanceof HttpException) {
                        if (Objects.equals(((HttpException) throwable).code(), 404)) {
                            Gson gson = new Gson();
                            either = Either.left(gson.toJson("No se ha encontrado el recurso"));
                        }
                    }
                    return either;
                });
    }

}