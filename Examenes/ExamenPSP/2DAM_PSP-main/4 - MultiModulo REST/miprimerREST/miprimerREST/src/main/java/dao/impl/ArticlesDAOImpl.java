package dao.impl;

import dao.ArticlesDAO;
import dao.common.DAOConstants;
import dao.common.DBConstants;
import dao.common.SQLQueries;
import domain.modelo.Article;
import domain.modelo.QueryArticlesAndNews;
import domain.modelo.QueryDescAndReaders;
import domain.modelo.errores.BaseDeDatosException;
import domain.modelo.errores.InvalidFieldsException;
import domain.modelo.errores.NotFoundException;
import domain.modelo.errores.OtherErrorException;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import org.springframework.core.NestedRuntimeException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ArticlesDAOImpl implements ArticlesDAO {

    private final DBConnection dbConnection;

    @Inject
    public ArticlesDAOImpl(DBConnection dbConnection) {
        this.dbConnection = dbConnection;
    }


    @Override
    public List<Article> getAllByNewspaper(String newsId) {
        try (Connection con = dbConnection.getConnection();
             PreparedStatement prpstatement = con.prepareStatement(SQLQueries.SELECT_ARTICLES_BY_NEWS)) {
            prpstatement.setString(1, newsId);
            ResultSet rs = prpstatement.executeQuery();
            List<Article> articles = readAllArticles(rs);
            if (articles.isEmpty()) {
                throw new NotFoundException(DAOConstants.THERE_ARE_NO_ARTICLES_IN_THE_NEWSPAPER + newsId);
            }
            return articles;
        } catch (NotFoundException e) {
            throw new NotFoundException(e.getMessage());
        } catch (SQLException e) {
            throw new BaseDeDatosException(e.getMessage());
        } catch (Exception e) {
            throw new OtherErrorException(e.getMessage());
        }
    }

    private List<Article> readAllArticles(ResultSet rs) throws SQLException {
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
    public List<Article> getAll() {
        try {
            JdbcTemplate jtm = new JdbcTemplate(dbConnection.getHikariDataSource());
            return jtm.query(SQLQueries.SELECT_ALL_ARTICLES, BeanPropertyRowMapper.newInstance(Article.class));
        } catch (NestedRuntimeException e) {
            throw new BaseDeDatosException(e.getMessage());
        } catch (Exception e) {
            throw new OtherErrorException(e.getMessage());
        }
    }

    @Override
    public List<Article> getAllByType(String idType) {
        try {
            JdbcTemplate jtm = new JdbcTemplate(dbConnection.getHikariDataSource());
            List<Article> articles = jtm.query(SQLQueries.SELECT_ARTICLES_BY_TYPE, BeanPropertyRowMapper.newInstance(Article.class), idType);
            if (!articles.isEmpty()) {
                return articles;
            }
            throw new NotFoundException(DAOConstants.THERE_ARE_NO_ARTICLES_OF_TYPE + idType);
        } catch (NotFoundException e) {
            throw new NotFoundException(e.getMessage());
        } catch (NestedRuntimeException e) {
            throw new BaseDeDatosException(e.getMessage());
        } catch (Exception e) {
            throw new OtherErrorException(e.getMessage());
        }
    }


    // POR EL DAO PATTERN, LUCIA PIDIO QUE ESTEN TODAS LAS FUNCIONES AUNQUE NO HAYAN SIDO IMPLEMENTADAS
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
        } catch (NestedRuntimeException e) {
            String message = e.getCause().toString();
            if (message != null && message.contains(DAOConstants.SQL_INTEGRITY_CONSTRAINT_VIOLATION_EXCEPTION)) {
                throw new InvalidFieldsException(DAOConstants.ARTICLE_ALREADY_EXISTS);
            } else {
                throw new BaseDeDatosException(e.getMessage());
            }
        } catch (Exception e) {
            throw new OtherErrorException(e.getMessage());
        }
        return code;
    }

    public QueryDescAndReaders getQuery(String id) {
        try (Connection con = dbConnection.getConnection();
             PreparedStatement prpStatement = con.prepareStatement(SQLQueries.SELECT_READERS_AND_DESCRIPTION_BY_ARTICLE)) {
            prpStatement.setString(1, id);
            ResultSet rs = prpStatement.executeQuery();
            return readRS(rs, id);
        } catch (NotFoundException e) {
            throw new NotFoundException(e.getMessage());
        } catch (SQLException e) {
            throw new BaseDeDatosException(e.getMessage());
        } catch (Exception e) {
            throw new OtherErrorException(e.getMessage());
        }
    }

    private QueryDescAndReaders readRS(ResultSet rs, String id) {
        try {
            if (rs.next()) {
                String description = rs.getString(DBConstants.DESCRIPTION) != null ? rs.getString(DBConstants.DESCRIPTION) : "";
                int cantReaders = rs.getInt(DBConstants.CANT_READERS);
                return new QueryDescAndReaders(description, cantReaders);
            }
            throw new NotFoundException(DAOConstants.THERE_ARE_NO_ARTICLES_WITH_ID + id);
        } catch (NotFoundException e) {
            throw new NotFoundException(e.getMessage());
        } catch (SQLException e) {
            throw new BaseDeDatosException(e.getMessage());
        } catch (Exception e) {
            throw new OtherErrorException(e.getMessage());
        }
    }

    // POR EL DAO PATTERN, LUCIA PIDIO QUE ESTEN TODAS LAS FUNCIONES AUNQUE NO HAYAN SIDO IMPLEMENTADAS
    @Override
    public int update(Article article) {
        return 0;
    }

    // POR EL DAO PATTERN, LUCIA PIDIO QUE ESTEN TODAS LAS FUNCIONES AUNQUE NO HAYAN SIDO IMPLEMENTADAS
    @Override
    public int delete(List<Article> articlesList) {
        return -1;
    }

    @Override
    public List<QueryArticlesAndNews> getAllQuerySpring(String idType) {
        try {
            JdbcTemplate jtm = new JdbcTemplate(dbConnection.getHikariDataSource());
            List<QueryArticlesAndNews> queryList = jtm.query(SQLQueries.SELECT_ARTNAME_AND_NEWSNAME, BeanPropertyRowMapper.newInstance(QueryArticlesAndNews.class), idType);
            if (!queryList.isEmpty()) {
                return queryList;
            }
            throw new NotFoundException(DAOConstants.THERE_ARE_NO_ARTICLES_OF_TYPE + idType);
        } catch (NotFoundException e) {
            throw new NotFoundException(e.getMessage());
        } catch (NestedRuntimeException e) {
            throw new BaseDeDatosException(e.getMessage());
        } catch (Exception e) {
            throw new OtherErrorException(e.getMessage());
        }
    }
}
