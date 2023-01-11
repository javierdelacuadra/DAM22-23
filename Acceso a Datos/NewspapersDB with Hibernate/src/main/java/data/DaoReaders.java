package data;

import common.Constantes;
import data.common.SQLQueries;
import data.hibernate.JPAUtil;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.PersistenceException;
import model.Login;
import model.Reader;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DaoReaders {
    private JPAUtil jpaUtil;
    private EntityManager em;
    private final DBConnection db;

    @Inject
    public DaoReaders(JPAUtil jpaUtil, DBConnection db) {
        this.jpaUtil = jpaUtil;
        this.em = jpaUtil.getEntityManager();
        this.db = db;
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

    public Either<Integer, List<Reader>> getAll(int id) {
        List<Reader> readers = new ArrayList<>();
        em = jpaUtil.getEntityManager();

        try {
            readers = em
                    .createNamedQuery("HQL_GET_READERS_BY_ID_NEWSPAPER", Reader.class)
                    .setParameter("id_newspaper", id)
                    .getResultList();

        } catch (PersistenceException e) {
            e.printStackTrace();
        } finally {
            if (em != null) em.close();
        }
        return readers.isEmpty() ? Either.left(-1) : Either.right(readers);
    }

    public Either<Integer, List<Reader>> getAll(String articleType) {
        List<Reader> readers = new ArrayList<>();
        em = jpaUtil.getEntityManager();

        try {
            readers = em
                    .createNamedQuery("HQL_GET_READERS_BY_ARTICLE_TYPE", Reader.class)
                    .setParameter("description", articleType)
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

        try {
            tx = em.getTransaction();
            tx.begin();
            em.remove(em.merge(reader));
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

    public Integer login(Login login) {
        em = jpaUtil.getEntityManager();
        Login user;

        try {
            user = em
                    .createNamedQuery("HQL_GET_LOGIN", Login.class)
                    .setParameter("name", login.getName())
                    .setParameter("password", login.getPassword())
                    .getSingleResult();
        } catch (PersistenceException e) {
            return -2;
        } finally {
            if (em != null) em.close();
        }
        return user.getReader().getId();

        //TODO: mover a daologin
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

    public Either<Integer, List<Reader>> getOldestSubscribers() {
        List<Reader> readers = new ArrayList<>();
        try (Connection con = db.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(SQLQueries.SELECT_OLDEST_SUBSCRIBERS)) {
            ResultSet rs = preparedStatement.executeQuery();
            readers = readRS(rs, 5);
        } catch (SQLException e) {
            Logger.getLogger(DaoReaders.class.getName()).log(Level.SEVERE, null, e);
        }
        return readers.isEmpty() ? Either.left(-1) : Either.right(readers);
    }

    private List<Reader> readRS(ResultSet rs, Integer limit) {
        List<Reader> readers = new ArrayList<>();
        int count = 0;
        try {
            while (rs.next() && count < limit) {
                Reader reader = new Reader();
                reader.setId(rs.getInt(Constantes.ID));
                reader.setName(rs.getString(Constantes.NAME));
                reader.setDateOfBirth(rs.getDate(Constantes.DATE_OF_BIRTH).toLocalDate());
                readers.add(reader);
                count++;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DaoReaders.class.getName()).log(Level.SEVERE, null, ex);
        }
        return readers;
    }
}