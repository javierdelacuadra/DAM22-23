package dao;

import dao.common.Constantes;
import dao.common.SQLQueries;
import domain.exceptions.DatabaseException;
import domain.exceptions.ObjectNotFoundException;
import jakarta.inject.Inject;
import modelo.Carpeta;

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
}
