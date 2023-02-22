package domain.serivces;

import io.reactivex.rxjava3.core.Single;
import io.vavr.control.Either;
import org.utils.domain.modelo.User;

public interface LoginServices {
    Single<Either<String, User>> login(String user, String password);
}
