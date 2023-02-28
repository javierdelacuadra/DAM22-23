package org.example.services.impl;

import io.vavr.control.Either;
import jakarta.inject.Inject;
import org.example.dao.ClientsDao;
import org.example.model.hibernate.Client;

import java.util.List;
import java.util.Map;

public class ClientServicesImpl implements org.example.services.ClientServices {

    private ClientsDao clientsDao;

    @Inject
    public ClientServicesImpl(ClientsDao clientsDao){
        this.clientsDao = clientsDao;
    }

    @Override
    public Either<String, List<Client>> getAll(){
        return clientsDao.getAll().mapLeft(integer -> "ERROR");
    }

    @Override
    public Either<String, Client> get(String name) {
        return clientsDao.get(new Client(name)).mapLeft(integer -> "ERROR");
    }
    @Override
    public int delete(Client client, boolean confirmed) {
        return clientsDao.delete(client, confirmed);
    }

    @Override
    public Map<Client, Integer> amountOfMilkBought(){return clientsDao.amountOfMilkBought();}
}
