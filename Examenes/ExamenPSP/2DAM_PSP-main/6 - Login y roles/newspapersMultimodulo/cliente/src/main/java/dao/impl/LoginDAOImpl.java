package dao.impl;

import dao.LoginDAO;
import dao.retrofit.llamadas.LoginApi;
import io.reactivex.rxjava3.core.Single;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import org.miutils.common.AplicationConstants;
import org.miutils.domain.modelo.Reader;
import retrofit2.Response;

public class LoginDAOImpl extends DaoGenericsImpl implements LoginDAO {


    private final LoginApi loginApi;

    @Inject
    public LoginDAOImpl(LoginApi loginApi) {
        this.loginApi = loginApi;
    }

    @Override
    public Single<Either<String, Response<Object>>> login(String credenciales) {
         return safeSingleResponseApicall(loginApi.login(credenciales));
    }

    @Override
    public Single<Either<String, Response<Object>>> logout() {
        return safeSingleResponseApicall(loginApi.logout());
    }

    @Override
    public Single<Either<String, Response<Object>>> forgotPassword(String mail) {
        return safeSingleResponseApicall(loginApi.forgot(mail));
    }

    @Override
    public Single<Either<String, Reader>> add(Reader reader) {
        return safeSingleApicall(loginApi.register(reader));
    }

    @Override
    public Single<Either<String, Response<Object>>> loginInvitado() {
        return safeSingleResponseApicall(loginApi.loginInvitado(AplicationConstants.INVITADO));
    }
}
