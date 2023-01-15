package servicios;

import data.DaoLogin;
import jakarta.inject.Inject;
import model.Login;

public class ServicesLogin {

    private final DaoLogin daoLogin;

    @Inject
    public ServicesLogin(DaoLogin daoLogin) {
        this.daoLogin = daoLogin;
    }

    public Integer login(Login login) {
        return daoLogin.login(login);
    }
}
