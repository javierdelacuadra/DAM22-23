package dao;

import dao.common.SQLQueries;
import dao.modelo.Reader;
import domain.exceptions.UnknownParamException;
import io.vavr.control.Either;
import jakarta.inject.Inject;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DaoReaders {

    private final DBConnection db;

    @Inject
    public DaoReaders(DBConnection db) {
        this.db = db;
    }

    public Either<Integer, List<Reader>> getAll() {
        List<Reader> readers;
        try (Connection con = db.getConnection();
             Statement statement = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                     ResultSet.CONCUR_READ_ONLY)) {
            ResultSet rs = statement.executeQuery(SQLQueries.SELECT_READERS);
            readers = readRS(rs);
        } catch (SQLException ex) {
            Logger.getLogger(DaoReaders.class.getName()).log(Level.SEVERE, null, ex);
            throw new UnknownParamException("a");
        }
        return readers.isEmpty() ? Either.left(-1) : Either.right(readers);
    }

    public Either<Integer, Reader> save(Reader reader) {
        List<Reader> readers = getAll().get();
        if (readers.stream().noneMatch(r -> r.getName().equals(reader.getName()))) {
            try (Connection con = db.getConnection();
                 PreparedStatement preparedStatement = con.prepareStatement(SQLQueries.INSERT_READER, Statement.RETURN_GENERATED_KEYS)) {
                preparedStatement.setString(1, reader.getName());
                preparedStatement.setDate(2, Date.valueOf(reader.getDateOfBirth()));
                preparedStatement.executeUpdate();
                ResultSet rs = preparedStatement.getGeneratedKeys();
                if (rs.next()) {
                    reader.setId(rs.getInt(1));
                }
            } catch (SQLException ex) {
                Logger.getLogger(DaoReaders.class.getName()).log(Level.SEVERE, null, ex);
                throw new UnknownParamException("a");
            } catch (Exception e) {
                e.printStackTrace();
                return Either.left(-1);
            }
        } else {
            return Either.left(-1);
        }
        return Either.right(reader);
    }

    public Either<Integer, List<Reader>> update(Reader reader) {
        List<Reader> readers = new ArrayList<>();
        if (getAll().get().stream().noneMatch(r -> r.getName().equals(reader.getName()) && r.getId() != reader.getId())) {
            try (Connection con = db.getConnection();
                 PreparedStatement preparedStatement = con.prepareStatement(SQLQueries.UPDATE_READER)) {
                preparedStatement.setString(1, reader.getName());
                preparedStatement.setDate(2, Date.valueOf(reader.getDateOfBirth()));
                preparedStatement.setInt(3, reader.getId());
                preparedStatement.executeUpdate();
                readers = getAll().get();
            } catch (SQLException e) {
                Logger.getLogger(DaoReaders.class.getName()).log(Level.SEVERE, null, e);
            }
        } else {
            return Either.left(-2);
        }
        return readers.isEmpty() ? Either.left(-1) : Either.right(readers);
    }

    public boolean delete(String id) {
        try (Connection con = db.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(SQLQueries.DELETE_READER)) {
            preparedStatement.setInt(1, Integer.parseInt(id));
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Either<Integer, Reader> get(String id) {
        Reader reader = new Reader();
        try (Connection con = db.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(SQLQueries.SELECT_READER_BY_ID)) {
            preparedStatement.setInt(1, Integer.parseInt(id));
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                reader.setId(rs.getInt("id"));
                reader.setName(rs.getString("name"));
                reader.setDateOfBirth(rs.getDate("dateOfBirth").toLocalDate());
            }
        } catch (SQLException e) {
            Logger.getLogger(DaoReaders.class.getName()).log(Level.SEVERE, null, e);
        }
        return reader.getId() == 0 ? Either.left(-1) : Either.right(reader);
    }

    private List<Reader> readRS(ResultSet rs) {
        List<Reader> readers = new ArrayList<>();
        try {
            while (rs.next()) {
                Reader reader = new Reader();
                reader.setId(rs.getInt("id"));
                reader.setName(rs.getString("name"));
                reader.setDateOfBirth(rs.getDate("dateOfBirth").toLocalDate());
                readers.add(reader);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DaoReaders.class.getName()).log(Level.SEVERE, null, ex);
        }
        return readers;
    }
}