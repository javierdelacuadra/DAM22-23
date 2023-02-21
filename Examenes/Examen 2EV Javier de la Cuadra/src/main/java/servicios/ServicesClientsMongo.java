package servicios;

import dao.DaoClientsMongo;
import jakarta.inject.Inject;
import servicios.modelo.mongo.ClientMongo;

public class ServicesClientsMongo {
    private final DaoClientsMongo dao;

    @Inject
    public ServicesClientsMongo(DaoClientsMongo dao) {
        this.dao = dao;
    }

    public int updateBalance(ClientMongo client) {
        return dao.update(client);
    }
}
