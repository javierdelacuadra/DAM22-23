package dao;

import dao.common.Constantes;
import dao.common.SQLQueries;
import domain.exceptions.ObjectNotFoundException;
import jakarta.inject.Inject;
import modelo.Mensaje;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DaoMensajes {

    private final DBConnection db;

    @Inject
    public DaoMensajes(DBConnection db) {
        this.db = db;
    }

    public List<Mensaje> getMensajesByUsuario(int id) {
        List<Mensaje> mensajes;
        try (Connection con = db.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(SQLQueries.SELECT_MENSAJES_BY_CARPETA)) {
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            mensajes = readRS(rs);
        } catch (SQLException ex) {
            Logger.getLogger(DaoReaders.class.getName()).log(Level.SEVERE, null, ex);
            throw new ObjectNotFoundException("No se han encontrado mensajes en la carpeta");
        }
        return mensajes;
    }

    private List<Mensaje> readRS(ResultSet rs) {
        List<Mensaje> mensajes = new ArrayList<>();
        try {
            while (rs.next()) {
                Mensaje mensaje = new Mensaje();
                mensaje.setId(rs.getInt(Constantes.ID));
                mensaje.setIDCarpeta(rs.getInt("IDCarpeta"));
                mensaje.setContenido(rs.getString("contenido"));
                mensajes.add(mensaje);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DaoReaders.class.getName()).log(Level.SEVERE, null, ex);
        }
        return mensajes;
    }

    public Mensaje addMensaje(Mensaje mensaje) {
        try (Connection con = db.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(SQLQueries.INSERT_MENSAJE, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, mensaje.getContenido());
            preparedStatement.setInt(2, mensaje.getIDCarpeta());
            preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.getGeneratedKeys();
            if (rs.next()) {
                mensaje.setId(rs.getInt(1));
            }
        } catch (SQLException ex) {
            Logger.getLogger(DaoReaders.class.getName()).log(Level.SEVERE, null, ex);
        }
        return mensaje;
    }

    public Mensaje updateMensaje(Mensaje mensaje) {
        try (Connection con = db.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(SQLQueries.UPDATE_MENSAJE)) {
            preparedStatement.setString(1, mensaje.getContenido());
            preparedStatement.setInt(2, mensaje.getIDCarpeta());
            preparedStatement.setInt(3, mensaje.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DaoReaders.class.getName()).log(Level.SEVERE, null, ex);
        }
        return mensaje;
    }

    public void deleteMensaje(int id) {
        try (Connection con = db.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(SQLQueries.DELETE_MENSAJE)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DaoReaders.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
