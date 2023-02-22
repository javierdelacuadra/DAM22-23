package dao.impl;

import dao.LoginDAO;
import dao.retrofit.llamadas.LoginApi;
import dao.retrofit.network.CacheAuthorization;
import io.reactivex.rxjava3.core.Single;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import okhttp3.Credentials;
import org.miutils.common.AplicationConstants;
import org.miutils.domain.modelo.Reader;
import retrofit2.Response;

public class LoginDAOImpl extends DaoGenericsImpl implements LoginDAO {


    private final LoginApi loginApi;
    private final CacheAuthorization ca;

    @Inject
    public LoginDAOImpl(LoginApi loginApi, CacheAuthorization ca) {
        this.loginApi = loginApi;
        this.ca = ca;
    }

    @Override
    public Single<Either<String, Response<Object>>> login(String user, String password) {
         ca.setUser(user);
         ca.setPass(password);
         return safeSingleResponseApicall(loginApi.login(Credentials.basic(user, password)));
    }

    @Override
    public Single<Either<String, Response<Object>>> logout() {
        ca.invalidate();
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
        ca.setUser(AplicationConstants.INVITADO);
        ca.setPass(AplicationConstants.INVITADO);
        return safeSingleResponseApicall(loginApi.login(Credentials.basic(AplicationConstants.INVITADO, AplicationConstants.INVITADO)));
    }
}
