package data;

import common.Constantes;
import data.common.SQLQueries;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import model.Newspaper;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DaoNewspaperSQL {
    private final DBConnection db;

    @Inject
    public DaoNewspaperSQL(DBConnection db) {
        this.db = db;
    }

    public Either<Integer, List<Newspaper>> getAll() {
        List<Newspaper> newspapers;
        String query = SQLQueries.SELECT_NEWSPAPERS;
        JdbcTemplate jdbc = new JdbcTemplate(db.getHikariDataSource());
        newspapers = jdbc.query(query, BeanPropertyRowMapper.newInstance(Newspaper.class));
        return newspapers.isEmpty() ? Either.left(-1) : Either.right(newspapers);
    }

    public Either<Integer, List<Newspaper>> addNewspaper(Newspaper newspaper) {
        List<Newspaper> newspapers;
        String query = SQLQueries.INSERT_NEWSPAPER;
        JdbcTemplate jdbc = new JdbcTemplate(db.getHikariDataSource());
        SimpleJdbcInsert insert = new SimpleJdbcInsert(jdbc);
        insert.withTableName(Constantes.TABLE_NEWSPAPERS);
        insert.usingGeneratedKeyColumns(Constantes.ID);
        insert.usingColumns(Constantes.NAME, Constantes.RELEASE_DATE);
        Map<String, Object> parameters = new HashMap<>();
        parameters.put(Constantes.NAME, newspaper.getName());
        parameters.put(Constantes.RELEASE_DATE, newspaper.getRelease_date());
        int id = insert.executeAndReturnKey(parameters).intValue();
        newspapers = jdbc.query(query, BeanPropertyRowMapper.newInstance(Newspaper.class));
        return newspapers.isEmpty() ? Either.left(id) : Either.right(newspapers);
    }

    public Either<Integer, List<Newspaper>> deleteNewspaper(int id) {
        List<Newspaper> newspapers;
        String query = SQLQueries.DELETE_NEWSPAPER;
        JdbcTemplate jdbc = new JdbcTemplate(db.getHikariDataSource());
        jdbc.update(query, id);
        newspapers = jdbc.query(query, BeanPropertyRowMapper.newInstance(Newspaper.class));
        return newspapers.isEmpty() ? Either.left(id) : Either.right(newspapers);
    }

    public Either<Integer, List<Newspaper>> updateNewspaper(Newspaper newspaper) {
        List<Newspaper> newspapers;
        String query = SQLQueries.UPDATE_NEWSPAPER;
        JdbcTemplate jdbc = new JdbcTemplate(db.getHikariDataSource());
        jdbc.update(query, newspaper.getName(), newspaper.getRelease_date(), newspaper.getId());
        newspapers = jdbc.query(query, BeanPropertyRowMapper.newInstance(Newspaper.class));
        return newspapers.isEmpty() ? Either.left(newspaper.getId()) : Either.right(newspapers);
    }
}