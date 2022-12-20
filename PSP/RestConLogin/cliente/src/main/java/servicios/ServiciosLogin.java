package servicios;

import data.DaoLogin;
import io.reactivex.rxjava3.core.Single;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import model.ReaderLogin;
import okhttp3.Credentials;
import retrofit2.Response;

public class ServiciosLogin {
    private final DaoLogin daoLogin;

    @Inject
    public ServiciosLogin(DaoLogin daoLogin) {
        this.daoLogin = daoLogin;
    }

    public Single<Either<String, ReaderLogin>> login(ReaderLogin readerLogin) {
        String credentials = Credentials.basic(readerLogin.getUsername(), readerLogin.getPassword());
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
        return daoLogin.logout();
    }
}
