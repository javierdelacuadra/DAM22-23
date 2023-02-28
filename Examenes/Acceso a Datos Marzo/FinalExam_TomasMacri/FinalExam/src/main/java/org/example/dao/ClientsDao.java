package org.example.dao;

import io.vavr.control.Either;
import org.example.model.hibernate.Client;

import java.util.Map;

public interface ClientsDao extends DaoStandard<Client>{
    int delete(Client c, boolean confirmed);

    Map<Client, Integer> amountOfMilkBought();

    Either<Integer, Client> get(Client client);
}
