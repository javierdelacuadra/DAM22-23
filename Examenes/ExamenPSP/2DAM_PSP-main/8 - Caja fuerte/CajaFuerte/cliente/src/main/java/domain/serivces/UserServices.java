package domain.serivces;

import io.reactivex.rxjava3.core.Single;
import io.vavr.control.Either;
import org.utils.domain.modelo.User;
import retrofit2.Response;

public interface UserServices {

    Single<Either<String, Response<Object>>> login(String user, String password);

    Single<Either<String, Response<Object>>> add(User newUser);
}
