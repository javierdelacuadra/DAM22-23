package domain.serivces;

import io.reactivex.rxjava3.core.Single;
import io.vavr.control.Either;
import retrofit2.Response;

public interface LoginServices {
    Single<Either<String, Response<Object>>> login(String user, String password);
}
