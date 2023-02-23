package servicios;

import data.DaoLogin;
import io.reactivex.rxjava3.core.Single;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import modelo.User;
import okhttp3.Credentials;
import servicios.modelo.CacheAuthorization;

public class ServiciosLogin {
    private final DaoLogin daoLogin;
    private CacheAuthorization ca;

    @Inject
    public ServiciosLogin(DaoLogin daoLogin, CacheAuthorization ca) {
        this.daoLogin = daoLogin;
        this.ca = ca;
    }

    public Single<Either<String, User>> login(User usuario) {
        String credentials = Credentials.basic(usuario.getNombre(), usuario.getPassword());
        ca.setUser(usuario.getNombre());
        ca.setPass(usuario.getPassword());
        return daoLogin.login(credentials);
    }
}
