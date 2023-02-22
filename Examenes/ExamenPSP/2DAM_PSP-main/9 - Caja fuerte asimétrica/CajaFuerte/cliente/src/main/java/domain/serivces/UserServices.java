package domain.serivces;

import io.vavr.control.Either;
import org.utils.domain.modelo.User;

public interface UserServices {


    Either<String, User> add(User newUser);

}
