package dao;

import dao.common.Constantes;
import dao.common.SQLQueries;
import domain.exceptions.DatabaseException;
import domain.exceptions.ObjectNotFoundException;
import jakarta.inject.Inject;
import modelo.Reader;

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

    public List<Reader> getAll() {
        List<Reader> readers;
        try (Connection con = db.getConnection();
             Statement statement = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                     ResultSet.CONCUR_READ_ONLY)) {
            ResultSet rs = statement.executeQuery(SQLQueries.SELECT_READERS);
            readers = readRS(rs);
        } catch (SQLException ex) {
            Logger.getLogger(DaoReaders.class.getName()).log(Level.SEVERE, null, ex);
            throw new ObjectNotFoundException(Constantes.NO_SE_HAN_ENCONTRADO_READERS);
        }
        return readers;
    }

    public Reader save(Reader reader) {
        List<Reader> readers = getAll();
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
                return reader;
            } catch (SQLException ex) {
                Logger.getLogger(DaoReaders.class.getName()).log(Level.SEVERE, null, ex);
                throw new ObjectNotFoundException(Constantes.NO_SE_HA_PODIDO_GUARDAR_EL_READER);
            }
        }
        throw new DatabaseException(Constantes.YA_EXISTE_UN_READER_CON_ESE_NOMBRE);
    }

    public Reader update(Reader reader) {
        List<Reader> readers = getAll();
        if (readers.stream().noneMatch(r -> r.getName().equals(reader.getName()) && r.getId() != reader.getId())) {
            try (Connection con = db.getConnection();
                 PreparedStatement preparedStatement = con.prepareStatement(SQLQueries.UPDATE_READER)) {
                preparedStatement.setString(1, reader.getName());
                preparedStatement.setDate(2, Date.valueOf(reader.getDateOfBirth()));
                preparedStatement.setInt(3, reader.getId());
                preparedStatement.executeUpdate();
                return reader;
            } catch (SQLException e) {
                Logger.getLogger(DaoReaders.class.getName()).log(Level.SEVERE, null, e);
                throw new ObjectNotFoundException(Constantes.NO_SE_HA_PODIDO_ACTUALIZAR_EL_READER);
            }
        }
        throw new DatabaseException(Constantes.YA_EXISTE_UN_READER_CON_ESE_NOMBRE);
    }

    public boolean delete(String id) {
        try (Connection con = db.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(SQLQueries.DELETE_READER)) {
            preparedStatement.setInt(1, Integer.parseInt(id));
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            Logger.getLogger(DaoReaders.class.getName()).log(Level.SEVERE, null, e);
            throw new DatabaseException(Constantes.NO_SE_HA_PODIDO_ELIMINAR_EL_READER);
        }
    }

    public Reader get(String id) {
        Reader reader;
        try (Connection con = db.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(SQLQueries.SELECT_READER_BY_ID)) {
            preparedStatement.setInt(1, Integer.parseInt(id));
            ResultSet rs = preparedStatement.executeQuery();
            reader = readRS(rs).get(0);
        } catch (SQLException | IndexOutOfBoundsException e) {
            throw new ObjectNotFoundException(Constantes.NO_SE_HAN_ENCONTRADO_READERS);
        }
        return reader;
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

    public List<Reader> getReadersByArticleType(String articleType) {
        List<Reader> readers;
        try (Connection con = db.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(SQLQueries.SELECT_READERS_BY_ARTICLE_TYPE)) {
            preparedStatement.setString(1, articleType);
            ResultSet rs = preparedStatement.executeQuery();
            readers = readRS(rs);
        } catch (SQLException e) {
            throw new DatabaseException(Constantes.ERROR_AL_REALIZAR_LA_CONSULTA);
        }
        return readers;
    }

    public List<Reader> getReadersByNewspaper(String idNewspaper) {
        List<Reader> readers;
        try (Connection con = db.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(SQLQueries.SELECT_READERS_BY_NEWSPAPER)) {
            preparedStatement.setInt(1, Integer.parseInt(idNewspaper));
            ResultSet rs = preparedStatement.executeQuery();
            readers = readRS(rs);
        } catch (SQLException e) {
            throw new DatabaseException(Constantes.ERROR_AL_REALIZAR_LA_CONSULTA);
        }
        return readers;
    }
}