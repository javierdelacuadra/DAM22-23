package dao;

import dao.common.Constantes;
import dao.common.SQLQueries;
import domain.exceptions.DatabaseException;
import domain.exceptions.ObjectNotFoundException;
import jakarta.inject.Inject;
import modelo.Article;

import java.sql.*;
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
        Article article;
        try (Connection connection = db.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQLQueries.SELECT_ARTICLE_BY_ID)) {
            preparedStatement.setInt(1, Integer.parseInt(id));
            ResultSet rs = preparedStatement.executeQuery();
            article = readRS(rs).get(0);
        } catch (SQLException | IndexOutOfBoundsException e) {
            throw new ObjectNotFoundException(Constantes.NO_SE_HAN_ENCONTRADO_ARTICLES);
        }
        return article;
    }

    public boolean save(Article article) {
        List<Article> articles = getAll();
        if (articles.stream().noneMatch(a -> a.getName_article().equals(article.getName_article()))) {
            try (Connection connection = db.getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(SQLQueries.INSERT_ARTICLE, Statement.RETURN_GENERATED_KEYS)) {
                preparedStatement.setString(1, article.getName_article());
                preparedStatement.setInt(2, article.getId_type());
                preparedStatement.setInt(3, article.getId_newspaper());
                preparedStatement.executeUpdate();
                ResultSet rs = preparedStatement.getGeneratedKeys();
                if (rs.next()) {
                    article.setId(rs.getInt(1));
                }
            } catch (SQLException e) {
                throw new ObjectNotFoundException(Constantes.NO_SE_HA_PODIDO_GUARDAR_EL_ARTICLE);
            }
            return true;
        }
        throw new ObjectNotFoundException(Constantes.YA_EXISTE_UN_ARTICLE_CON_ESE_NOMBRE);
    }

    public boolean update(Article article) {
        List<Article> articles = getAll();
        if (articles.stream().anyMatch(a -> a.getId() == article.getId())) {
            try (Connection connection = db.getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(SQLQueries.UPDATE_ARTICLE)) {
                preparedStatement.setString(1, article.getName_article());
                preparedStatement.setInt(2, article.getId_type());
                preparedStatement.setInt(3, article.getId_newspaper());
                preparedStatement.setInt(4, article.getId());
                preparedStatement.executeUpdate();
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
            try (Connection connection = db.getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(SQLQueries.DELETE_ARTICLE)) {
                preparedStatement.setInt(1, Integer.parseInt(id));
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                throw new ObjectNotFoundException(Constantes.NO_SE_HA_PODIDO_ELIMINAR_EL_ARTICLE);
            }
            return true;
        }
        throw new ObjectNotFoundException(Constantes.NO_EXISTE_UN_ARTICLE_CON_ESE_ID);
    }

    public List<Article> getArticlesByType(String type) {
        List<Article> articles;
        try (Connection con = db.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(SQLQueries.SELECT_ARTICLES_BY_TYPE)) {
            preparedStatement.setString(1, type);
            ResultSet rs = preparedStatement.executeQuery();
            articles = readRS(rs);
        } catch (SQLException e) {
            throw new DatabaseException(Constantes.ERROR_AL_REALIZAR_LA_CONSULTA);
        }
        return articles;
    }
}
