package dao;

import dao.common.Constantes;
import dao.common.SQLQueries;
import domain.exceptions.DatabaseException;
import jakarta.inject.Inject;

import java.sql.*;
import java.time.LocalDateTime;

public class DaoCalls {

    private final DBConnection db;

    @Inject
    public DaoCalls(DBConnection db) {
        this.db = db;
    }

    public int getCallsByName(String name) {
        int calls;
        try (Connection con = db.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(SQLQueries.SELECT_CALLS_BY_NAME)) {
            preparedStatement.setString(1, name);
            ResultSet rs = preparedStatement.executeQuery();
            rs.next();
            calls = rs.getInt(Constantes.CALLS);
        } catch (SQLException e) {
            throw new DatabaseException(Constantes.ERROR_AL_REALIZAR_LA_CONSULTA);
        }
        return calls;
    }

    public void registerCall(String name) {
        try (Connection con = db.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(SQLQueries.INSERT_CALL)) {
            preparedStatement.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now()));
            preparedStatement.setString(2, name);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException(Constantes.ERROR_AL_REALIZAR_LA_CONSULTA);
        }
    }
}
