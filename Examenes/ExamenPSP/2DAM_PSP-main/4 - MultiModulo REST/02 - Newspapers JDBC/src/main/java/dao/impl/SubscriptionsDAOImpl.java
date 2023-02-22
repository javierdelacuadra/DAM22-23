package dao.impl;

import common.NumericConstants;
import dao.SubscriptionsDAO;
import dao.common.DAOConstants;
import dao.common.DBConstants;
import dao.common.SQLQueries;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import lombok.extern.log4j.Log4j2;
import model.Suscription;
import org.springframework.core.NestedRuntimeException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Log4j2
public class SubscriptionsDAOImpl implements SubscriptionsDAO {

    private DBConnection dbConnection;

    @Inject
    public SubscriptionsDAOImpl(DBConnection dbConnection) {
        this.dbConnection = dbConnection;
    }


    @Override
    public Either<Integer, List<Suscription>> getAll(int id) {
        return null;
    }

    @Override
    public Either<Integer, Suscription> get(int id) {
        return null;
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

    @Override
    public int update(Suscription suscription) {
        try {
            JdbcTemplate jtm = new JdbcTemplate(dbConnection.getHikariDataSource());
            if (suscription.getCancellation_date() == null) {
                // IT IS A PERSON WHO HAD CANCELLED THE SUBSCRIPTION AND NOW HE WANTS TO SUSCRIBE AGAIN
                return jtm.update(SQLQueries.UPDATE_SUBSCRIPTIONS_SET_CANCELLATION_DATE_NULL_SIGNING_DATE_WHERE_ID_READER_AND_ID_NEWSPAPER, suscription.getSigning_date(), suscription.getId_reader(), suscription.getId_newspaper());
            } else {
                // IT IS A PERSON WHO IS CANCELLING A CURRENT SUSCRIPTION
                return jtm.update(SQLQueries.UPDATE_SUBSCRIPTIONS_SET_CANCELLATION_DATE_WHERE_ID_READER_AND_ID_NEWSPAPER, suscription.getCancellation_date(), suscription.getId_reader(), suscription.getId_newspaper());
            }
        } catch (NestedRuntimeException e) {
            log.error(e.getMessage());
            return NumericConstants.DB_EXCEPTION_CODE;
        } catch (Exception e) {
            log.error(e.getMessage());
            return NumericConstants.NON_RELATED_TO_DB_EXCEPTION_CODE;
        }
    }

    @Override
    public int add(Suscription suscription) {
        int code;
        try {
            SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(dbConnection.getHikariDataSource()).withTableName(DBConstants.SUBSCRIPTIONS);

            Map<String, Object> parameters = new HashMap<>();

            parameters.put(DBConstants.ID_NEWSPAPER, suscription.getId_newspaper());
            parameters.put(DBConstants.ID_READER, suscription.getId_reader());
            parameters.put(DBConstants.SIGNING_DATE, suscription.getSigning_date());
            // cancellation_date is null by default when adding
            code = jdbcInsert.execute(parameters);
        } catch (NestedRuntimeException e) {
            String message = e.getMessage();
            if (message != null && message.contains(DAOConstants.SQL_INTEGRITY_CONSTRAINT_VIOLATION_EXCEPTION)) {
                code = NumericConstants.SQL_INTEGRITY_CONSTRAINT_VIOLATION_EXCEPTION;
            } else {
                code = NumericConstants.DB_EXCEPTION_CODE;
            }
            log.error(message);
        } catch (Exception e) {
            log.error(e.getMessage());
            code = NumericConstants.NON_RELATED_TO_DB_EXCEPTION_CODE;
        }
        return code;
    }

}

