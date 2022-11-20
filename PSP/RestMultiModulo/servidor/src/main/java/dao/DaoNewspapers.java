package dao;

import dao.common.Constantes;
import dao.common.SQLQueries;
import domain.exceptions.DatabaseException;
import domain.exceptions.ObjectNotFoundException;
import jakarta.inject.Inject;
import modelo.Newspaper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DaoNewspapers {

    private final DBConnection db;

    @Inject
    public DaoNewspapers(DBConnection db) {
        this.db = db;
    }

    public List<Newspaper> getAll() {
        List<Newspaper> newspapers;
        try (Connection con = db.getConnection();
             Statement statement = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                     ResultSet.CONCUR_READ_ONLY)) {
            ResultSet rs = statement.executeQuery(SQLQueries.SELECT_NEWSPAPERS);
            newspapers = readRS(rs);
        } catch (SQLException ex) {
            Logger.getLogger(DaoReaders.class.getName()).log(Level.SEVERE, null, ex);
            throw new ObjectNotFoundException(Constantes.NO_SE_HAN_ENCONTRADO_NEWSPAPERS);
        }
        return newspapers;
    }

    private List<Newspaper> readRS(ResultSet rs) {
        List<Newspaper> newspapers = new ArrayList<>();
        try {
            while (rs.next()) {
                Newspaper newspaper = new Newspaper();
                newspaper.setId(rs.getInt(Constantes.ID));
                newspaper.setName(rs.getString(Constantes.NAME));
                newspaper.setRelease_date(String.valueOf(rs.getDate(Constantes.RELEASE_DATE)));
                newspapers.add(newspaper);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DaoReaders.class.getName()).log(Level.SEVERE, null, ex);
        }
        return newspapers;
    }

    public Newspaper save(Newspaper newspaper) {
        List<Newspaper> newspapers = getAll();
        if (newspapers.stream().noneMatch(n -> n.getName().equals(newspaper.getName()))) {
            try (Connection con = db.getConnection();
                 PreparedStatement preparedStatement = con.prepareStatement(SQLQueries.INSERT_NEWSPAPER, Statement.RETURN_GENERATED_KEYS)) {
                preparedStatement.setString(1, newspaper.getName());
                preparedStatement.setDate(2, Date.valueOf(newspaper.getRelease_date()));
                preparedStatement.executeUpdate();
                ResultSet rs = preparedStatement.getGeneratedKeys();
                if (rs.next()) {
                    newspaper.setId(rs.getInt(1));
                }
                return newspaper;
            } catch (SQLException ex) {
                Logger.getLogger(DaoReaders.class.getName()).log(Level.SEVERE, null, ex);
                throw new ObjectNotFoundException(Constantes.NO_SE_HA_PODIDO_GUARDAR_EL_NEWSPAPER);
            }
        }
        throw new DatabaseException(Constantes.YA_EXISTE_UN_NEWSPAPER_CON_ESE_NOMBRE);
    }

    public Newspaper update(Newspaper newspaper) {
        try (Connection con = db.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(SQLQueries.UPDATE_NEWSPAPER)) {
            preparedStatement.setString(1, newspaper.getName());
            preparedStatement.setDate(2, Date.valueOf(newspaper.getRelease_date()));
            preparedStatement.setInt(3, newspaper.getId());
            preparedStatement.executeUpdate();
            return newspaper;
        } catch (Exception ex) {
            Logger.getLogger(DaoReaders.class.getName()).log(Level.SEVERE, null, ex);
            throw new ObjectNotFoundException(Constantes.NO_SE_HA_PODIDO_ACTUALIZAR_EL_NEWSPAPER);
        }
    }

    public boolean delete(String id) {
        try (Connection con = db.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(SQLQueries.DELETE_NEWSPAPER)) {
            preparedStatement.setInt(1, Integer.parseInt(id));
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DaoReaders.class.getName()).log(Level.SEVERE, null, ex);
            throw new ObjectNotFoundException(Constantes.NO_SE_HA_PODIDO_ELIMINAR_EL_NEWSPAPER);
        }
        return true;
    }

    public Newspaper get(String id) {
        Newspaper newspaper;
        try (Connection con = db.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(SQLQueries.SELECT_NEWSPAPER_BY_ID)) {
            preparedStatement.setInt(1, Integer.parseInt(id));
            ResultSet rs = preparedStatement.executeQuery();
            newspaper = readRS(rs).get(0);
        } catch (SQLException | IndexOutOfBoundsException ex) {
            throw new ObjectNotFoundException(Constantes.NO_SE_HA_ENCONTRADO_EL_NEWSPAPER);
        }
        return newspaper;
    }

}