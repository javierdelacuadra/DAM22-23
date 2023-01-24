package data;

import data.hibernate.JPAUtil;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.Query;
import model.ArticleType;
import model.Login;
import model.Newspaper;
import model.Reader;

import java.util.ArrayList;
import java.util.List;

public class DaoReaders {
    private JPAUtil jpaUtil;
    private EntityManager em;

    @Inject
    public DaoReaders(JPAUtil jpaUtil) {
        this.jpaUtil = jpaUtil;
        this.em = jpaUtil.getEntityManager();
    }

    public Either<Integer, List<Reader>> getAll() {
        List<Reader> readers = new ArrayList<>();
        em = jpaUtil.getEntityManager();

        try {
            readers = em
                    .createNamedQuery("HQL_GET_ALL_READERS", Reader.class)
                    .getResultList();

        } catch (PersistenceException e) {
            e.printStackTrace();
        } finally {
            if (em != null) em.close();
        }

        return readers.isEmpty() ? Either.left(-1) : Either.right(readers);
    }

    public Either<Integer, List<Reader>> getAll(Newspaper newspaper) {
        List<Reader> readers = new ArrayList<>();
        em = jpaUtil.getEntityManager();

        try {
            readers = em
                    .createNamedQuery("HQL_GET_READERS_BY_ID_NEWSPAPER", Reader.class)
                    .setParameter("id_newspaper", newspaper.getId())
                    .getResultList();

        } catch (PersistenceException e) {
            e.printStackTrace();
        } finally {
            if (em != null) em.close();
        }
        return readers.isEmpty() ? Either.left(-1) : Either.right(readers);
    }

    public Either<Integer, List<Reader>> getAll(ArticleType type) {
        List<Reader> readers = new ArrayList<>();
        em = jpaUtil.getEntityManager();

        try {
            readers = em
                    .createNamedQuery("HQL_GET_READERS_BY_ARTICLE_TYPE", Reader.class)
                    .setParameter("description", type.getDescription())
                    .getResultList();

        } catch (PersistenceException e) {
            e.printStackTrace();
        } finally {
            if (em != null) em.close();
        }

        return readers.isEmpty() ? Either.left(-1) : Either.right(readers);
    }

    public int save(Reader reader) {
        Login login = reader.getLogin();
        login.setReader(reader);

        em = jpaUtil.getEntityManager();
        EntityTransaction tx = null;

        try {
            tx = em.getTransaction();
            tx.begin();
            em.persist(login);
            tx.commit();
        } catch (PersistenceException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
            return -1;
        } finally {
            if (em != null) em.close();
        }
        return 1;
    }

    public int delete(Reader reader) {
        em = jpaUtil.getEntityManager();
        EntityTransaction tx = null;

        Reader managedReader = em.find(Reader.class, reader.getId());
        managedReader.setDateOfBirth(reader.getDateOfBirth());
        managedReader.getLogin().setPassword(reader.getLogin().getPassword());

        try {
            tx = em.getTransaction();
            tx.begin();
            String hql = "DELETE FROM Subscription WHERE id_reader = :id";
            Query query = em.createQuery(hql);
            query.setParameter("id", reader.getId());
            query.executeUpdate();
            em.remove(managedReader);
            tx.commit();
        } catch (PersistenceException e) {
            assert tx != null;
            if (tx.isActive()) tx.rollback();
            return -1;
        } finally {
            if (em != null) em.close();
        }
        return 1;
    }

    public Integer update(Reader reader) {
        em = jpaUtil.getEntityManager();
        EntityTransaction tx = null;

        Reader managedReader = em.find(Reader.class, reader.getId());
        managedReader.setDateOfBirth(reader.getDateOfBirth());
        managedReader.getLogin().setPassword(reader.getLogin().getPassword());

        try {
            tx = em.getTransaction();
            tx.begin();
            em.merge(managedReader);
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

    public Reader get(int id) {
        em = jpaUtil.getEntityManager();
        EntityTransaction tx = null;
        Reader reader = null;

        try {
            tx = em.getTransaction();
            tx.begin();
            reader = em.find(Reader.class, id);
            tx.commit();
        } catch (PersistenceException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            if (em != null) em.close();
        }
        return reader;
    }
}