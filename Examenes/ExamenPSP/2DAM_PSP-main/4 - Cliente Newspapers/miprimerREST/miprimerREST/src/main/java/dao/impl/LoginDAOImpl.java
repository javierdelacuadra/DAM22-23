package dao.impl;

import dao.LoginDAO;
import dao.common.DAOConstants;
import dao.common.DBConstants;
import dao.common.SQLQueries;
import domain.modelo.errores.BaseDeDatosException;
import domain.modelo.errores.NotFoundException;
import domain.modelo.errores.OtherErrorException;
import jakarta.inject.Inject;
import domain.modelo.Login;
import domain.modelo.Reader;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class LoginDAOImpl implements LoginDAO {

    private final DBConnection dbConnection;


    @Inject
    public LoginDAOImpl(DBConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    @Override
    public Reader login(String user, String password) {
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

                return new Reader(id, name, birthDate, login);
            } else {
                throw new NotFoundException(DAOConstants.INVALID_USER_OR_PASSWORD);
            }
        } catch (NotFoundException e) {
            throw new NotFoundException(e.getMessage());
        }
        catch (SQLException e) {
            throw new BaseDeDatosException(e.getMessage());
        } catch (Exception e) {
            throw new OtherErrorException(e.getMessage());
        }
    }
}
