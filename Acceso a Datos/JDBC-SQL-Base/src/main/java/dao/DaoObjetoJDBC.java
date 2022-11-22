package dao;

import dao.common.SQLQueries;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import model.Objeto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DaoObjetoJDBC {
    private final DBConnection db;

    @Inject
    public DaoObjetoJDBC(DBConnection db) {
        this.db = db;
    }

    public Either<Integer, List<Objeto>> getAll() {
        List<Objeto> objetos = new ArrayList<>();
        try (Connection con = db.getConnection();
             Statement statement = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                     ResultSet.CONCUR_READ_ONLY)) {
            ResultSet rs = statement.executeQuery(SQLQueries.SELECT_OBJETOS);
            objetos = readRS(rs);
        } catch (SQLException ex) {
            Logger.getLogger(DaoObjetoJDBC.class.getName()).log(Level.SEVERE, null, ex);
        }
        return objetos.isEmpty() ? Either.left(-1) : Either.right(objetos);
    }

    private List<Objeto> readRS(ResultSet rs) {
        List<Objeto> objetos = new ArrayList<>();
        try {
            while (rs.next()) {
                objetos.add(new Objeto(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getDate("fecha").toLocalDate()));
            }
        } catch (SQLException ex) {
            Logger.getLogger(DaoObjetoJDBC.class.getName()).log(Level.SEVERE, null, ex);
        }
        return objetos;
    }

    public int save(Objeto objeto) {
        int result = -1;
        try (Connection con = db.getConnection();
             PreparedStatement ps = con.prepareStatement(SQLQueries.INSERT_OBJETO)) {
            ps.setString(1, objeto.getNombre());
            ps.setString(2, objeto.getApellido());
            ps.setDate(3, Date.valueOf(objeto.getFecha()));
            result = ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DaoObjetoJDBC.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    public int update(Objeto objeto) {
        int result = -1;
        try (Connection con = db.getConnection();
             PreparedStatement ps = con.prepareStatement(SQLQueries.UPDATE_OBJETO)) {
            ps.setString(1, objeto.getNombre());
            ps.setString(2, objeto.getApellido());
            ps.setDate(3, Date.valueOf(objeto.getFecha()));
            ps.setInt(4, objeto.getId());
            result = ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DaoObjetoJDBC.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    public int remove(Integer id) {
        int result = -1;
        try (Connection con = db.getConnection();
             PreparedStatement ps = con.prepareStatement(SQLQueries.DELETE_OBJETO)) {
            ps.setInt(1, id);
            result = ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DaoObjetoJDBC.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    public Objeto get(int id) {
        Objeto objeto = null;
        try (Connection con = db.getConnection();
             Statement statement = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                     ResultSet.CONCUR_READ_ONLY)) {
            ResultSet rs = statement.executeQuery(SQLQueries.SELECT_OBJETO + id);
            objeto = readRS(rs).get(0);
        } catch (SQLException ex) {
            Logger.getLogger(DaoObjetoJDBC.class.getName()).log(Level.SEVERE, null, ex);
        }
        return objeto;
    }
}