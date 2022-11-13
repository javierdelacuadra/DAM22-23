package data;

import common.Constantes;
import data.common.SQLQueries;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import model.*;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DaoArticlesSQL {

    private final DBConnection db;

    @Inject
    public DaoArticlesSQL(DBConnection db) {
        this.db = db;
    }

    public Either<Integer, List<Article>> getAll() {
        List<Article> articles = new ArrayList<>();
        try {
            String query = SQLQueries.SELECT_ARTICLES;
            JdbcTemplate jdbc = new JdbcTemplate(db.getHikariDataSource());
            articles = jdbc.query(query, BeanPropertyRowMapper.newInstance(Article.class));
        } catch (DataAccessException e) {
            Logger.getLogger(DaoArticlesSQL.class.getName()).log(Level.SEVERE, null, e);
        }
        return articles.isEmpty() ? Either.left(-1) : Either.right(articles);
    }

    public Either<Integer, List<Article>> getAll(Integer id) {
        List<Article> articles = new ArrayList<>();
        try {
            String query = SQLQueries.SELECT_ARTICLES_BY_READER;
            JdbcTemplate jdbc = new JdbcTemplate(db.getHikariDataSource());
            articles = jdbc.query(query, BeanPropertyRowMapper.newInstance(Article.class), id);
        } catch (DataAccessException e) {
            Logger.getLogger(DaoArticlesSQL.class.getName()).log(Level.SEVERE, null, e);
        }
        return articles.isEmpty() ? Either.left(-1) : Either.right(articles);
    }

    public Either<Integer, List<Article>> getAll(String type) {
        List<Article> articles = new ArrayList<>();
        try {
            String query = SQLQueries.SELECT_ARTICLES_BY_TYPE;
            JdbcTemplate jdbc = new JdbcTemplate(db.getHikariDataSource());
            articles = jdbc.query(query, BeanPropertyRowMapper.newInstance(Article.class), type);
        } catch (DataAccessException e) {
            Logger.getLogger(DaoArticlesSQL.class.getName()).log(Level.SEVERE, null, e);
        }
        return articles.isEmpty() ? Either.left(-1) : Either.right(articles);
    }

    public Either<Integer, List<ArticleType>> getAllArticleTypes() {
        List<ArticleType> types = new ArrayList<>();
        try (Connection con = db.getConnection();
             Statement statement = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                     ResultSet.CONCUR_READ_ONLY)) {

            ResultSet rs = statement.executeQuery(SQLQueries.SELECT_ARTICLE_TYPE);
            types = readRSArticleType(rs);

        } catch (SQLException ex) {
            Logger.getLogger(DaoArticlesSQL.class.getName()).log(Level.SEVERE, null, ex);
        }
        return types.isEmpty() ? Either.left(-1) : Either.right(types);
    }

    public Either<Integer, List<Query1>> getArticlesQuery() {
        List<Query1> articles = new ArrayList<>();
        try (Connection con = db.getConnection();
             Statement statement = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                     ResultSet.CONCUR_READ_ONLY)) {

            ResultSet rs = statement.executeQuery(SQLQueries.SELECT_ARTICLE_TYPE_ARTICLE_NAME_AND_READERS);
            articles = readRSQuery(rs);
        } catch (SQLException ex) {
            Logger.getLogger(DaoArticlesSQL.class.getName()).log(Level.SEVERE, null, ex);
        }
        return articles.isEmpty() ? Either.left(-1) : Either.right(articles);
    }

    private List<Query1> readRSQuery(ResultSet rs) {
        List<Query1> articles = new ArrayList<>();
        try {
            while (rs.next()) {
                Query1 article = new Query1();
                article.setName_article(rs.getString(Constantes.NAME_ARTICLE));
                article.setCount(rs.getInt(Constantes.READERS));
                article.setDescription(rs.getString(Constantes.DESCRIPTION));
                articles.add(article);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DaoArticlesSQL.class.getName()).log(Level.SEVERE, null, ex);
        }
        return articles;
    }

    private List<ReadArticle> readRSReadArticle(ResultSet rs) {
        List<ReadArticle> articles = new ArrayList<>();
        try {
            while (rs.next()) {
                ReadArticle article = new ReadArticle();
                article.setId(rs.getInt(Constantes.ID));
                article.setId_article(rs.getInt(Constantes.ID_ARTICLE));
                article.setId_reader(rs.getInt(Constantes.ID_READER));
                article.setRating(rs.getInt(Constantes.RATING));
                articles.add(article);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DaoArticlesSQL.class.getName()).
                    log(Level.SEVERE, null, ex);
        }
        return articles;
    }

    public Either<Integer, List<Article>> saveReadArticle(Article article, Integer rating, Integer readerId) {
        List<ReadArticle> readArticles = new ArrayList<>();
        List<Article> articles = new ArrayList<>();
        try (Connection con = db.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(SQLQueries.SELECT_READARTICLES_BY_ID_ARTICLE)) {
            preparedStatement.setInt(1, article.getId());
            preparedStatement.setInt(2, readerId);
            ResultSet rs = preparedStatement.executeQuery();
            readArticles = readRSReadArticle(rs);
        } catch (SQLException e) {
            Logger.getLogger(DaoArticlesSQL.class.getName()).log(Level.SEVERE, null, e);
        }
        if (readArticles.isEmpty()) {
            try (Connection con = db.getConnection();
                 PreparedStatement preparedStatement = con.prepareStatement(SQLQueries.INSERT_READ_ARTICLE)) {
                preparedStatement.setInt(1, article.getId());
                preparedStatement.setInt(2, readerId);
                preparedStatement.setInt(3, rating);
                preparedStatement.executeUpdate();
                articles = getAll(readerId).get();
            } catch (SQLException ex) {
                Logger.getLogger(DaoArticlesSQL.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            return Either.left(-2);
        }
        return articles.isEmpty() ? Either.left(-1) : Either.right(articles);
    }


    private List<ArticleType> readRSArticleType(ResultSet rs) {
        List<ArticleType> types = new ArrayList<>();
        try {
            while (rs.next()) {
                ArticleType type = new ArticleType();
                type.setId(rs.getInt(Constantes.ID));
                type.setDescription(rs.getString(Constantes.DESCRIPTION));
                types.add(type);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DaoArticlesSQL.class.getName()).log(Level.SEVERE, null, ex);
        }
        return types;
    }

    public Either<Integer, List<Query2>> getArticlesByTypeAndNameNewspaper(String type, String nameNewspaper) {
        List<Query2> articles = new ArrayList<>();
        try {
            String query = SQLQueries.SELECT_ARTICLES_BY_TYPE_AND_NEWSPAPER;
            JdbcTemplate jdbc = new JdbcTemplate(db.getHikariDataSource());
            articles = jdbc.query(query, BeanPropertyRowMapper.newInstance(Query2.class), type, nameNewspaper);
        } catch (Exception e) {
            Logger.getLogger(DaoArticlesSQL.class.getName()).log(Level.SEVERE, null, e);
        }
        return articles.isEmpty() ? Either.left(-1) : Either.right(articles);
    }

    public Either<Integer, List<Query3>> getArticlesByNewspaperWithBadRatings(String idNewspaper) {
        List<Query3> articles = new ArrayList<>();
        try {
            String query = SQLQueries.SELECT_ARTICLES_BY_NEWSPAPER_AND_BAD_RATINGS;
            JdbcTemplate jdbc = new JdbcTemplate(db.getHikariDataSource());
            articles = jdbc.query(query, BeanPropertyRowMapper.newInstance(Query3.class), idNewspaper);
        } catch (Exception e) {
            Logger.getLogger(DaoArticlesSQL.class.getName()).log(Level.SEVERE, null, e);
        }
        return articles.isEmpty() ? Either.left(-1) : Either.right(articles);
    }


    public Either<Integer, List<Article>> add(Article article) {
        List<Article> articles = new ArrayList<>();
        try {
            String query = SQLQueries.INSERT_ARTICLE;
            JdbcTemplate jdbc = new JdbcTemplate(db.getHikariDataSource());
            jdbc.update(query, article.getName_article(), article.getId_type(), article.getId_newspaper());
            articles = getAll().get();
        } catch (DataAccessException e) {
            Logger.getLogger(DaoArticlesSQL.class.getName()).log(Level.SEVERE, null, e);
        }
        return articles.isEmpty() ? Either.left(-1) : Either.right(articles);
    }
}