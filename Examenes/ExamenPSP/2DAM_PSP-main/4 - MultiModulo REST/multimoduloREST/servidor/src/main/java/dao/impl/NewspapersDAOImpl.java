package dao.impl;

import dao.NewspapersDAO;
import dao.common.DAOConstants;
import dao.common.DBConstants;
import dao.common.SQLQueries;
import domain.modelo.errores.*;
import jakarta.inject.Inject;
import org.miutils.domain.modelo.Newspaper;
import org.springframework.core.NestedRuntimeException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class NewspapersDAOImpl implements NewspapersDAO {

    private final DBConnection dbConnection;

    @Inject
    public NewspapersDAOImpl(DBConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    @Override
    public List<Newspaper> getAll() {
        try {
            JdbcTemplate jtm = new JdbcTemplate(dbConnection.getHikariDataSource());
            return jtm.query(SQLQueries.SELECT_ALL_NEWSPAPERS, BeanPropertyRowMapper.newInstance(Newspaper.class));
        } catch (NestedRuntimeException e) {
            throw new BaseDeDatosException(DAOConstants.ERROR_GETTING_NEWSPAPERS);
        } catch (Exception e) {
            throw new OtherErrorException(DAOConstants.ERROR_GETTING_NEWSPAPERS + DAOConstants.NON_RELATED_WITH_THE_DB);
        }
    }

    @Override
    public Newspaper get(String idNews) {
        try {
            JdbcTemplate jtm = new JdbcTemplate(dbConnection.getHikariDataSource());
            List<Newspaper> newspapers = jtm.query(SQLQueries.SELECT_ONE_NEWSPAPER, BeanPropertyRowMapper.newInstance(Newspaper.class), idNews);
            if (!newspapers.isEmpty()) {
                return newspapers.get(0);
            }
            throw new NotFoundException(DAOConstants.NO_SE_HA_ENCONTRADO_EL_PERIODICO_CON_ID + idNews);
        } catch (NestedRuntimeException e) {
            throw new BaseDeDatosException(DAOConstants.ERROR_GETTING_NEWSPAPERS);
        } catch (NotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new OtherErrorException(DAOConstants.ERROR_GETTING_NEWSPAPERS + DAOConstants.NON_RELATED_WITH_THE_DB);
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
                throw new InvalidFieldsException(DAOConstants.ERROR_UPDATING_NEWSPAPER + DAOConstants.YA_HAY_UN_NEWSPAPER_CON_ESE_NOMBRE_Y_FECHA_DE_PUBLICACION);
            } else
                throw new BaseDeDatosException(DAOConstants.ERROR_UPDATING_NEWSPAPER);
        } catch (Exception e) {
            throw new OtherErrorException(DAOConstants.ERROR_UPDATING_NEWSPAPER + DAOConstants.NON_RELATED_WITH_THE_DB);
        }
    }

    @Override
    public int delete(String id) {
        int code;
        code = deleteQuery(id);
        return code;

    }

    private int deleteQuery(String id) {
        try {
            JdbcTemplate jtm = new JdbcTemplate(dbConnection.getHikariDataSource());
            return jtm.update(SQLQueries.DELETE_FROM_NEWSPAPERS_BY_ID, id);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityException(e.getMessage());
        } catch (NestedRuntimeException e) {
            throw new BaseDeDatosException(DAOConstants.ERROR_DELETING_NEWSPAPER);
        } catch (Exception e) {
            throw new OtherErrorException(DAOConstants.ERROR_DELETING_NEWSPAPER + DAOConstants.NON_RELATED_WITH_THE_DB);
        }
    }


    private int deleteTransaction(String id) {
        int code;
        TransactionDefinition txDef = new DefaultTransactionDefinition();
        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager(dbConnection.getHikariDataSource());
        TransactionStatus txStatus = transactionManager.getTransaction(txDef);
        try {
            code = executeDeleteNewsQueries(id, transactionManager, txStatus);
            if (code == 0) {
                throw new NotFoundException(DAOConstants.NO_SE_HA_ENCONTRADO_EL_PERIODICO_CON_ID + id);
            }
        } catch (NestedRuntimeException e) {
            throw new BaseDeDatosException(DAOConstants.ERROR_DELETING_NEWSPAPER);
        } catch (InvalidFieldsException | BaseDeDatosException | NotFoundException | OtherErrorException e) {
            throw e;
        } catch (Exception e) {
            throw new OtherErrorException(e.getMessage());
        }
        return code;
    }

    private int executeDeleteNewsQueries(String id, DataSourceTransactionManager transactionManager, TransactionStatus txStatus) {
        int code;
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
            String msg = e.getMessage();
            if (msg != null && msg.contains(DAOConstants.TRUNCATED_INCORRECT_DOUBLE_VALUE)) {
                throw new InvalidFieldsException(DAOConstants.ERROR_DELETING_NEWSPAPER + DAOConstants.EL_ID_DEL_NEWSPAPER_ES_INCORRECTO);
            } else {
                throw new BaseDeDatosException(DAOConstants.ERROR_DELETING_NEWSPAPER + DAOConstants.ROLLBACK_DONE);
            }
        } catch (Exception e) {
            transactionManager.rollback(txStatus);
            throw new OtherErrorException(DAOConstants.ERROR_DELETING_NEWSPAPER + DAOConstants.ROLLBACK_DONE + DAOConstants.NON_RELATED_WITH_THE_DB);
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
            return code;
        } catch (NestedRuntimeException e) {
            String message = e.getMessage();
            if (message != null && message.contains(DAOConstants.SQL_INTEGRITY_CONSTRAINT_VIOLATION_EXCEPTION)) {
                throw new InvalidFieldsException(DAOConstants.ERROR_UPDATING_NEWSPAPER + DAOConstants.YA_HAY_UN_NEWSPAPER_CON_ESE_NOMBRE_Y_FECHA_DE_PUBLICACION);
            } else {
                throw new BaseDeDatosException(DAOConstants.ERROR_ADDING_NEWSPAPER);
            }
        } catch (Exception e) {
            throw new OtherErrorException(DAOConstants.ERROR_ADDING_NEWSPAPER + DAOConstants.NON_RELATED_WITH_THE_DB);
        }
    }

}
