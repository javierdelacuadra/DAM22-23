package dao;

import dao.common.Constantes;
import dao.common.SQLQueries;
import domain.exceptions.DatabaseException;
import jakarta.inject.Inject;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

public class DaoSubscriptions {

    private final DBConnection db;

    @Inject
    public DaoSubscriptions(DBConnection db) {
        this.db = db;
    }

    public boolean save(String newspaperId, String readerId) {
        try (Connection con = db.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(SQLQueries.INSERT_SUBSCRIPTION)) {
            preparedStatement.setInt(1, Integer.parseInt(newspaperId));
            preparedStatement.setInt(2, Integer.parseInt(readerId));
            preparedStatement.setDate(3, Date.valueOf(LocalDate.now()));
            preparedStatement.setDate(4, null);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException(Constantes.NO_SE_HA_PODIDO_GUARDAR_LA_SUSCRIPCION);
        }
        return true;
    }

    public boolean remove(String newspaperId, String readerId) {
        try (Connection con = db.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(SQLQueries.DELETE_SUBSCRIPTION)) {
            preparedStatement.setInt(1, Integer.parseInt(newspaperId));
            preparedStatement.setInt(2, Integer.parseInt(readerId));
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException(Constantes.NO_SE_HA_PODIDO_ELIMINAR_LA_SUSCRIPCION);
        }
        return true;
    }
}
