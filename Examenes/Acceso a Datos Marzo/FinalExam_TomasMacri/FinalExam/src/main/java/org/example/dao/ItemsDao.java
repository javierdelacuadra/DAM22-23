package org.example.dao;

import io.vavr.control.Either;
import org.example.model.hibernate.Client;
import org.example.model.hibernate.Item;
import org.example.model.mongo.PurchaseMongo;

import java.util.List;

public interface ItemsDao extends DaoStandard<Item> {

    int add(PurchaseMongo purchaseMongo);
    Either<Integer, List<Item>> getAll(Client client);

    Either<Integer, Item> get(Item item);
}
