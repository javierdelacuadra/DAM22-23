package domain.servicios;

import dao.DaoLogin;
import jakarta.inject.Inject;
import model.ReaderLogin;

import java.time.LocalDateTime;

public class ServiciosLogin {

    private final DaoLogin daoLogin;

    @Inject
    public ServiciosLogin(DaoLogin daoLogin) {
        this.daoLogin = daoLogin;
    }

    public ReaderLogin getLogin(String user, String pass) {
        return daoLogin.getLogin(user, pass);
    }

    public ReaderLogin addLogin(ReaderLogin login) {
        return daoLogin.addLogin(login);
    }

    public void activarUsuario(String code) {
        daoLogin.activarUsuario(code);
    }
}
