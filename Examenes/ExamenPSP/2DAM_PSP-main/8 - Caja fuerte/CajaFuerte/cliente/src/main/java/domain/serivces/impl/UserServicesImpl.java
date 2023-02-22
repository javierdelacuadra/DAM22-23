package domain.serivces.impl;

import dao.impl.UserDaoImpl;
import domain.serivces.UserServices;
import io.reactivex.rxjava3.core.Single;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import org.utils.domain.modelo.User;
import retrofit2.Response;

public class UserServicesImpl implements UserServices {

    UserDaoImpl userDao;

    @Inject
    public UserServicesImpl(UserDaoImpl userDao) {
        this.userDao = userDao;
    }


    @Override
    public Single<Either<String, Response<Object>>> login(String user, String password) {
        return userDao.login(user, password);
    }

    @Override
    public Single<Either<String, Response<Object>>> add(User newUser) {
        return userDao.add(newUser);
    }
}
