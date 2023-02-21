package dao;

import dao.hibernate.JPAUtil;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.Query;
import servicios.modelo.hibernate.ClientsEntity;
import servicios.modelo.hibernate.PurchasesEntity;
import servicios.modelo.hibernate.PurchasesItemsEntity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
            String hqlDeletePurchasesItems = "DELETE from PurchasesItemsEntity p where p.purchases.id = :id";
            Query query1 = em.createQuery(hqlDeletePurchasesItems);
            List<PurchasesEntity> purchases = client.getPurchasesById();
            List<Integer> purchasesItemsIDs = new ArrayList<>();
            for (PurchasesEntity purchase : purchases) {
                purchasesItemsIDs.add(purchase.getId());
            }
            for (Integer purchasedItem : purchasesItemsIDs) {
                query1.setParameter("id", purchasedItem);
                query1.executeUpdate();
            }
            String hqlDeletePurchases = "DELETE from PurchasesEntity p where p.clientsByIdClient.id = :id";
            Query query = em.createQuery(hqlDeletePurchases);
            query.setParameter("id", client.getId());
            query.executeUpdate();
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
