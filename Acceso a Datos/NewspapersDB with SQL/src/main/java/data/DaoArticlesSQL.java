package data;

import common.Constantes;
import data.common.SQLQueries;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import model.Article;
import model.ArticleType;
import model.Reader;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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

            ResultSet rs = statement.executeQuery(SQLQueries.SELECT_READERS);
            articles = readRS(rs);

        } catch (SQLException ex) {
            Logger.getLogger(DaoArticlesSQL.class.getName()).log(Level.SEVERE, null, ex);
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


    private List<Article> readRS(ResultSet rs) {
        List<Article> articles = new ArrayList<>();
        try {
            while (rs.next()) {
                Article article = new Article();
                article.setId(rs.getInt(Constantes.ID));
                article.setNameArticle(rs.getString(Constantes.NAME_ARTICLE));
                article.setIdType(rs.getInt(Constantes.ID_TYPE));
                article.setIdNewspaper(rs.getInt(Constantes.ID_NEWSPAPER));
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