package dao.impl;


import dao.TypeDAO;
import dao.common.SQLQueries;
import domain.modelo.errores.BaseDeDatosException;
import domain.modelo.errores.OtherErrorException;
import jakarta.inject.Inject;
import org.miutils.domain.modelo.TypeArt;
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
}
