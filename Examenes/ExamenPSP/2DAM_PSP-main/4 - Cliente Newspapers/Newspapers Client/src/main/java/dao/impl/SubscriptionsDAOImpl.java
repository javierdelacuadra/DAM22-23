package dao.impl;

import dao.SubscriptionsDAO;
import dao.common.DBConstants;
import dao.common.SQLQueries;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import lombok.extern.log4j.Log4j2;
import model.Newspaper;
import model.Reader;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Log4j2
public class SubscriptionsDAOImpl implements SubscriptionsDAO {

    private DBConnection dbConnection;

    @Inject
    public SubscriptionsDAOImpl(DBConnection dbConnection) {
        this.dbConnection = dbConnection;
    }


    @Override
    public int delete(int id) {
        int code = -1;
        try (Connection con = dbConnection.getConnection();
             PreparedStatement prpStatement = con.prepareStatement(SQLQueries.DELETE_FROM_SUBSCRIPTIONS_WHERE_ID_READER)) {
            prpStatement.setInt(1, id);
            code = prpStatement.executeUpdate();
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
        return code;
    }




}

