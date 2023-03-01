package dao;

import dao.hibernate.JPAUtil;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceException;
import servicios.modelo.hibernate.OrderItemsEntity;

import java.util.ArrayList;
import java.util.List;

public class DaoOrderItems {
    private JPAUtil jpaUtil;
    private EntityManager em;

    @Inject
    public DaoOrderItems() {
        jpaUtil = new JPAUtil();
        em = jpaUtil.getEntityManager();
    }

    public Either<Integer, List<OrderItemsEntity>> getAll() {
        List<OrderItemsEntity> orders = new ArrayList<>();
        em = jpaUtil.getEntityManager();

        try {
            orders = em
                    .createNamedQuery("HQL_GET_ALL_ORDER_ITEMS", OrderItemsEntity.class)
                    .getResultList();

        } catch (PersistenceException e) {
            e.printStackTrace();
        } finally {
            if (em != null) em.close();
        }

        return orders.isEmpty() ? Either.left(-1) : Either.right(orders);
    }
}
