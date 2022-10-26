package data;

import common.Constantes;
import data.common.SQLQueries;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import model.Article;
import model.ArticleType;
import model.Reader;

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
        try (Connection con = db.getConnection();
             Statement statement = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                     ResultSet.CONCUR_READ_ONLY)) {

            ResultSet rs = statement.executeQuery(SQLQueries.SELECT_ARTICLES);
            articles = readRS(rs);

        } catch (SQLException ex) {
            Logger.getLogger(DaoArticlesSQL.class.getName()).log(Level.SEVERE, null, ex);
        }
        return articles.isEmpty() ? Either.left(-1) : Either.right(articles);
    }

    public Either<Integer, List<Article>> getAll(Integer id) {
        List<Article> articles = new ArrayList<>();
        try (Connection con = db.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(SQLQueries.SELECT_ARTICLES_BY_READER)) {
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            articles = readRS(rs);
        } catch (SQLException e) {
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

    public Either<Integer, List<Article>> saveReadArticle(Article article, Integer rating, Integer readerId) {
        List<Article> articles = new ArrayList<>();
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
        return articles.isEmpty() ? Either.left(-1) : Either.right(articles);
    }


    private List<Article> readRS(ResultSet rs) {
        List<Article> articles = new ArrayList<>();
        try {
            while (rs.next()) {
                Article article = new Article();
                article.setId(rs.getInt(Constantes.ID));
                article.setName_article(rs.getString(Constantes.NAME_ARTICLE));
                article.setId_type(rs.getInt(Constantes.ID_TYPE));
                article.setId_newspaper(rs.getInt(Constantes.ID_NEWSPAPER));
                articles.add(article);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DaoArticlesSQL.class.getName()).log(Level.SEVERE, null, ex);
        }
        return articles;
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
}