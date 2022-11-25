package services;

import dao.DaoClients;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import model.Client;

import java.util.List;

public class ServicesClients {

    private final DaoClients dao;

    @Inject
    public ServicesClients(DaoClients dao) {
        this.dao = dao;
    }

    public Either<String, Boolean> saveClients(List<Client> clients) {
        return dao.save(clients);
    }

    public Either<Integer, List<Client>> getAllClients() {
        return dao.getAll();
    }
}