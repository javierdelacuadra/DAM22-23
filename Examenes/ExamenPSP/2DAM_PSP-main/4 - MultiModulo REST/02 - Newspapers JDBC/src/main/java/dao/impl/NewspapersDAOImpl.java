package dao.impl;

import common.NumericConstants;
import config.Configuration;
import dao.NewspapersDAO;
import dao.common.DAOConstants;
import dao.common.DBConstants;
import dao.common.SQLQueries;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import lombok.extern.log4j.Log4j2;
import model.Newspaper;
import model.Reader;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.core.*;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

@Log4j2
public class NewspapersDAOImpl implements NewspapersDAO {

    private final DBConnection dbConnection;

    @Inject
    public NewspapersDAOImpl(DBConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    @Override
    public Either<Integer, List<Newspaper>> getAll() {
        try {
            JdbcTemplate jtm = new JdbcTemplate(dbConnection.getHikariDataSource());
            return Either.right(jtm.query(SQLQueries.SELECT_ALL_NEWSPAPERS, BeanPropertyRowMapper.newInstance(Newspaper.class)));
        } catch (NestedRuntimeException e) {
            log.error(e.getMessage());
            return Either.left(NumericConstants.DB_EXCEPTION_CODE);
        } catch (Exception e) {
            log.error(e.getMessage());
            return Either.left(NumericConstants.NON_RELATED_TO_DB_EXCEPTION_CODE);
        }
    }

    @Override
    public Either<Integer, List<Newspaper>> getAll(Reader currentReader) {
        try (Connection con = dbConnection.getConnection();
             PreparedStatement prpStatement = con.prepareStatement(SQLQueries.SELECT_NEWSPAPERS_SUSCRIBED_READER)) {
            prpStatement.setInt(1, currentReader.getId());
            ResultSet rs = prpStatement.executeQuery();
            return readRS(rs);

        } catch (SQLException e) {
            log.error(e.getMessage());
            return Either.left(NumericConstants.DB_EXCEPTION_CODE);
        } catch (Exception e) {
            log.error(e.getMessage());
            return Either.left(NumericConstants.NON_RELATED_TO_DB_EXCEPTION_CODE);
        }
    }

    @Override
    public Either<Integer, List<Newspaper>> getAll(int idReader, boolean suscribed) {
        try {
            JdbcTemplate jtm = new JdbcTemplate(dbConnection.getHikariDataSource());
            String query;
            if (suscribed) {
                query = SQLQueries.SELECT_NEWSPAPERS_SUSCRIBED_READER;
            } else {
                query = SQLQueries.SELECT_ALL_NEWSPAPERS_UNSUSCRIBED;
            }
            return Either.right(jtm.query(query, BeanPropertyRowMapper.newInstance(Newspaper.class), idReader));
        } catch (NestedRuntimeException e) {
            log.error(e.getMessage());
            return Either.left(NumericConstants.DB_EXCEPTION_CODE);
        } catch (Exception e) {
            log.error(e.getMessage());
            return Either.left(NumericConstants.NON_RELATED_TO_DB_EXCEPTION_CODE);
        }
    }

    private Either<Integer, List<Newspaper>> readRS(ResultSet rs) {
        try {
            List<Newspaper> news = new ArrayList<>();
            while (rs.next()) {
                int id = rs.getInt(DBConstants.ID);
                String nameNews = rs.getString(DBConstants.NAME_NEWSPAPER);
                LocalDate releaseDate = rs.getDate(DBConstants.RELEASE_DATE).toLocalDate();

                news.add(new Newspaper(id, nameNews, releaseDate));
            }
            return Either.right(news);
        } catch (SQLException e) {
            return Either.left(NumericConstants.DB_EXCEPTION_CODE);
        } catch (Exception e) {
            return Either.left(NumericConstants.NON_RELATED_TO_DB_EXCEPTION_CODE);
        }
    }

    @Override
    public Either<String, Newspaper> get(Newspaper news) {
        try {
            // SEGUIR POR ACA - CORREGIR ESTO
            return Either.right(getAll().get()
                    .stream()
                    .filter(n -> n.equals(news))
                    .findFirst().
                    orElse(new Newspaper(-1, "", LocalDate.now())));
        } catch (Exception e) {
            return Either.left(DAOConstants.ERROR_GETTING_NEWSPAPERS + e.getMessage());
        }

    }

    @Override
    public int update(Newspaper news) {
        try {
            JdbcTemplate jtm = new JdbcTemplate(dbConnection.getHikariDataSource());
            return jtm.update(SQLQueries.UPDATE_NEWSPAPER_SET, news.getName_newspaper(), news.getRelease_date(), news.getId());
        } catch (NestedRuntimeException e) {
            String message = e.getMessage();
            if (message != null && message.contains(DAOConstants.SQL_INTEGRITY_CONSTRAINT_VIOLATION_EXCEPTION)) {
                return NumericConstants.SQL_INTEGRITY_CONSTRAINT_VIOLATION_EXCEPTION;
            }
            log.error(message);
            return NumericConstants.DB_EXCEPTION_CODE;
        } catch (Exception e) {
            log.error(e.getMessage());
            return NumericConstants.NON_RELATED_TO_DB_EXCEPTION_CODE;
        }
    }

    public int delete(int id, boolean confirmed) {
        if (!confirmed){
            return deleteQuery(id);
        }
        return deleteTransaction(id);
    }

    private int deleteQuery(int id) {
        try {
            JdbcTemplate jtm = new JdbcTemplate(dbConnection.getHikariDataSource());
            return jtm.update(SQLQueries.DELETE_FROM_NEWSPAPERS_BY_ID, id);
        } catch (DataIntegrityViolationException e) {
            return NumericConstants.DATA_INTEGRITY_VIOLATION_EXCEPTION;
        } catch (NestedRuntimeException e) {
            return NumericConstants.DB_EXCEPTION_CODE;
        } catch (Exception e) {
            return NumericConstants.NON_RELATED_TO_DB_EXCEPTION_CODE;
        }
    }

    private int deleteTransaction(int id) {
        int code;
        TransactionDefinition txDef = new DefaultTransactionDefinition();
        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager(dbConnection.getHikariDataSource());
        TransactionStatus txStatus = transactionManager.getTransaction(txDef);
        try {
            try {
                // articles, readArt, subs, news
                JdbcTemplate jtm = new JdbcTemplate(Objects.requireNonNull(transactionManager.getDataSource()));
                jtm.update(SQLQueries.DELETE_READARTS_ARTS_IN_NEWS, id);
                jtm.update(SQLQueries.DELETE_ARTICLES_WHERE_ID_NEWS, id);
                jtm.update(SQLQueries.DELETE_SUBSCRIPTIONS_WHERE_ID_NEWS, id);
                code = jtm.update(SQLQueries.DELETE_FROM_NEWSPAPERS_BY_ID, id);
                // Commit when all movements have been done
                transactionManager.commit(txStatus);
            } catch (NestedRuntimeException e) {
                transactionManager.rollback(txStatus);
                code = NumericConstants.DB_EXCEPTION_CODE;
            } catch (Exception e) {
                transactionManager.rollback(txStatus);
                code = NumericConstants.NON_RELATED_TO_DB_EXCEPTION_CODE;
            }
        } catch (NestedRuntimeException e) {
            code = NumericConstants.DB_EXCEPTION_CODE;
        } catch (Exception e) {
            code = NumericConstants.NON_RELATED_TO_DB_EXCEPTION_CODE;
        }
        return code;
    }

    @Override
    public int add(Newspaper newspaper) {
        int code = 1;
        try {
            SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(dbConnection.getHikariDataSource()).withTableName(DBConstants.NEWSPAPERS)
                    .usingGeneratedKeyColumns(DBConstants.ID);

            Map<String, Object> parameters = new HashMap<>();

            parameters.put(DBConstants.NAME_NEWSPAPER, newspaper.getName_newspaper());
            parameters.put(DBConstants.RELEASE_DATE, newspaper.getRelease_date());
            newspaper.setId(jdbcInsert.executeAndReturnKey(parameters).intValue());
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
