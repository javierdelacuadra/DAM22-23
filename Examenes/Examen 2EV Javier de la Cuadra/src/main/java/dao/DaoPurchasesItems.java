package dao;

import dao.hibernate.JPAUtil;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.PersistenceException;
import servicios.modelo.hibernate.PurchasesItemsEntity;

import java.util.ArrayList;
import java.util.List;

public class DaoPurchasesItems {

    private JPAUtil jpaUtil;
    private EntityManager em;

    @Inject
    public DaoPurchasesItems() {
        jpaUtil = new JPAUtil();
        em = jpaUtil.getEntityManager();
    }

    public Integer addPurchasedItems(List<PurchasesItemsEntity> purchasesItems) {
        em = jpaUtil.getEntityManager();
        EntityTransaction transaction = null;

        try {
            transaction = em.getTransaction();
            transaction.begin();
            for (PurchasesItemsEntity purchasedItem : purchasesItems) {
                em.persist(purchasedItem);
            }
            transaction.commit();
            return 1;
        } catch (PersistenceException e) {
            assert transaction != null;
            if (transaction.isActive()) transaction.rollback();
            return -1;
        } finally {
            if (em != null) em.close();
        }
    }

    public Either<Integer, List<PurchasesItemsEntity>> get(List<Integer> purchasesIDs) {
        List<PurchasesItemsEntity> articles;
        List<PurchasesItemsEntity> list = new ArrayList<>();
        em = jpaUtil.getEntityManager();

        try {
            for (Integer purchasesID : purchasesIDs) {
                articles = em
                        .createNamedQuery("HQL_GET_PURCHASED_ITEMS_BY_ID_CLIENT", PurchasesItemsEntity.class)
                        .setParameter("id", purchasesID)
                        .getResultList();
                list.addAll(articles);
            }
        } catch (PersistenceException e) {
            e.printStackTrace();
        } finally {
            if (em != null) em.close();
        }

        return list.isEmpty() ? Either.left(-1) : Either.right(list);
    }
}
