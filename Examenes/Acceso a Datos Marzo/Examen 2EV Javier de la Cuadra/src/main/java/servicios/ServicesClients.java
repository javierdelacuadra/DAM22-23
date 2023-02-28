package servicios;

import dao.DaoClients;
import dao.DaoClientsMongo;
import jakarta.inject.Inject;
import servicios.modelo.hibernate.ClientsEntity;

import java.util.List;
import java.util.Map;

public class ServicesClients {
    private final DaoClients dao;
    private final DaoClientsMongo daoMongo;

    @Inject
    public ServicesClients(DaoClients dao, DaoClientsMongo daoMongo) {
        this.dao = dao;
        this.daoMongo = daoMongo;
    }

    public ClientsEntity getClientById(int clientID) {
        return dao.get(clientID);
    }

    public Integer deleteAllClientData(ClientsEntity client, boolean delete) {
        if (delete) {
            return dao.delete(client);
        } else {
            if (!client.getPurchasesById().isEmpty()) {
                return -2;
            } else {
                return -1;
            }
        }
    }

    public List<String> aggregation() {
        return daoMongo.aggregation();
    }
}
