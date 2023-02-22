package dao.impl;

import config.Configuration;
import dao.ArticlesDAO;
import dao.common.DAOConstants;
import dao.common.DBConstants;
import dao.common.SQLQueries;
import jakarta.enterprise.inject.New;
import model.Newspaper;
import model.QueryDescAndReaders;
import model.Reader;
import org.springframework.core.NestedRuntimeException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import serivces.common.ServicesConstants;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import model.Article;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ArticlesDAOImpl implements ArticlesDAO {

    private Configuration configuration;
    private DBConnection dbConnection;

    @Inject
    public ArticlesDAOImpl(Configuration configuration, DBConnection dbConnection) {
        this.configuration = configuration;
        this.dbConnection = dbConnection;
    }

    @Override
    public Either<Integer, List<Article>> getAll(Reader reader, Newspaper newspaper) {
        Either<Integer, List<Article>> either;
        try (Connection con = dbConnection.getConnection();
             PreparedStatement prpstatement = con.prepareStatement(SQLQueries.SELECT_UNREAD_ARTICLES_BY_READER_AND_NEWS)) {
            prpstatement.setInt(1, newspaper.getId());
            prpstatement.setInt(2, reader.getId());
            ResultSet rs = prpstatement.executeQuery();
            List<Article> articles = new ArrayList<>();
            while (rs.next()) {
                int id = rs.getInt(DBConstants.ID);
                String name = rs.getString(DBConstants.NAME_ARTICLE);
                int idType = rs.getInt(DBConstants.ID_TYPE);
                int idNewspaper = rs.getInt(DBConstants.ID_NEWSPAPER);
                String description = rs.getString(DBConstants.DESCRIPTION);
                articles.add(new Article(id, name, description, idType, idNewspaper));
            }
            either = Either.right(articles);
        } catch (SQLException e) {
            either = Either.left(-3);
        } catch (Exception e) {
            either = Either.left(-2);
        }
        return either;
    }

    @Override
    public Either<Integer, List<Article>> getAll() {
        try {
            JdbcTemplate jtm = new JdbcTemplate(dbConnection.getHikariDataSource());
            return Either.right(jtm.query(SQLQueries.SELECT_ALL_ARTICLES, BeanPropertyRowMapper.newInstance(Article.class)));
        } catch (NestedRuntimeException e) {
            return Either.left(-3);
        } catch (Exception e) {
            return Either.left(-2);
        }
    }

    @Override
    public Either<Integer, List<Article>> getAll(int idType) {
        try {
            JdbcTemplate jtm = new JdbcTemplate(dbConnection.getHikariDataSource());
            return Either.right(jtm.query(SQLQueries.SELECT_ARTICLES_BY_TYPE, BeanPropertyRowMapper.newInstance(Article.class), idType));
        } catch (NestedRuntimeException e) {
            return Either.left(-3);
        } catch (Exception e) {
            return Either.left(-2);
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
            String message = e.getMessage();
            if (message != null && message.contains(DAOConstants.SQL_INTEGRITY_CONSTRAINT_VIOLATION_EXCEPTION)) {
                code = 1062;
            } else {
                code = -3;
            }
        }
        catch (Exception e) {
            code = -2;
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
            either = Either.left(-3);
        } catch (Exception e) {
            either = Either.left(-2);
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
            return Either.left(-3);
        } catch (Exception e) {
            return Either.left(-2);
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
}
