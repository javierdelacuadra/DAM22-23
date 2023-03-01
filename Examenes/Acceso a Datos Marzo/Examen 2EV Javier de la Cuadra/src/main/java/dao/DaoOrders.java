package dao;

import dao.hibernate.JPAUtil;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.PersistenceException;
import servicios.modelo.hibernate.MenuItemsEntity;
import servicios.modelo.hibernate.OrdersEntity;

import java.util.ArrayList;
import java.util.List;

public class DaoOrders {
    private JPAUtil jpaUtil;
    private EntityManager em;

    @Inject
    public DaoOrders() {
        jpaUtil = new JPAUtil();
        em = jpaUtil.getEntityManager();
    }

    public Integer add(OrdersEntity order) {
        em = jpaUtil.getEntityManager();
        EntityTransaction transaction = null;

        try {
            transaction = em.getTransaction();
            transaction.begin();
            em.persist(order);
            transaction.commit();
            return order.getId();
        } catch (PersistenceException e) {
            assert transaction != null;
            if (transaction.isActive()) transaction.rollback();
            e.printStackTrace();
            return -1;
        } finally {
            if (em != null) em.close();
        }
    }

    public Either<Integer, List<MenuItemsEntity>> get(int clientID) {
        List<MenuItemsEntity> items = new ArrayList<>();
        em = jpaUtil.getEntityManager();

        try {
            items = em
                    .createNamedQuery("HQL_GET_ITEMS_ORDERED_BY_CLIENT_ID", MenuItemsEntity.class)
                    .setParameter("id", clientID)
                    .getResultList();

        } catch (PersistenceException e) {
            e.printStackTrace();
        } finally {
            if (em != null) em.close();
        }

        assert items != null;
        return items.isEmpty() ? Either.left(-1) : Either.right(items);
    }

    public Either<Integer, List<OrdersEntity>> getAll() {
        List<OrdersEntity> orders = new ArrayList<>();
        em = jpaUtil.getEntityManager();

        try {
            orders = em
                    .createNamedQuery("HQL_GET_ALL_ORDERS", OrdersEntity.class)
                    .getResultList();

        } catch (PersistenceException e) {
            e.printStackTrace();
        } finally {
            if (em != null) em.close();
        }

        return orders.isEmpty() ? Either.left(-1) : Either.right(orders);
    }
}
