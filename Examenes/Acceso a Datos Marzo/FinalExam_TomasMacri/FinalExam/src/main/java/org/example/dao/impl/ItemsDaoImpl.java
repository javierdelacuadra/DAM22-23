package org.example.dao.impl;

import com.google.gson.Gson;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceException;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.example.common.NumericConstants;
import org.example.dao.ItemsDao;
import org.example.dao.impl.connections.DBConnection;
import org.example.dao.impl.connections.JPAUtil;
import org.example.model.hibernate.Client;
import org.example.model.hibernate.Item;
import org.example.model.mongo.ItemMongo;
import org.example.model.mongo.PurchaseMongo;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Projections.excludeId;
import static com.mongodb.client.model.Projections.include;
import static com.mongodb.client.model.Updates.push;
import static com.mongodb.client.model.Updates.set;

public class ItemsDaoImpl extends DaoGenericsImpl implements ItemsDao {

    private JPAUtil jpaUtil;

    private DBConnection dbConnection;

    private Gson gson;

    @Inject
    public ItemsDaoImpl(JPAUtil jpaUtil, DBConnection dbConnection, Gson gson) {
        this.jpaUtil = jpaUtil;
        this.dbConnection = dbConnection;
        this.gson = gson;

    }

    @Override
    public int add(Item item) {
        return 0;
    }

    @Override
    public int update(Item item) {
        return 0;
    }

    @Override
    public int delete(int id) {
        return 0;
    }

    @Override
    public Either<Integer, Item> get(int id) {
        return null;
    }

    @Override
    public Either<Integer, List<Item>> getAll() {
        EntityManager em = jpaUtil.getEntityManager();
        try {
            return Either.right(em.createNamedQuery("HQL_ALL_ITEMS", Item.class).getResultList());
        } catch (PersistenceException e) {
            return Either.left(NumericConstants.DB_EXCEPTION_CODE);
        } finally {
            em.close();
        }
    }

    @Override
    public int add(PurchaseMongo purchaseMongo) {
        ItemMongo newItem = purchaseMongo.getItems().get(0);
        Integer newPrice = newItem.getPrice()*newItem.getAmount();
        Document d = Document.parse(gson.toJson(newItem));
        Bson filter = eq("_id", purchaseMongo.get_id());
        Bson updateOperation = push("items", d);
        update(dbConnection, filter, updateOperation);
        //get the name of the buyer and the balance
        Document documentOfClient = dbConnection.getEst().find(filter).projection(include("client")).first();
        String nameClient = documentOfClient.get("client", Document.class).getString("name");
        Double balanceOfClient = documentOfClient.get("client", Document.class).getDouble("balance");
        Bson filterNewBalance = eq("client.name", nameClient);
        Bson set = set("client.balance", balanceOfClient-newPrice);
        return Integer.parseInt(dbConnection.getEst().updateMany(filterNewBalance, set).getModifiedCount()+"");
    }

    @Override
    public Either<Integer, List<Item>> getAll(Client client) {
        EntityManager em = jpaUtil.getEntityManager();
        try {
            return Either.right(em
                    .createNamedQuery("HQL_ALL_ITEMS_BOUGHT_BY_A_CLIENT", Item.class)
                    .setParameter("clientName", client.getName())
                    .getResultList());
        } catch (PersistenceException e) {
            return Either.left(NumericConstants.DB_EXCEPTION_CODE);
        } finally {
            em.close();
        }
    }

    @Override
    public Either<Integer, Item> get(Item item) {
        EntityManager em = jpaUtil.getEntityManager();
        try {
            //can't do it with a find because I don't know the id of the item
            return Either.right(em.createNamedQuery("HQL_ITEM_BY_NAME", Item.class)
                    .setParameter("itemName", item.getName())
                    .getSingleResult());
        } catch (PersistenceException e) {
            return Either.left(NumericConstants.DB_EXCEPTION_CODE);
        } finally {
            em.close();
        }
    }
}
