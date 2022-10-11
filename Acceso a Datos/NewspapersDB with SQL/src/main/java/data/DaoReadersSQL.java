package data;

import data.common.SQLQueries;
import jakarta.inject.Inject;
import modelo.Reader;

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

    public int insertReader(Reader reader) {
        int rowsAffected = 0;
        try (Connection con = db.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(SQLQueries.INSERT_READER)) {
            preparedStatement.setInt(1, reader.getId());
            preparedStatement.setString(2, reader.getName());
            preparedStatement.setDate(3, Date.valueOf(reader.getDateOfBirth()));
            rowsAffected = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            Logger.getLogger(DaoReadersSQL.class.getName()).log(Level.SEVERE, null, e);
        }
        return rowsAffected;
    }

    public int deleteReader(int id) {
        int rowsAffected = 0;
        try (Connection con = db.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(SQLQueries.DELETE_READER)) {
            preparedStatement.setInt(1, id);
            rowsAffected = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            Logger.getLogger(DaoReadersSQL.class.getName()).log(Level.SEVERE, null, e);
        }
        return rowsAffected;
    }

    public int updateReader(Reader reader) {
        int rowsAffected = 0;
        try (Connection con = db.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(SQLQueries.UPDATE_READER)) {
            preparedStatement.setString(1, reader.getName());
            preparedStatement.setDate(2, Date.valueOf(reader.getDateOfBirth()));
            preparedStatement.setInt(3, reader.getId());
            rowsAffected = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            Logger.getLogger(DaoReadersSQL.class.getName()).log(Level.SEVERE, null, e);
        }
        return rowsAffected;
    }

    public List<Reader> getAllReaders() {
        List<Reader> readers = new ArrayList<>();
        try (Connection con = db.getConnection();
             Statement statement = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                     ResultSet.CONCUR_READ_ONLY)) {

            ResultSet rs = statement.executeQuery(SQLQueries.SELECT_READERS);
            readers = readRS(rs);

        } catch (SQLException ex) {
            Logger.getLogger(DaoReadersSQL.class.getName()).log(Level.SEVERE, null, ex);
        }
        return readers;
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
            Logger.getLogger(DaoReadersSQL.class.getName()).log(Level.SEVERE, null, ex);
        }
        return readers;
    }
}