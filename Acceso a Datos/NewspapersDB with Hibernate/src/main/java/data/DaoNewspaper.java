package data;

import data.hibernate.JPAUtil;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.PersistenceException;
import model.Newspaper;

import java.util.ArrayList;
import java.util.List;

public class DaoNewspaper {
    private JPAUtil jpaUtil;
    private EntityManager em;

    @Inject
    public DaoNewspaper(JPAUtil jpaUtil) {
        this.jpaUtil = jpaUtil;
        this.em = jpaUtil.getEntityManager();
    }

    public Either<Integer, List<Newspaper>> getAll() {
        List<Newspaper> newspapers = new ArrayList<>();
        em = jpaUtil.getEntityManager();

        try {
            newspapers = em
                    .createNamedQuery("HQL_GET_ALL_NEWSPAPERS", Newspaper.class)
                    .getResultList();

        } catch (PersistenceException e) {
            e.printStackTrace();
        } finally {
            if (em != null) em.close();
        }

        return newspapers.isEmpty() ? Either.left(-1) : Either.right(newspapers);
    }

    public Integer add(Newspaper newspaper) {
        em = jpaUtil.getEntityManager();
        EntityTransaction transaction = null;

        try {
            transaction = em.getTransaction();
            transaction.begin();
            em.persist(newspaper);
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

    public Integer delete(Integer id) {
        em = jpaUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            Newspaper newspaper = em.find(Newspaper.class, id);
            em.remove(em.merge(newspaper));
            tx.commit();
            return 1;
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            e.printStackTrace();
            return -1;
        } finally {
            if (em != null) em.close();
        }
        //TODO: investigar transaction para el deleteNewspaper
    }

    public Integer update(Newspaper newspaper) {
        em = jpaUtil.getEntityManager();
        EntityTransaction tx = null;

        try {
            tx = em.getTransaction();
            tx.begin();
            em.merge(newspaper);
            tx.commit();
            return 1;
        } catch (PersistenceException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
            return -1;
        } finally {
            if (em != null) em.close();
        }
    }

    public List<Newspaper> getFromSpecificNewspaper(int newspaperID) {
        List<Newspaper> newspapers = new ArrayList<>();
        em = jpaUtil.getEntityManager();

        try {
            newspapers = em
                    .createNamedQuery("HQL_GET_ALL_ARTICLES_OF_SPECIFIC_NEWSPAPER", Newspaper.class)
                    .setParameter("id", newspaperID)
                    .getResultList();

        } catch (PersistenceException e) {
            e.printStackTrace();
        } finally {
            if (em != null) em.close();
        }

        return newspapers;
    }
}