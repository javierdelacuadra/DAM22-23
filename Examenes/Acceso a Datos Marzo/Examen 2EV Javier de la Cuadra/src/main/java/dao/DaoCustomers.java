package dao;

import dao.hibernate.JPAUtil;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.Query;
import servicios.modelo.hibernate.CustomersEntity;
import servicios.modelo.hibernate.OrderItemsEntity;
import servicios.modelo.hibernate.OrdersEntity;

import java.util.List;

public class DaoCustomers {
    private JPAUtil jpaUtil;
    private EntityManager em;

    @Inject
    public DaoCustomers() {
        jpaUtil = new JPAUtil();
        em = jpaUtil.getEntityManager();
    }

    public CustomersEntity get(String email) {
        CustomersEntity customer = new CustomersEntity();
        em = jpaUtil.getEntityManager();

        try {
            customer = em
                    .createNamedQuery("HQL_GET_CUSTOMER_BY_EMAIL", CustomersEntity.class)
                    .setParameter("email", email)
                    .getSingleResult();

        } catch (PersistenceException e) {
            e.printStackTrace();
        } finally {
            if (em != null) em.close();
        }
        return customer;
    }

    public CustomersEntity get(String firstName, String lastName) {
        CustomersEntity customer = new CustomersEntity();
        em = jpaUtil.getEntityManager();

        try {
            customer = em
                    .createNamedQuery("HQL_GET_CUSTOMER_BY_NAME", CustomersEntity.class)
                    .setParameter("firstName", firstName)
                    .setParameter("lastName", lastName)
                    .getSingleResult();

        } catch (PersistenceException e) {
            e.printStackTrace();
        } finally {
            if (em != null) em.close();
        }
        return customer;
    }

    public Integer delete(CustomersEntity customer, boolean delete) {
        em = jpaUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        if (delete) {
            try {
                List<OrderItemsEntity> items = em
                        .createNamedQuery("HQL_GET_ORDER_ITEMS_PURCHASED_BY_CLIENT_ID", OrderItemsEntity.class)
                        .setParameter("id", customer.getId())
                        .getResultList();

                String hqlDeletePurchasesItems = "DELETE from OrderItemsEntity p where p.id = :id";
                for (OrderItemsEntity item : items) {
                    Query query = em.createQuery(hqlDeletePurchasesItems);
                    query.setParameter("id", item.getId());
                    query.executeUpdate();
                }
                String hqlDeletePurchases = "DELETE from OrdersEntity p where p.id = :id";
                for (OrdersEntity order : customer.getOrders()) {
                    Query query = em.createQuery(hqlDeletePurchases);
                    query.setParameter("id", order.getId());
                    query.executeUpdate();
                }
                em.remove(em.merge(customer));
                tx.commit();
                return 1;
            } catch (PersistenceException e) {
                if (tx.isActive()) tx.rollback();
                return -1;
            } finally {
                if (em != null) em.close();
            }
        } else {
            try {
                String deleteCustomerQuery = "DELETE from CustomersEntity c where c.id = :id ";
                Query query = em.createQuery(deleteCustomerQuery);
                query.setParameter("id", customer.getId());
                query.executeUpdate();
                tx.commit();
                return 1;
            } catch (PersistenceException e) {
                if (tx.isActive()) tx.rollback();
                return -1;
            } finally {
                em.close();
            }
        }
    }
}
