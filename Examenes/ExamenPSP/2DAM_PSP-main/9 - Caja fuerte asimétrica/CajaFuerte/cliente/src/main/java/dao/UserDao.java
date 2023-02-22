package dao;

import io.reactivex.rxjava3.core.Single;
import io.vavr.control.Either;
import org.utils.domain.modelo.User;
import org.utils.domain.modelo.dto.UserDTO;

public interface UserDao {

    Single<Either<String, User>> login(UserDTO userDTO);

    Single<Either<String, User>> add(User newUser);

    Single<Either<String, String>> getCertificate(String username);
}
