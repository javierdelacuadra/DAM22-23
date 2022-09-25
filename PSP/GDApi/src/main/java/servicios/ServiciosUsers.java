package servicios;

import data.DaoUsers;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import modelo.ResponseUser;

public class ServiciosUsers {

    private final DaoUsers daoUsers;

    @Inject
    public ServiciosUsers(DaoUsers daoUsers) {
        this.daoUsers = daoUsers;
    }

    public Either<String, ResponseUser> getUser(String username) {
        return daoUsers.getUser(username);
    }
}