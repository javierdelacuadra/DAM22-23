package dao.impl;

import dao.ReadArticlesDAO;
import dao.common.SQLQueries;
import jakarta.inject.Inject;
import lombok.extern.log4j.Log4j2;
import model.ReadArticle;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Log4j2
public class ReadArticlesDAOImpl implements ReadArticlesDAO {

    DBConnection dbConnection;

    @Inject
    public ReadArticlesDAOImpl(DBConnection dbConnection) {
        this.dbConnection = dbConnection;
    }


    @Override
    public int add(ReadArticle ra){
        int code = 0;
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQLQueries.ADD_READ_ARTICLE)) {
            preparedStatement.setInt(1, ra.getIdReader());
            preparedStatement.setInt(2, ra.getIdArticle());
            preparedStatement.setInt(3, ra.getRating());
            preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.getGeneratedKeys();

            if (rs.next()) {
                ra.setId(rs.getInt(1));
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
            if (e.getErrorCode() == 1062){
                code = e.getErrorCode();
            } else {
                code = -3;
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            code = -2;
        }
        return code;
    }

    @Override
    public int delete(int id) {
        int code = -1;
        try (Connection con = dbConnection.getConnection();
             PreparedStatement prpStatement = con.prepareStatement(SQLQueries.DELETE_FROM_LOGIN_WHERE_ID_READER)) {
            prpStatement.setInt(1, id);
            code = prpStatement.executeUpdate();
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
        return code;
    }
}
