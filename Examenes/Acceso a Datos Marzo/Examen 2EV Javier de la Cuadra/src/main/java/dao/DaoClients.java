package dao;

import com.mongodb.client.MongoCollection;
import dao.hibernate.JPAUtil;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.Query;
import org.bson.Document;
import servicios.modelo.hibernate.ClientsEntity;
import servicios.modelo.hibernate.ItemsEntity;
import servicios.modelo.hibernate.PurchasesEntity;
import servicios.modelo.hibernate.PurchasesItemsEntity;

import java.util.*;
import java.util.stream.Collectors;

import static com.mongodb.client.model.Accumulators.addToSet;
import static com.mongodb.client.model.Aggregates.*;
import static com.mongodb.client.model.Aggregates.project;
import static com.mongodb.client.model.Filters.gte;
import static com.mongodb.client.model.Projections.*;

public class DaoClients {
    private JPAUtil jpaUtil;
    private EntityManager em;

    @Inject
    public DaoClients() {
        jpaUtil = new JPAUtil();
        em = jpaUtil.getEntityManager();
    }

    public ClientsEntity get(int clientID) {
        ClientsEntity client = new ClientsEntity();
        em = jpaUtil.getEntityManager();

        try {
            client = em
                    .createNamedQuery("HQL_GET_CLIENT_BY_ID", ClientsEntity.class)
                    .setParameter("id", clientID)
                    .getSingleResult();

        } catch (PersistenceException e) {
            e.printStackTrace();
        } finally {
            if (em != null) em.close();
        }
        return client;
    }

    public Integer delete(ClientsEntity client) {
        em = jpaUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            List<PurchasesItemsEntity> items = em
                    .createNamedQuery("HQL_GET_PURCHASES_ITEMS_PURCHASED_BY_CLIENT_ID", PurchasesItemsEntity.class)
                    .setParameter("id", client.getId())
                    .getResultList();

            String hqlDeletePurchasesItems = "DELETE from PurchasesItemsEntity p where p.id = :id";
            for (PurchasesItemsEntity item : items) {
                Query query = em.createQuery(hqlDeletePurchasesItems);
                query.setParameter("id", item.getId());
                query.executeUpdate();
            }
            String hqlDeletePurchases = "DELETE from PurchasesEntity p where p.id = :id";
            for (PurchasesEntity purchase : client.getPurchasesById()) {
                Query query = em.createQuery(hqlDeletePurchases);
                query.setParameter("id", purchase.getId());
                query.executeUpdate();
            }
            em.remove(em.merge(client));
            tx.commit();
            return 1;
        } catch (PersistenceException e) {
            if (tx.isActive()) tx.rollback();
            return -1;
        } finally {
            if (em != null) em.close();
        }
    }
}
