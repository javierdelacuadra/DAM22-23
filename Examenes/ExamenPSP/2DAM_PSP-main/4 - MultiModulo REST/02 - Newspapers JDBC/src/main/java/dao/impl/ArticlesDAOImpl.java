package dao.impl;

import common.NumericConstants;
import config.Configuration;
import dao.ArticlesDAO;
import dao.common.DAOConstants;
import dao.common.DBConstants;
import dao.common.SQLQueries;
import model.*;
import org.springframework.core.NestedRuntimeException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import io.vavr.control.Either;
import jakarta.inject.Inject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;


public class ArticlesDAOImpl implements ArticlesDAO {

    private DBConnection dbConnection;

    @Inject
    public ArticlesDAOImpl(DBConnection dbConnection) {
        this.dbConnection = dbConnection;
    }


    @Override
    public Either<Integer, List<Article>> getAll(Newspaper newspaper) {
        Either<Integer, List<Article>> either;
        try (Connection con = dbConnection.getConnection();
             PreparedStatement prpstatement = con.prepareStatement(SQLQueries.SELECT_ARTICLES_BY_NEWS)) {
            prpstatement.setInt(1, newspaper.getId());
            ResultSet rs = prpstatement.executeQuery();
            List<Article> articles = readAllArticles(rs);
            either = Either.right(articles);
        } catch (SQLException e) {
            either = Either.left(NumericConstants.DB_EXCEPTION_CODE);
        } catch (Exception e) {
            either = Either.left(NumericConstants.NON_RELATED_TO_DB_EXCEPTION_CODE);
        }
        return either;
    }

    private static List<Article> readAllArticles(ResultSet rs) throws SQLException {
        List<Article> articles = new ArrayList<>();
        while (rs.next()) {
            int id = rs.getInt(DBConstants.ID);
            String name = rs.getString(DBConstants.NAME_ARTICLE);
            int idType = rs.getInt(DBConstants.ID_TYPE);
            int idNewspaper = rs.getInt(DBConstants.ID_NEWSPAPER);
            String description = rs.getString(DBConstants.DESCRIPTION);
            articles.add(new Article(id, name, description, idType, idNewspaper));
        }
        return articles;
    }


    @Override
    public Either<Integer, List<Article>> getAll() {
        try {
            JdbcTemplate jtm = new JdbcTemplate(dbConnection.getHikariDataSource());
            return Either.right(jtm.query(SQLQueries.SELECT_ALL_ARTICLES, BeanPropertyRowMapper.newInstance(Article.class)));
        } catch (NestedRuntimeException e) {
            return Either.left(NumericConstants.DB_EXCEPTION_CODE);
        } catch (Exception e) {
            return Either.left(NumericConstants.NON_RELATED_TO_DB_EXCEPTION_CODE);
        }
    }

    @Override
    public Either<Integer, List<Article>> getAll(int idType) {
        try {
            JdbcTemplate jtm = new JdbcTemplate(dbConnection.getHikariDataSource());
            return Either.right(jtm.query(SQLQueries.SELECT_ARTICLES_BY_TYPE, BeanPropertyRowMapper.newInstance(Article.class), idType));
        } catch (NestedRuntimeException e) {
            return Either.left(NumericConstants.DB_EXCEPTION_CODE);
        } catch (Exception e) {
            return Either.left(NumericConstants.NON_RELATED_TO_DB_EXCEPTION_CODE);
        }
    }




    @Override
    public Either<String, Article> get(int id) {
        return null;
    }

    @Override
    public int add(Article article) {
        int code = 1;
        try {
            SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(dbConnection.getHikariDataSource()).withTableName(DBConstants.ARTICLES)
                    .usingGeneratedKeyColumns(DBConstants.ID);

            Map<String, Object> parameters = new HashMap<>();
            parameters.put(DBConstants.NAME_ARTICLE, article.getName_article());
            parameters.put(DBConstants.ID_NEWSPAPER, article.getId_newspaper());
            parameters.put(DBConstants.ID_TYPE, article.getId_type());
            parameters.put(DBConstants.DESCRIPTION, article.getDescription());
            article.setId(jdbcInsert.executeAndReturnKey(parameters).intValue());
        } catch (NestedRuntimeException e ){
            String message = e.getCause().toString();
            if (message != null && message.contains(DAOConstants.SQL_INTEGRITY_CONSTRAINT_VIOLATION_EXCEPTION)) {
                if (message.contains(DAOConstants.CANNOT_ADD_OR_UPDATE_A_CHILD_ROW)) {
                    code = NumericConstants.UPDATED_RECORD_EXCEPTION;
                } else {
                    code = NumericConstants.SQL_INTEGRITY_CONSTRAINT_VIOLATION_EXCEPTION;
                }
            } else {
                code = NumericConstants.DB_EXCEPTION_CODE;
            }
        }
        catch (Exception e) {
            code = NumericConstants.NON_RELATED_TO_DB_EXCEPTION_CODE;
        }
        return code;
    }

    public Either<Integer, QueryDescAndReaders> getQuery(int id) {
        Either<Integer, QueryDescAndReaders> either;
        try (Connection con = dbConnection.getConnection();
             PreparedStatement prpStatement = con.prepareStatement(SQLQueries.SELECT_READERS_AND_DESCRIPTION_BY_ARTICLE)) {
            prpStatement.setInt(1, id);
            ResultSet rs = prpStatement.executeQuery();
            either = readRS(rs);
        } catch (SQLException e) {
            either = Either.left(NumericConstants.DB_EXCEPTION_CODE);
        } catch (Exception e) {
            either = Either.left(NumericConstants.NON_RELATED_TO_DB_EXCEPTION_CODE);
        }
        return either;
    }

    private Either<Integer, QueryDescAndReaders> readRS(ResultSet rs) {
        try {
            QueryDescAndReaders queryDescAndReaders = null;
            while (rs.next()) {
                String description = rs.getString(DBConstants.DESCRIPTION) != null ? rs.getString(DBConstants.DESCRIPTION) : "";
                int cantReaders = rs.getInt(DBConstants.CANT_READERS);
                queryDescAndReaders = new QueryDescAndReaders(description, cantReaders);
            }
            return Either.right(queryDescAndReaders);
        } catch (SQLException e) {
            return Either.left(NumericConstants.DB_EXCEPTION_CODE);
        } catch (Exception e) {
            return Either.left(NumericConstants.NON_RELATED_TO_DB_EXCEPTION_CODE);
        }
    }

    @Override
    public int update(Article article) {
        return 0;
    }

    @Override
    public int delete(List<Article> articlesList) {
     return -1;
    }

    @Override
    public Either<Integer, List<QueryArticlesAndNews>> getAllQuerySpring(int idType) {
        try {
            JdbcTemplate jtm = new JdbcTemplate(dbConnection.getHikariDataSource());
            return Either.right(jtm.query(SQLQueries.SELECT_ARTNAME_AND_NEWSNAME, BeanPropertyRowMapper.newInstance(QueryArticlesAndNews.class), idType));
        } catch (NestedRuntimeException e) {
            return Either.left(NumericConstants.DB_EXCEPTION_CODE);
        } catch (Exception e) {
            return Either.left(NumericConstants.NON_RELATED_TO_DB_EXCEPTION_CODE);
        }
    }
}
