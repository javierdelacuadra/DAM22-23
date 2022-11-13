package data;

import data.common.SQLQueries;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import model.Newspaper;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DaoNewspaperSQL {
    private final DBConnection db;

    @Inject
    public DaoNewspaperSQL(DBConnection db) {
        this.db = db;
    }

    public Either<Integer, List<Newspaper>> getAll() {
        List<Newspaper> newspapers = new ArrayList<>();
        try {
            String query = SQLQueries.SELECT_NEWSPAPERS;
            JdbcTemplate jdbc = new JdbcTemplate(db.getHikariDataSource());
            newspapers = jdbc.query(query, BeanPropertyRowMapper.newInstance(Newspaper.class));
        } catch (DataAccessException e) {
            Logger.getLogger(DaoNewspaperSQL.class.getName()).log(Level.SEVERE, null, e);
        }
        return newspapers.isEmpty() ? Either.left(-1) : Either.right(newspapers);
    }

    public Either<Integer, List<Newspaper>> addNewspaper(Newspaper newspaper) {
        List<Newspaper> newspapers = new ArrayList<>();
        try {
            String query = SQLQueries.INSERT_NEWSPAPER;
            JdbcTemplate jdbc = new JdbcTemplate(db.getHikariDataSource());
            jdbc.update(query, newspaper.getName(), newspaper.getRelease_date());
            newspapers = getAll().get();
        } catch (DataAccessException e) {
            Logger.getLogger(DaoNewspaperSQL.class.getName()).log(Level.SEVERE, null, e);
        }
        return newspapers.isEmpty() ? Either.left(-1) : Either.right(newspapers);
    }

    public Either<Integer, List<Newspaper>> deleteNewspaper(Integer id) {
        List<Newspaper> newspapers = new ArrayList<>();
        try {
            String query = SQLQueries.DELETE_NEWSPAPER;
            JdbcTemplate jdbc = new JdbcTemplate(db.getHikariDataSource());
            jdbc.update(query, id);
            newspapers = getAll().get();
        } catch (DataAccessException e) {
            Logger.getLogger(DaoNewspaperSQL.class.getName()).log(Level.SEVERE, null, e);
        }
        return newspapers.isEmpty() ? Either.left(-1) : Either.right(newspapers);
    }

    public Either<Integer, List<Newspaper>> updateNewspaper(Newspaper newspaper) {
        List<Newspaper> newspapers = new ArrayList<>();
        try {
            String query = SQLQueries.UPDATE_NEWSPAPER;
            JdbcTemplate jdbc = new JdbcTemplate(db.getHikariDataSource());
            jdbc.update(query, newspaper.getName(), newspaper.getRelease_date(), newspaper.getId());
            newspapers = getAll().get();
        } catch (DataAccessException e) {
            Logger.getLogger(DaoNewspaperSQL.class.getName()).log(Level.SEVERE, null, e);
        }
        return newspapers.isEmpty() ? Either.left(-1) : Either.right(newspapers);
    }
}