package data;

import common.Constantes;
import data.common.SQLQueries;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import model.Reader;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DaoReadersSQL {
    private final DBConnection db;

    @Inject
    public DaoReadersSQL(DBConnection db) {
        this.db = db;
    }

    public Either<Integer, List<Reader>> getAll() {
        List<Reader> readers = new ArrayList<>();
        try (Connection con = db.getConnection();
             Statement statement = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                     ResultSet.CONCUR_READ_ONLY)) {

            ResultSet rs = statement.executeQuery(SQLQueries.SELECT_READERS);
            readers = readRS(rs);

        } catch (SQLException ex) {
            Logger.getLogger(DaoReadersSQL.class.getName()).log(Level.SEVERE, null, ex);
        }
        return readers.isEmpty() ? Either.left(-1) : Either.right(readers);
    }

    public Either<Integer, List<Reader>> save(Reader reader) {
        List<Reader> readers = new ArrayList<>();
        try (Connection con = db.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(SQLQueries.INSERT_READER)) {
            preparedStatement.setString(2, reader.getName());
            preparedStatement.setDate(3, Date.valueOf(reader.getDateOfBirth()));
            preparedStatement.executeUpdate();
            readers = getAll().get();
        } catch (SQLException e) {
            Logger.getLogger(DaoReadersSQL.class.getName()).log(Level.SEVERE, null, e);
        }
        return readers.isEmpty() ? Either.left(-1) : Either.right(readers);
    }

    public Either<Integer, List<Reader>> delete(int id) {
        List<Reader> readers = new ArrayList<>();
        try (Connection con = db.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(SQLQueries.DELETE_READER)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            readers = getAll().get();
        } catch (SQLException e) {
            Logger.getLogger(DaoReadersSQL.class.getName()).log(Level.SEVERE, null, e);
        }
        return readers.isEmpty() ? Either.left(-1) : Either.right(readers);
    }

    public Either<Integer, List<Reader>> update(Reader reader) {
        List<Reader> readers = new ArrayList<>();
        try (Connection con = db.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(SQLQueries.UPDATE_READER)) {
            preparedStatement.setString(1, reader.getName());
            preparedStatement.setDate(2, Date.valueOf(reader.getDateOfBirth()));
            preparedStatement.setInt(3, reader.getId());
            preparedStatement.executeUpdate();
            readers = getAll().get();
        } catch (SQLException e) {
            Logger.getLogger(DaoReadersSQL.class.getName()).log(Level.SEVERE, null, e);
        }
        return readers.isEmpty() ? Either.left(-1) : Either.right(readers);
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
            Logger.getLogger(DaoReadersSQL.class.getName()).log(Level.SEVERE, null, ex);
        }
        return readers;
    }
}