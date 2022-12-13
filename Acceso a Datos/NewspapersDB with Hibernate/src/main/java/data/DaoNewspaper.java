package data;

import data.hibernate.JPAUtil;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.PersistenceException;
import model.Newspaper;

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
        List list = null;
        em = jpaUtil.getEntityManager();

        try {
            list = em
                    .createNamedQuery("HQL_GET_ALL_NEWSPAPERS", Newspaper.class)
                    .getResultList();

        } catch (PersistenceException e) {
            e.printStackTrace();
        } finally {
            if (em != null) em.close();
        }

        return list.isEmpty() ? Either.left(-1) : Either.right(list);
    }

    public Integer addNewspaper(Newspaper newspaper) {
        em = jpaUtil.getEntityManager();
        EntityTransaction tx = null;

        try {
            tx = em.getTransaction();
            tx.begin();
            em.persist(newspaper);
            tx.commit();
            return 1;
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            e.printStackTrace();
            return -1;
        }
        finally {
            if (em != null)  em.close();
        }
    }

    public Integer deleteNewspaper(Integer id) {
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
        }
        finally {
            if (em != null)  em.close();
        }
    }

    public Integer updateNewspaper(Newspaper newspaper) {
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
}