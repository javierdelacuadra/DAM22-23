package domain.servicios;

import dao.DaoExamen;
import modelo.User;

public class ServiciosLogin {

    public ServiciosLogin() {
    }

    public static User getLogin(String user, String pass) {
        return DaoExamen.checkLogin(user, pass);
    }
}