package data;

import common.Constantes;
import data.common.SQLQueries;
import data.hibernate.JPAUtil;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.PersistenceException;
import model.Reader;

import java.sql.*;
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
        List list = null;
        em = jpaUtil.getEntityManager();

        try {
            list = em
                    .createNamedQuery("HQL_GET_ALL_READERS", Reader.class)
                    .getResultList();

        } catch (PersistenceException e) {
            e.printStackTrace();
        } finally {
            if (em != null) em.close();
        }

        return list.isEmpty() ? Either.left(-1) : Either.right(list);
    }

    public Either<Integer, List<Reader>> getAll(int id) {
        List<Reader> readers = new ArrayList<>();
        try (Connection con = db.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(SQLQueries.SELECT_READERS_BY_NEWSPAPER)) {
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            readers = readRS(rs);
        } catch (SQLException e) {
            Logger.getLogger(DaoReaders.class.getName()).log(Level.SEVERE, null, e);
        }
        return readers.isEmpty() ? Either.left(-1) : Either.right(readers);
    }

    public Either<Integer, List<Reader>> getAll(String articleType) {
        List<Reader> readers = new ArrayList<>();
        try (Connection con = db.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(SQLQueries.SELECT_READERS_BY_ARTICLE_TYPE)) {
            preparedStatement.setString(1, articleType);
            ResultSet rs = preparedStatement.executeQuery();
            readers = readRS(rs);
        } catch (SQLException e) {
            Logger.getLogger(DaoReaders.class.getName()).log(Level.SEVERE, null, e);
        }
        return readers.isEmpty() ? Either.left(-1) : Either.right(readers);
    }

    public Either<Integer, List<Reader>> save(Reader reader, String password) {
        int rowsAffected;
        List<Reader> readers = getAll().get();
        if (readers.stream().noneMatch(r -> r.getName().equals(reader.getName()))) {
            try (Connection con = db.getConnection();
                 PreparedStatement preparedStatement = con.prepareStatement(SQLQueries.INSERT_READER, Statement.RETURN_GENERATED_KEYS)) {
                preparedStatement.setString(1, reader.getName());
                preparedStatement.setDate(2, Date.valueOf(reader.getDateOfBirth()));
                rowsAffected = preparedStatement.executeUpdate();
                ResultSet rs = preparedStatement.getGeneratedKeys();
                if (rs.next()) {
                    reader.setId(rs.getInt(1));
                }
                if (rowsAffected > 0) {
                    saveLogin(reader.getId(), reader.getName(), password);
                }
                readers = getAll().get();
            } catch (SQLException e) {
                return Either.left(-1);
            } catch (Exception e) {
                e.printStackTrace();
                return Either.left(-2);
            }
        } else {
            return Either.left(-3);
        }
        return Either.right(readers);
    }

    private void saveLogin(int id, String name, String password) {
        try (Connection con = db.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(SQLQueries.INSERT_LOGIN)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, password);
            preparedStatement.setInt(3, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            Logger.getLogger(DaoReaders.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    private void deleteLogin(String name) {
        try (Connection con = db.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(SQLQueries.DELETE_FROM_LOGIN)) {
            preparedStatement.setString(1, name);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            Logger.getLogger(DaoReaders.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    private void deleteSubscriptions(int id) {
        try (Connection con = db.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(SQLQueries.DELETE_FROM_SUBSCRIPTIONS)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            Logger.getLogger(DaoReaders.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    private void deleteReadArticles(int id) {
        try (Connection con = db.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(SQLQueries.DELETE_FROM_READARTICLES)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            Logger.getLogger(DaoReaders.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public Either<Integer, List<Reader>> delete(Reader reader) {
        List<Reader> readers;
        deleteSubscriptions(reader.getId());
        deleteReadArticles(reader.getId());
        deleteLogin(reader.getName());
        try (Connection con = db.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(SQLQueries.DELETE_READER)) {
            preparedStatement.setInt(1, reader.getId());
            preparedStatement.executeUpdate();
            readers = getAll().get();
        } catch (SQLException e) {
            return Either.left(-1);
        } catch (Exception e) {
            e.printStackTrace();
            return Either.left(-2);
        }
        return Either.right(readers);
    }

    public Integer update(Reader reader) {
        em = jpaUtil.getEntityManager();
        EntityTransaction tx = null;

        try {
            tx = em.getTransaction();
            tx.begin();
            em.merge(reader);
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

    //TODO: updateLogin, deleteReader, addReader

    private void updateLogin(Integer id, String name) {
        try (Connection con = db.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(SQLQueries.UPDATE_READER_LOGIN)) {
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            Logger.getLogger(DaoReaders.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    private List<Reader> readRS(ResultSet rs) {
        List<Reader> readers = new ArrayList<>();
        try {
            while (rs.next()) {
                Reader reader = new Reader();
                reader.setId(rs.getInt(Constantes.ID));
                reader.setName(rs.getString(Constantes.NAME));
                reader.setDateOfBirth(rs.getDate(Constantes.DATE_OF_BIRTH).toLocalDate());
                readers.add(reader);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DaoReaders.class.getName()).log(Level.SEVERE, null, ex);
        }
        return readers;
    }

    public Integer login(String name, String password) {
        Integer code = null;
        try (Connection con = db.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(SQLQueries.LOGIN)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, password);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                PreparedStatement preparedStatement1 = con.prepareStatement(SQLQueries.SELECT_READER_BY_NAME);
                preparedStatement1.setString(1, name);
                ResultSet rs1 = preparedStatement1.executeQuery();
                if (rs1.next()) {
                    code = rs1.getInt(Constantes.ID);
                }
            } else {
                code = -2;
            }
        } catch (Exception e) {
            Logger.getLogger(DaoReaders.class.getName()).log(Level.SEVERE, null, e);
            code = -3;
        }
        return code;
    }

    public Reader get(int id) {
        Reader reader = new Reader();
        try (Connection con = db.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(SQLQueries.SELECT_READER_BY_ID)) {
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                reader.setId(rs.getInt(Constantes.ID));
                reader.setName(rs.getString(Constantes.NAME));
                reader.setDateOfBirth(rs.getDate(Constantes.DATE_OF_BIRTH).toLocalDate());
            }
        } catch (SQLException e) {
            Logger.getLogger(DaoReaders.class.getName()).log(Level.SEVERE, null, e);
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