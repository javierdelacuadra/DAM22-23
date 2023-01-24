package data;

import data.hibernate.JPAUtil;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.PersistenceException;
import model.Newspaper;
import model.Reader;
import model.Subscription;

import java.util.ArrayList;
import java.util.List;

public class DaoSubscriptions {
    private JPAUtil jpaUtil;
    private EntityManager em;

    @Inject
    public DaoSubscriptions(JPAUtil jpaUtil) {
        this.jpaUtil = jpaUtil;
        this.em = jpaUtil.getEntityManager();
    }

    public Integer save(Subscription subscription) {
        em = jpaUtil.getEntityManager();
        EntityTransaction transaction = null;

        try {
            transaction = em.getTransaction();
            transaction.begin();
            em.persist(subscription);
            transaction.commit();
            return 1;
        } catch (PersistenceException e) {
            assert transaction != null;
            if (transaction.isActive()) transaction.rollback();
            e.printStackTrace();
            return -1;
        } finally {
            if (em != null) em.close();
        }
    }

    public Integer update(Subscription subscription) {
        em = jpaUtil.getEntityManager();
        EntityTransaction transaction = null;

        try {
            transaction = em.getTransaction();
            transaction.begin();
            em.merge(subscription);
            transaction.commit();
            return 1;
        } catch (PersistenceException e) {
            assert transaction != null;
            if (transaction.isActive()) transaction.rollback();
            e.printStackTrace();
            return -1;
        } finally {
            if (em != null) em.close();
        }
    }

    public Either<Integer, List<Subscription>> get(Newspaper newspaper) {
        em = jpaUtil.getEntityManager();
        EntityTransaction tx = null;
        List<Subscription> subscriptions = new ArrayList<>();

        try {
            tx = em.getTransaction();
            tx.begin();
            subscriptions = em
                    .createNamedQuery("HQL_GET_ACTIVE_SUBSCRIPTIONS_BY_NEWSPAPER", Subscription.class)
                    .setParameter("id", newspaper.getId())
                    .getResultList();
            tx.commit();
        } catch (PersistenceException e) {
            assert tx != null;
            if (tx.isActive()) tx.rollback();
            e.printStackTrace();
        } finally {
            if (em != null) em.close();
        }
        return subscriptions.isEmpty() ? Either.left(-1) : Either.right(subscriptions);
    }

    public Either<Integer, List<Subscription>> get(Reader reader) {
        em = jpaUtil.getEntityManager();
        EntityTransaction tx = null;
        List<Subscription> subscriptions;

        try {
            tx = em.getTransaction();
            tx.begin();
            subscriptions = em
                    .createNamedQuery("HQL_GET_ACTIVE_SUBSCRIPTIONS_BY_READER", Subscription.class)
                    .setParameter("id", reader.getId())
                    .getResultList();
            tx.commit();
        } catch (PersistenceException e) {
            assert tx != null;
            if (tx.isActive()) tx.rollback();
            return Either.left(-1);
        } finally {
            if (em != null) em.close();
        }
        return Either.right(subscriptions);
    }
}