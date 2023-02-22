package dao.impl;

import common.CachedCredentials;
import dao.UserDao;
import dao.retrofit.llamadas.UserApi;
import io.reactivex.rxjava3.core.Single;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import okhttp3.Credentials;
import org.utils.domain.modelo.User;
import retrofit2.Response;

public class UserDaoImpl extends DaoGenericsImpl implements UserDao {


    private final UserApi userApi;
    private final CachedCredentials ca;

    @Inject
    public UserDaoImpl(UserApi userApi, CachedCredentials ca) {
        this.userApi = userApi;
        this.ca = ca;
    }

    @Override
    public Single<Either<String, Response<Object>>> login(String user, String password) {
         ca.setUser(user);
         ca.setPass(password);
         return safeSingleResponseApicall(userApi.login(Credentials.basic(user, password)));
    }
    @Override
    public Single<Either<String, Response<Object>>> add(User newUser) {
        return safeSingleResponseApicall(userApi.add(newUser));
    }

}
