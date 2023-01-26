package servicios;

import data.DaoLogin;
import io.reactivex.rxjava3.core.Single;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import modelo.Usuario;
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

    public Single<Either<String, Usuario>> login(Usuario usuario) {
        String credentials = Credentials.basic(usuario.getNombre(), usuario.getPassword());
        ca.setUser(usuario.getNombre());
        ca.setPass(usuario.getPassword());
        return daoLogin.login(credentials);
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
