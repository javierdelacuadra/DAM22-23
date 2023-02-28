package org.example.services;

import io.vavr.control.Either;
import org.example.model.hibernate.Client;

import java.util.List;
import java.util.Map;

public interface ClientServices {
    Either<String, List<Client>> getAll();

    Either<String, Client> get(String name);

    int delete(Client client, boolean confirmed);

    Map<Client, Integer> amountOfMilkBought();
}
