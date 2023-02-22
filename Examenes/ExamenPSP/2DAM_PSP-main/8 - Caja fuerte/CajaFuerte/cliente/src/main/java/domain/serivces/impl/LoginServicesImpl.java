package domain.serivces.impl;

import dao.impl.UserDaoImpl;
import domain.serivces.LoginServices;
import io.reactivex.rxjava3.core.Single;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import org.utils.common.AplicationConstants;
import retrofit2.Response;

public class LoginServicesImpl implements LoginServices {

    private final UserDaoImpl loginDAOImpl;

    @Inject
    public LoginServicesImpl(UserDaoImpl loginDAOImpl) {
        this.loginDAOImpl = loginDAOImpl;
    }

    @Override
    public Single<Either<String, Response<Object>>> login(String user, String password) {
        if (user.isEmpty() || password.isEmpty()) {
            return Single.just(Either.left(AplicationConstants.COMPLETA_TODOS_LOS_CAMPOS));
        }
        return loginDAOImpl.login(user, password);
    }
}
