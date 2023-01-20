package dao;

import dao.common.SQLQueries;
import domain.exceptions.ObjectAlreadyExistsException;
import jakarta.inject.Inject;
import model.Carpeta;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DaoCarpetas {

    private final DBConnection db;

    @Inject
    public DaoCarpetas(DBConnection db) {
        this.db = db;
    }

    public void createFolder(Carpeta carpeta) {
        try (Connection connection = db.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQLQueries.INSERT_FOLDER)) {
            preparedStatement.setString(1, carpeta.getNombre());
            preparedStatement.setString(2, carpeta.getPassword());
            preparedStatement.setBoolean(3, carpeta.isModoEdicion());
            preparedStatement.setString(4, carpeta.getNombreUsuario());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new ObjectAlreadyExistsException("Ya existe una carpeta con ese nombre");
        }
    }

}