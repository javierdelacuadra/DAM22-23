package domain.servicios;

import dao.DaoLogin;
import jakarta.inject.Inject;
import model.ReaderLogin;

public class ServiciosLogin {

    private final DaoLogin daoLogin;

    @Inject
    public ServiciosLogin(DaoLogin daoLogin) {
        this.daoLogin = daoLogin;
    }

    public ReaderLogin getLogin(String user, char[] pass) {
        return daoLogin.checkLogin(user, pass);
    }

    public ReaderLogin addLogin(ReaderLogin login) {
        return daoLogin.addLogin(login);
    }

    public void activarUsuario(String code) {
        daoLogin.activarUsuario(code);
    }

    public String passwordRecovery(String email) {
        return daoLogin.passwordRecovery(email);
    }

    public String emailResend(String email) {
        return daoLogin.emailResend(email);
    }

    public void crearNuevaPassword(String password, String code) {
        daoLogin.crearNuevaPassword(password, code);
    }
}
