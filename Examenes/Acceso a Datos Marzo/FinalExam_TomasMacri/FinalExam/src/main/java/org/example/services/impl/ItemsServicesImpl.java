package org.example.services.impl;

import io.vavr.control.Either;
import jakarta.inject.Inject;
import org.example.dao.ItemsDao;
import org.example.model.hibernate.Client;
import org.example.model.hibernate.Item;
import org.example.model.mongo.PurchaseMongo;
import org.example.services.ItemsServices;

import java.util.List;

public class ItemsServicesImpl implements ItemsServices {

    private ItemsDao itemsDao;

    @Inject
    public ItemsServicesImpl(ItemsDao itemsDao) {
        this.itemsDao = itemsDao;
    }

    @Override
    public Either<String, List<Item>> getAll() {
        return itemsDao.getAll().mapLeft(integer -> "DB ERROR");
    }

    @Override
    public Either<String, List<Item>> getAll(Client c) {
        return itemsDao.getAll(c).mapLeft(integer -> "DB ERROR");
    }

    @Override
    public Either<String, Item> get(String name) {
        return itemsDao.get(new Item(name)).mapLeft(integer -> "DB ERROR");
    }

    @Override
    public int add(PurchaseMongo purchaseMongo) {
        return itemsDao.add(purchaseMongo);
    }
}
