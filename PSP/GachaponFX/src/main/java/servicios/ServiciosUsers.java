package servicios;

import data.DaoUsers;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import modelo.ResponseUser;

import java.io.IOException;

public class ServiciosUsers {

    private final DaoUsers daoUsers;

    @Inject
    public ServiciosUsers(DaoUsers daoUsers) {
        this.daoUsers = daoUsers;
    }

    public Either<String, ResponseUser> getUser(String username) throws IOException {
        return daoUsers.getUser(username);
    }
}