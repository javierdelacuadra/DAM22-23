package dao;

import dao.hibernate.JPAUtil;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.PersistenceException;
import servicios.modelo.hibernate.PurchasesEntity;

import java.util.ArrayList;
import java.util.List;

public class DaoPurchases {

    private JPAUtil jpaUtil;
    private EntityManager em;

    @Inject
    public DaoPurchases() {
        jpaUtil = new JPAUtil();
        em = jpaUtil.getEntityManager();
    }

    public Integer addPurchase(PurchasesEntity purchase) {
        em = jpaUtil.getEntityManager();
        EntityTransaction transaction = null;

        try {
            transaction = em.getTransaction();
            transaction.begin();
            em.persist(purchase);
            transaction.commit();
            return purchase.getId();
        } catch (PersistenceException e) {
            assert transaction != null;
            if (transaction.isActive()) transaction.rollback();
            e.printStackTrace();
            return -1;
        } finally {
            if (em != null) em.close();
        }
    }

    public Either<Integer, List<PurchasesEntity>> get(int clientID) {
        List<PurchasesEntity> purchases = new ArrayList<>();
        em = jpaUtil.getEntityManager();

        try {
            purchases = em
                    .createNamedQuery("HQL_GET_PURCHASES_BY_CLIENT_ID", PurchasesEntity.class)
                    .setParameter("id", clientID)
                    .getResultList();

        } catch (PersistenceException e) {
            e.printStackTrace();
        } finally {
            if (em != null) em.close();
        }

        assert purchases != null;
        return purchases.isEmpty() ? Either.left(-1) : Either.right(purchases);
    }
}
