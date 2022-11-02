package data;

import data.common.SQLQueries;
import jakarta.inject.Inject;
import model.Newspaper;

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

    public Integer save(Newspaper newspaper, Integer id) {
        try (Connection con = db.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(SQLQueries.INSERT_SUBSCRIPTION)) {
            preparedStatement.setInt(1, newspaper.getId());
            preparedStatement.setInt(2, id);
            preparedStatement.setDate(3, Date.valueOf(LocalDate.now()));
            preparedStatement.setDate(4, null);
            preparedStatement.executeUpdate();
            return 1;
        } catch (SQLException e) {
            return -1;
        }
    }

    public Integer remove(Newspaper newspaper, Integer id) {
        try (Connection con = db.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(SQLQueries.DELETE_SUBSCRIPTION)) {
            preparedStatement.setInt(1, newspaper.getId());
            preparedStatement.setInt(2, id);
            preparedStatement.executeUpdate();
            return 1;
        } catch (SQLException e) {
            return -1;
        }
    }
}