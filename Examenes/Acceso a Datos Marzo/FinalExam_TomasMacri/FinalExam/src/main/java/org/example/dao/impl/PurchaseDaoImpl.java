package org.example.dao.impl;

import io.vavr.control.Either;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import org.example.dao.PurchaseDao;
import org.example.dao.impl.connections.JPAUtil;
import org.example.model.hibernate.Purchase;

import java.util.List;

public class PurchaseDaoImpl extends DaoGenericsImpl implements PurchaseDao {

    private JPAUtil jpaUtil;

    @Inject
    public PurchaseDaoImpl() {
        jpaUtil = new JPAUtil();
    }
    @Override
    public int add(Purchase purchase) {
        return add(jpaUtil, purchase);
    }

    @Override
    public int update(Purchase purchase) {
        return 0;
    }

    @Override
    public int delete(int id) {
        return 0;
    }

    @Override
    public Either<Integer, Purchase> get(int id) {
        return null;
    }

    @Override
    public Either<Integer, List<Purchase>> getAll() {
        // CLOSE CONNECTION TO GET LAZY INITIALIZATION EXCEPTION
        EntityManager em = jpaUtil.getEntityManager();
        try {
            List<Purchase> purchases = em.createNamedQuery("HQL_ALL_PURCHASES", Purchase.class).getResultList();
            purchases.forEach(purchase -> purchase.getItems_purchased().size());
            return Either.right(purchases);
        } catch (Exception e) {
            return Either.left(0);
        } finally {
            em.close();
        }
    }
}
