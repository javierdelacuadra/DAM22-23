package org.example.dao.impl;

import io.vavr.control.Either;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.PersistenceException;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.example.common.NumericConstants;
import org.example.dao.ClientsDao;
import org.example.dao.impl.connections.DBConnection;
import org.example.dao.impl.connections.JPAUtil;
import org.example.model.hibernate.Client;

import java.util.*;

import static com.mongodb.client.model.Accumulators.sum;
import static com.mongodb.client.model.Aggregates.*;
import static com.mongodb.client.model.Filters.eq;

public class ClientsDaoImpl extends DaoGenericsImpl implements ClientsDao {
    private JPAUtil jpaUtil;

    private DBConnection dbConnection;

    @Inject
    public ClientsDaoImpl(JPAUtil jpaUtil, DBConnection dbConnection) {
        this.jpaUtil = jpaUtil;
        this.dbConnection = dbConnection;
    }
    @Override
    public int add(Client client) {
        return 0;
    }

    @Override
    public int update(Client client) {
        return 0;
    }

    @Override
    public int delete(int id){
        return 0;
    }

    @Override
    public Either<Integer, Client> get(int id) {
        return null;
    }

    @Override
    public int delete(Client c, boolean confirmed) {
        if (!confirmed) {
            EntityManager entityManager = jpaUtil.getEntityManager();
            EntityTransaction tx = entityManager.getTransaction();
            tx.begin();
            try {
                int updated = entityManager.createNamedQuery("HQL_DELETE_CLIENT_BY_NAME")
                        .setParameter("clientName", c.getName())
                        .executeUpdate();
                tx.commit();
                return updated;
            } catch (PersistenceException e){
                if (tx.isActive()) tx.rollback();
                return NumericConstants.DATA_INTEGRITY_VIOLATION_EXCEPTION;
            } finally {
                entityManager.close();
            }
        } else {

            //HAS CONFIRMED
            EntityManager em = jpaUtil.getEntityManager();
            EntityTransaction tx = em.getTransaction();
            try {
                tx.begin();
                Client fullClient = em.createNamedQuery("HQL_GET_CLIENT_BY_NAME", Client.class)
                        .setParameter("clientName", c.getName()).getSingleResult();
                List<Integer> purchasedProductsIDs = em.createNamedQuery("HQL_GET_ITEMS_PURCHASED_BY_CLIENT_NAME", Integer.class).setParameter("nameClient", fullClient.getName())
                        .getResultList();

                em.createNamedQuery("HQL_DELETE_PP_BY_ID").setParameter("listId", purchasedProductsIDs).executeUpdate();

                em.remove(em.merge(fullClient));
                tx.commit();
                return 1;
            } catch (PersistenceException e) {
                if (tx.isActive()) tx.rollback();
                return NumericConstants.INTEGRITY_CONSTRAINT_VIOLATION_EXCEPTION;
            } finally {
                em.close();
            }
        }
    }

    @Override
    public Map<Client, Integer> amountOfMilkBought()
    {
        //MONGO
//        [
//        {
//            $unwind: {
//                path: "$items",
//            },
//        },
//        {
//            $match: {
//                "items.name": "milk",
//            },
//        },
//        {
//            $group:
//            /**
//             * _id: The id of the group.
//             * fieldN: The first field name.
//             */
//            {
//                _id: "$client.name",
//                        priceMilk: {
//                $sum: "$items.amount",
//            },
//            },
//        },
//]
        //JAVA CODE
//        Arrays.asList(new Document("$unwind",
//                        new Document("path", "$items")),
//                new Document("$match",
//                        new Document("items.name", "milk")),
//                new Document("$group",
//                        new Document("_id", "$client.name")
//                                .append("priceMilk",
//                                        new Document("$sum", "$items.amount"))))


        List<Bson> aggregations = Arrays.asList(
                unwind("$items"),
                match(eq("items.name", "milk")),
                group("$client.name", sum("amountOfMilk", "$items.amount")));

        List<Document> documents = dbConnection.getEst().aggregate(aggregations).into(new ArrayList<>());
        Map<Client, Integer> amountOfMilk =  new HashMap<>();

        for (Document document:
             documents)
        {
            amountOfMilk.put(new Client(document.getString("_id")), document.getInteger("amountOfMilk"));

        }
        return amountOfMilk;
    }

    @Override
    public Either<Integer, Client> get(Client client) {
        EntityManager em = jpaUtil.getEntityManager();
        try {
            //can't do it with a find because I don't know the id of the client
            return Either.right(em.createNamedQuery("HQL_GET_CLIENT_BY_NAME", Client.class)
                    .setParameter("clientName", client.getName()).getSingleResult() );
        } catch (PersistenceException e){
            return Either.left(NumericConstants.DB_EXCEPTION_CODE);
        } finally {
            em.close();
        }
    }

    @Override
    public Either<Integer, List<Client>> getAll() {
        EntityManager em = jpaUtil.getEntityManager();
        try {
            return Either.right(em.createNamedQuery("HQL_ALL_CLIENTS", Client.class).getResultList());
        } catch (Exception e) {
            return Either.left(NumericConstants.DB_EXCEPTION_CODE);
        } finally {
            em.close();
        }

    }
}
