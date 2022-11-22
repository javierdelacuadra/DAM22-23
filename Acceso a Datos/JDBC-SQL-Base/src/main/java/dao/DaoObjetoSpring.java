package dao;

import dao.common.SQLQueries;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import model.Objeto;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DaoObjetoSpring {
    private final DBConnection db;

    @Inject
    public DaoObjetoSpring(DBConnection db) {
        this.db = db;
    }

    public Either<Integer, List<Objeto>> getAll() {
        List<Objeto> objetos = new ArrayList<>();
        try {
            String query = SQLQueries.SELECT_OBJETOS;
            JdbcTemplate jdbc = new JdbcTemplate(db.getHikariDataSource());
            objetos = jdbc.query(query, BeanPropertyRowMapper.newInstance(Objeto.class));
        } catch (DataAccessException e) {
            Logger.getLogger(DaoObjetoSpring.class.getName()).log(Level.SEVERE, null, e);
        }
        return objetos.isEmpty() ? Either.left(-1) : Either.right(objetos);
    }

    public int save(Objeto objeto) {
        int id = -1;
        try {
            String query = SQLQueries.INSERT_OBJETO;
            JdbcTemplate jdbc = new JdbcTemplate(db.getHikariDataSource());
            jdbc.update(query, objeto.getNombre(), objeto.getApellido(), objeto.getFecha());
            id = getAll().get().get(getAll().get().size() - 1).getId();
        } catch (DataAccessException e) {
            Logger.getLogger(DaoObjetoSpring.class.getName()).log(Level.SEVERE, null, e);
        }
        return id;
    }

    public int remove(Integer id) {
        int result = -1;
        try {
            String query = SQLQueries.DELETE_OBJETO;
            JdbcTemplate jdbc = new JdbcTemplate(db.getHikariDataSource());
            jdbc.update(query, id);
            result = 1;
        } catch (DataAccessException e) {
            Logger.getLogger(DaoObjetoSpring.class.getName()).log(Level.SEVERE, null, e);
        }
        return result;
    }

    public int update(Objeto objeto) {
        int result = -1;
        try {
            String query = SQLQueries.UPDATE_OBJETO;
            JdbcTemplate jdbc = new JdbcTemplate(db.getHikariDataSource());
            jdbc.update(query, objeto.getNombre(), objeto.getApellido(), objeto.getFecha(), objeto.getId());
            result = 1;
        } catch (DataAccessException e) {
            Logger.getLogger(DaoObjetoSpring.class.getName()).log(Level.SEVERE, null, e);
        }
        return result;
    }

    public Either<Integer, Objeto> get(Integer id) {
        Objeto objeto = null;
        try {
            String query = SQLQueries.SELECT_OBJETO;
            JdbcTemplate jdbc = new JdbcTemplate(db.getHikariDataSource());
            objeto = jdbc.queryForObject(query, BeanPropertyRowMapper.newInstance(Objeto.class), id);
        } catch (DataAccessException e) {
            Logger.getLogger(DaoObjetoSpring.class.getName()).log(Level.SEVERE, null, e);
        }
        return objeto == null ? Either.left(-1) : Either.right(objeto);
    }

}