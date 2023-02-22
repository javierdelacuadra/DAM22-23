package dao.impl;


import common.NumericConstants;
import config.Configuration;
import dao.TypeDAO;
import dao.common.DAOConstants;
import dao.common.SQLQueries;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import model.Article;
import model.TypeArt;
import org.springframework.core.NestedRuntimeException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class TypeDAOImpl implements TypeDAO {

    private DBConnection dbConnection;

    @Inject
    public TypeDAOImpl (DBConnection dbConnection) {
        this.dbConnection = dbConnection;
    }


    @Override public Either<Integer, List<TypeArt>> getAll() {
        try {
            JdbcTemplate jtm = new JdbcTemplate(dbConnection.getHikariDataSource());
            return Either.right(jtm.query(SQLQueries.SELECT_ALL_TYPES, BeanPropertyRowMapper.newInstance(TypeArt.class)));
        } catch (NestedRuntimeException e) {
            return Either.left(NumericConstants.DB_EXCEPTION_CODE);
        } catch (Exception e) {
            return Either.left(NumericConstants.NON_RELATED_TO_DB_EXCEPTION_CODE);
        }
    }

    @Override
    public Either<String, TypeArt> get(TypeArt typeArt) {
        return null;
    }

    @Override
    public int add(TypeArt typeArt) {
        return 0;
    }

    @Override
    public int update(TypeArt typeArt) {
        return 0;
    }

    @Override
    public int delete(TypeArt typeArt) {
        return 0;
    }
}
