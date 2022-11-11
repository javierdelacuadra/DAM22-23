package dao;

import dao.common.Constantes;
import dao.common.SQLQueries;
import dao.modelo.Article;
import domain.exceptions.ObjectNotFoundException;
import jakarta.inject.Inject;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DaoArticles {

    private final DBConnection db;

    @Inject
    public DaoArticles(DBConnection db) {
        this.db = db;
    }

    public List<Article> getAll() {
        List<Article> articles;
        try (Connection connection = db.getConnection()) {
            Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = statement.executeQuery(SQLQueries.SELECT_ARTICLES);
            articles = readRS(rs);
        } catch (SQLException e) {
            throw new ObjectNotFoundException(Constantes.NO_SE_HAN_ENCONTRADO_ARTICLES);

        }
        return articles;
    }

    public List<Article> readRS(ResultSet rs) {
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
        } catch (SQLException e) {
            throw new ObjectNotFoundException(Constantes.NO_SE_HAN_ENCONTRADO_ARTICLES);
        }
        return articles;
    }

    public Article get(String id) {
        Article article = null;
        try (Connection connection = db.getConnection()) {
            Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = statement.executeQuery(SQLQueries.SELECT_ARTICLE_BY_ID + id);
            List<Article> articles = readRS(rs);
            if (articles.size() > 0) {
                article = articles.get(0);
            }
        } catch (SQLException e) {
            throw new ObjectNotFoundException(Constantes.NO_SE_HA_ENCONTRADO_EL_ARTICLE);
        }
        return article;
    }

    public boolean save(Article article) {
        List<Article> articles = getAll();
        if (articles.stream().noneMatch(a -> a.getName_article().equals(article.getName_article()))) {
            try (Connection connection = db.getConnection()) {
                Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                statement.executeUpdate(SQLQueries.INSERT_ARTICLE);
            } catch (SQLException e) {
                throw new ObjectNotFoundException(Constantes.NO_SE_HA_PODIDO_GUARDAR_EL_ARTICLE);
            }
            return true;
        }
        throw new ObjectNotFoundException(Constantes.YA_EXISTE_UN_ARTICLE_CON_ESE_NOMBRE);
    }

    public boolean update(Article article) {
        List<Article> articles = getAll();
        if (articles.stream().anyMatch(a -> a.getName_article().equals(article.getName_article()))) {
            try (Connection connection = db.getConnection()) {
                Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                statement.executeUpdate(SQLQueries.UPDATE_ARTICLE);
            } catch (SQLException e) {
                throw new ObjectNotFoundException(Constantes.NO_SE_HA_PODIDO_ACTUALIZAR_EL_ARTICLE);
            }
            return true;
        }
        throw new ObjectNotFoundException(Constantes.NO_EXISTE_UN_ARTICLE_CON_ESE_NOMBRE);
    }

    public boolean delete(String id) {
        List<Article> articles = getAll();
        if (articles.stream().anyMatch(a -> a.getId() == Integer.parseInt(id))) {
            try (Connection connection = db.getConnection()) {
                Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                statement.executeUpdate(SQLQueries.DELETE_ARTICLE + id);
            } catch (SQLException e) {
                throw new ObjectNotFoundException(Constantes.NO_SE_HA_PODIDO_ELIMINAR_EL_ARTICLE);
            }
            return true;
        }
        throw new ObjectNotFoundException(Constantes.NO_EXISTE_UN_ARTICLE_CON_ESE_ID);
    }

}