package dao;

import dao.common.Constantes;
import dao.common.SQLQueries;
import domain.exceptions.DatabaseException;
import domain.exceptions.ObjectNotFoundException;
import jakarta.inject.Inject;
import modelo.Carpeta;
import modelo.Mensaje;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DaoCarpetas {

    private final DBConnection db;

    @Inject
    public DaoCarpetas(DBConnection db) {
        this.db = db;
    }

    public List<Carpeta> getCarpetasByUsuario(int id) {
        List<Carpeta> carpetas;
        try (Connection con = db.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(SQLQueries.SELECT_CARPETAS_BY_USUARIO)) {
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            carpetas = readRS(rs);
        } catch (SQLException ex) {
            Logger.getLogger(DaoUsuarios.class.getName()).log(Level.SEVERE, null, ex);
            throw new ObjectNotFoundException("No se han encontrado carpetas");
        }
        return carpetas;
    }

    private List<Carpeta> readRS(ResultSet rs) {
        List<Carpeta> carpetas = new ArrayList<>();
        try {
            while (rs.next()) {
                Carpeta carpeta = new Carpeta();
                carpeta.setId(rs.getInt(Constantes.ID));
                carpeta.setNombreCarpeta(rs.getString("nombreCarpeta"));
                carpeta.setPassword(rs.getString("password"));
                carpeta.setIDUsuario(rs.getInt("IDUsuario"));
                carpeta.setModoEdicion(rs.getBoolean("modoEdicion"));
                carpetas.add(carpeta);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DaoUsuarios.class.getName()).log(Level.SEVERE, null, ex);
        }
        return carpetas;
    }

    public Carpeta addCarpeta(Carpeta carpeta) {
        try (Connection con = db.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(SQLQueries.INSERT_CARPETA, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, carpeta.getNombreCarpeta());
            preparedStatement.setString(2, carpeta.getPassword());
            preparedStatement.setInt(3, carpeta.getIDUsuario());
            preparedStatement.setBoolean(4, carpeta.isModoEdicion());
            preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.getGeneratedKeys();
            if (rs.next()) {
                carpeta.setId(rs.getInt(1));
            }
            return carpeta;
        } catch (SQLException ex) {
            Logger.getLogger(DaoUsuarios.class.getName()).log(Level.SEVERE, null, ex);
            throw new DatabaseException("Error al insertar la carpeta");
        }
    }

    public Carpeta cargarCarpetaCompartida(String nombreCarpeta, String nombreUsuario, String passwordCarpeta) {
        Carpeta carpeta;
        try (Connection con = db.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(SQLQueries.SELECT_CARPETA_COMPARTIDA)) {
            preparedStatement.setString(1, nombreCarpeta);
            preparedStatement.setString(2, passwordCarpeta);
            preparedStatement.setString(3, nombreUsuario);
            ResultSet rs = preparedStatement.executeQuery();
            List<Carpeta> carpetas = readRS(rs);
            if (carpetas.size() == 1) {
                carpeta = carpetas.get(0);
            } else {
                throw new ObjectNotFoundException("No se ha encontrado la carpeta compartida");
            }
        } catch (SQLException ex) {
            Logger.getLogger(DaoUsuarios.class.getName()).log(Level.SEVERE, null, ex);
            throw new ObjectNotFoundException("No se ha encontrado la carpeta compartida");
        }
        return carpeta;
    }

    public Carpeta updateCarpeta(Carpeta carpeta) {
        Connection connection = null;
        PreparedStatement updateCarpeta = null;
        PreparedStatement updateMensajes = null;
        try {
            connection = db.getConnection();
            connection.setAutoCommit(false);
            updateCarpeta = connection.prepareStatement(SQLQueries.UPDATE_CARPETA);
            updateCarpeta.setString(1, carpeta.getPassword());
            updateCarpeta.setInt(2, carpeta.getId());
            updateCarpeta.executeUpdate();

            for (Mensaje mensaje : carpeta.getMensajes()) {
                updateMensajes = connection.prepareStatement(SQLQueries.UPDATE_MENSAJE);
                updateMensajes.setString(1, mensaje.getContenido());
                updateMensajes.setInt(2, mensaje.getId());
                updateMensajes.executeUpdate();
            }

            connection.commit();
        } catch (SQLException e) {
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException ex) {
                    throw new DatabaseException("Error al actualizar la carpeta y sus mensajes");
                }
            }
            throw new DatabaseException("Error al actualizar la carpeta y sus mensajes");
        } finally {
            try {
                if (updateCarpeta != null) {
                    updateCarpeta.close();
                }
                if (updateMensajes != null) {
                    updateMensajes.close();
                }
                if (connection != null) {
                    connection.setAutoCommit(true);
                    connection.close();
                }
            } catch (SQLException e) {
                throw new DatabaseException("Error al cerrar la conexión");
            }
        }
        return carpeta;
    }
}
