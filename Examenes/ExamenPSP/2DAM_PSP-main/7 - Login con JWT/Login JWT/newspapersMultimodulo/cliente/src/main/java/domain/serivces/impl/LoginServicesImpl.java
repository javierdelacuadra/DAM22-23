package domain.serivces.impl;

import dao.impl.LoginDAOImpl;
import domain.serivces.LoginServices;
import io.reactivex.rxjava3.core.Single;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import org.miutils.common.AplicationConstants;
import org.miutils.domain.modelo.Reader;
import retrofit2.Response;

public class LoginServicesImpl implements LoginServices {

    private final LoginDAOImpl loginDAOImpl;

    @Inject
    public LoginServicesImpl(LoginDAOImpl loginDAOImpl) {
        this.loginDAOImpl = loginDAOImpl;
    }

    @Override
    public Single<Either<String, Response<Object>>> login(String user, String password) {
        if (user.isEmpty() || password.isEmpty()) {
            return Single.just(Either.left(AplicationConstants.COMPLETA_TODOS_LOS_CAMPOS));
        }
        return loginDAOImpl.login(user, password);
    }

    @Override
    public Single<Either<String, Response<Object>>> logout() {
        return loginDAOImpl.logout();
    }

    @Override
    public Single<Either<String, Response<Object>>> forgotPassword(String text) {
        return loginDAOImpl.forgotPassword(text);
    }

    @Override
    public Single<Either<String, Reader>> add(Reader reader) {
        return loginDAOImpl.add(reader);
    }

    @Override
    public Single<Either<String, Response<Object>>> loginInvitado() {
        return loginDAOImpl.loginInvitado();
    }
}
