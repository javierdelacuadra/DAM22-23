package servicios;

import data.DaoFarmeo;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import modelo.ResponseUser;

import java.io.IOException;

public class ServiciosFarmeo {

    private final DaoFarmeo daoFarmeo;

    @Inject
    public ServiciosFarmeo(DaoFarmeo daoFarmeo) {
        this.daoFarmeo = daoFarmeo;
    }

    public Either<String, ResponseUser> getUser(String username) throws IOException {
        return daoFarmeo.getUser(username);
    }
}