package domain.serivces;

import io.reactivex.rxjava3.core.Single;
import io.vavr.control.Either;
import org.utils.domain.modelo.User;

public interface UserServices {


    Either<String, User> add(User newUser);

    Single<Either<String, String>> getCertificate(String username);
}
