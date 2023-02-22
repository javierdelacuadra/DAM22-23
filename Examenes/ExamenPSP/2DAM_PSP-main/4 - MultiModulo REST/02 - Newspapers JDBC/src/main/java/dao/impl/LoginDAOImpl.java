package dao.impl;

import common.NumericConstants;
import dao.LoginDAO;
import dao.common.DBConstants;
import dao.common.SQLQueries;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import model.Login;
import model.Reader;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class LoginDAOImpl implements LoginDAO {


    private DBConnection dbConnection;


    @Inject
    public LoginDAOImpl(DBConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    @Override
    public Either<Integer, Reader> login(String user, String password) {
        Either<Integer, Reader> either;
        try (Connection con = dbConnection.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(SQLQueries.LOGIN_READER)) {
            preparedStatement.setString(1, user);
            preparedStatement.setString(2, password);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                int id = rs.getInt(DBConstants.ID);
                String name = rs.getString(DBConstants.NAME_READER);
                LocalDate birthDate = rs.getDate(DBConstants.DATE_OF_BIRTH).toLocalDate();
                Login login = new Login(rs.getString(DBConstants.USER), rs.getString(DBConstants.PASSWORD), id);

                either = Either.right(new Reader(id, name, birthDate, login));
            } else {
                either = Either.left(-1);
            }
        } catch (SQLException e) {
            either = Either.left(NumericConstants.DB_EXCEPTION_CODE);
        } catch (Exception e) {
            either = Either.left(NumericConstants.NON_RELATED_TO_DB_EXCEPTION_CODE);
        }
        return either;
    }

    @Override
    public void closePool() {
        dbConnection.closePool();
    }
}
