package domain.servicios;

import dao.DaoExamen;
import modelo.User;

public class ServiciosUsers {

    public ServiciosUsers() {
    }

    public User getUserByName(String name) {
        return DaoExamen.getUserByName(name);
    }
}
