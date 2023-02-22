package dao.impl;


import dao.TypeDAO;
import dao.common.SQLQueries;
import domain.modelo.TypeArt;
import domain.modelo.errores.BaseDeDatosException;
import domain.modelo.errores.OtherErrorException;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import org.springframework.core.NestedRuntimeException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class TypeDAOImpl implements TypeDAO {

    private final DBConnection dbConnection;

    @Inject
    public TypeDAOImpl(DBConnection dbConnection) {
        this.dbConnection = dbConnection;
    }


    @Override
    public List<TypeArt> getAll() {
        try {
            JdbcTemplate jtm = new JdbcTemplate(dbConnection.getHikariDataSource());
            return jtm.query(SQLQueries.SELECT_ALL_TYPES, BeanPropertyRowMapper.newInstance(TypeArt.class));
        } catch (NestedRuntimeException e) {
            throw new BaseDeDatosException(e.getMessage());
        } catch (Exception e) {
            throw new OtherErrorException(e.getMessage());
        }
    }

    // POR EL DAO PATTERN, LUCIA PIDIO QUE ESTEN TODAS LAS FUNCIONES AUNQUE NO HAYAN SIDO IMPLEMENTADAS
    @Override
    public Either<String, TypeArt> get(TypeArt typeArt) {
        return null;
    }

    // POR EL DAO PATTERN, LUCIA PIDIO QUE ESTEN TODAS LAS FUNCIONES AUNQUE NO HAYAN SIDO IMPLEMENTADAS
    @Override
    public int add(TypeArt typeArt) {
        return 0;
    }

    // POR EL DAO PATTERN, LUCIA PIDIO QUE ESTEN TODAS LAS FUNCIONES AUNQUE NO HAYAN SIDO IMPLEMENTADAS
    @Override
    public int update(TypeArt typeArt) {
        return 0;
    }

    // POR EL DAO PATTERN, LUCIA PIDIO QUE ESTEN TODAS LAS FUNCIONES AUNQUE NO HAYAN SIDO IMPLEMENTADAS
    @Override
    public int delete(TypeArt typeArt) {
        return 0;
    }
}
