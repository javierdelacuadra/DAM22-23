package dao.impl;

import common.NumericConstants;
import dao.ReadArticlesDAO;
import dao.common.DBConstants;
import dao.common.SQLQueries;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import lombok.extern.log4j.Log4j2;
import model.QueryBadRatedArticles;
import model.ReadArticle;
import model.TypeArt;
import org.springframework.core.NestedRuntimeException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.*;
import java.util.List;
import java.util.Objects;

@Log4j2
public class ReadArticlesDAOImpl implements ReadArticlesDAO {

    DBConnection dbConnection;

    @Inject
    public ReadArticlesDAOImpl(DBConnection dbConnection) {
        this.dbConnection = dbConnection;
    }


    @Override
    public Either<Integer, List<ReadArticle>> getAll() {
        return null;
    }

    @Override
    public Either<Integer, ReadArticle> get(int id) {
        return null;
    }

    @Override
    public int update(ReadArticle readArticle) {
        try {
            JdbcTemplate jtm = new JdbcTemplate(dbConnection.getHikariDataSource());
            return jtm.update(SQLQueries.UPDATE_READARTICLES_SET_RATING_WHERE_ID_READER_AND_ID_ARTICLE, readArticle.getRating(), readArticle.getIdReader(), readArticle.getIdArticle());

        } catch (NestedRuntimeException e) {
            log.error(e.getMessage());
            return NumericConstants.DB_EXCEPTION_CODE;
        } catch (
                Exception e) {
            log.error(e.getMessage());
            return NumericConstants.NON_RELATED_TO_DB_EXCEPTION_CODE;
        }

    }

    @Override
    public int add(ReadArticle ra) {
        int code = 0;
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQLQueries.ADD_READ_ARTICLE, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setInt(1, ra.getIdReader());
            preparedStatement.setInt(2, ra.getIdArticle());
            preparedStatement.setInt(3, ra.getRating());
            preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.getGeneratedKeys();

            if (rs.next()) {
                ra.setId(rs.getInt(1));
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
            if (e.getErrorCode() == NumericConstants.SQL_INTEGRITY_CONSTRAINT_VIOLATION_EXCEPTION) {
                code = e.getErrorCode();
            } else {
                code = NumericConstants.DB_EXCEPTION_CODE;
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            code = NumericConstants.NON_RELATED_TO_DB_EXCEPTION_CODE;
        }
        return code;
    }

    @Override
    public int delete(int id) {
        int code = -1;
        try (Connection con = dbConnection.getConnection();
             PreparedStatement prpStatement = con.prepareStatement(SQLQueries.DELETE_FROM_LOGIN_WHERE_ID_READER)) {
            prpStatement.setInt(1, id);
            code = prpStatement.executeUpdate();
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
        return code;
    }

    @Override
    public Either<Integer, List<QueryBadRatedArticles>> getAllDifficultSpringQuery(int idReader, int idNewspaper) {
        try {
            JdbcTemplate jtm = new JdbcTemplate(dbConnection.getHikariDataSource());
            return Either.right(jtm.query(SQLQueries.IMPOSSIBLE_QUERY,
                    new RowMapper<QueryBadRatedArticles>() {
                        @Override
                        public QueryBadRatedArticles mapRow(ResultSet rs, int rowNum) throws SQLException {
                            int badRatings = rs.getInt(DBConstants.BAD_RATINGS);
                            boolean badRater = badRatings > 4;
                            return new QueryBadRatedArticles(rs.getString(DBConstants.NAME_ARTICLE), badRater);
                        }
                    }, idNewspaper
            ));

        } catch (NestedRuntimeException e) {
            return Either.left(NumericConstants.DB_EXCEPTION_CODE);
        } catch (Exception e) {
            return Either.left(NumericConstants.NON_RELATED_TO_DB_EXCEPTION_CODE);
        }
    }

    @Override
    public Either<Integer, Integer> get(int idArticle, int idReader) {
        try {
            JdbcTemplate jtm = new JdbcTemplate(dbConnection.getHikariDataSource());
            Integer currentRating = jtm.queryForObject(SQLQueries.SELECT_RATING_FROM_READARTICLES, Integer.class, idReader, idArticle);
            return Either.right(currentRating);
        } catch (Exception e) {
            // We don't care about the exception, we just want to return 1 if the article has not been read yet
            // CURRENT RATING IS EMPTY, SO WE RETURN 1
            return Either.left(1);
        }
    }
}
