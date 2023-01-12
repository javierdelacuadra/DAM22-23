package servicios;

import data.DaoLogin;
import io.reactivex.rxjava3.core.Single;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import model.Reader;
import model.ReaderLogin;
import okhttp3.Credentials;
import retrofit2.Response;
import servicios.modelo.CacheAuthorization;

public class ServiciosLogin {
    private final DaoLogin daoLogin;
    private CacheAuthorization ca;

    @Inject
    public ServiciosLogin(DaoLogin daoLogin, CacheAuthorization ca) {
        this.daoLogin = daoLogin;
        this.ca = ca;
    }

    public Single<Either<String, Reader>> login(ReaderLogin readerLogin) {
        String credentials = Credentials.basic(readerLogin.getUsername(), readerLogin.getPassword());
        ca.setUser(readerLogin.getUsername());
        ca.setPass(readerLogin.getPassword());
        return daoLogin.login(credentials);
    }

    public Single<Either<String, ReaderLogin>> register(ReaderLogin reader) {
        return daoLogin.register(reader);
    }

    public Single<Response<String>> recoverPassword(String email) {
        return daoLogin.recoverPassword(email);
    }

    public Single<Response<String>> sendEmail(String email) {
        return daoLogin.sendEmail(email);
    }

    public Single<Response<String>> logout() {
        ca.setUser(null);
        ca.setPass(null);
        ca.setJwt(null);
        return daoLogin.logout();
    }
}
