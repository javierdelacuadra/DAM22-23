package dao.impl;

import dao.UserDao;
import dao.retrofit.llamadas.UserApi;
import io.reactivex.rxjava3.core.Single;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import org.utils.domain.modelo.User;
import org.utils.domain.modelo.dto.UserDTO;

public class UserDaoImpl extends DaoGenericsImpl implements UserDao {


    private final UserApi userApi;

    @Inject
    public UserDaoImpl(UserApi userApi) {
        this.userApi = userApi;
    }

    @Override
    public Single<Either<String, User>> login(UserDTO userDTO) {
        return safeSingleApicall(userApi.login(userDTO));
    }

    @Override
    public Single<Either<String, User>> add(User newUser) {
        return safeSingleApicall(userApi.add(newUser));
    }

    @Override
    public Single<Either<String, String>> getCertificate(String username) {
        return safeSingleApicall(userApi.getCertificate(username));
    }

}
