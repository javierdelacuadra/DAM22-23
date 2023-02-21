package servicios;

import dao.DaoClients;
import jakarta.inject.Inject;
import servicios.modelo.hibernate.ClientsEntity;

public class ServicesClients {
    private final DaoClients dao;

    @Inject
    public ServicesClients(DaoClients dao) {
        this.dao = dao;
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
}
