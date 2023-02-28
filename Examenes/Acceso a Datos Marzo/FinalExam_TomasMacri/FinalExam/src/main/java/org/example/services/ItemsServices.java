package org.example.services;

import io.vavr.control.Either;
import org.example.model.hibernate.Client;
import org.example.model.hibernate.Item;
import org.example.model.mongo.PurchaseMongo;

import java.util.List;

public interface ItemsServices {
    Either<String, List<Item>> getAll();

    Either<String, List<Item>> getAll(Client c);

    Either<String, Item> get(String name);

    int add(PurchaseMongo purchaseMongo);
}
