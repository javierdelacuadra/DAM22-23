package dao;

import dao.common.Constantes;
import dao.common.SQLQueries;
import domain.exceptions.DatabaseException;
import jakarta.inject.Inject;
import modelo.ReadArticle;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DaoReadArticles {

    private final DBConnection db;

    @Inject
    public DaoReadArticles(DBConnection db) {
        this.db = db;
    }

    public boolean saveReadArticle(String idArticle, String idReader, String rating) {
        List<ReadArticle> readArticles;
        try (Connection con = db.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(SQLQueries.SELECT_READARTICLES_BY_ID_ARTICLE)) {
            preparedStatement.setInt(1, Integer.parseInt(idArticle));
            ResultSet rs = preparedStatement.executeQuery();
            readArticles = readRSReadArticle(rs);
        } catch (SQLException e) {
            throw new DatabaseException(Constantes.ERROR_AL_LEER_LOS_READ_ARTICLES);
        }
        if (readArticles.isEmpty()) {
            try (Connection con = db.getConnection();
                 PreparedStatement preparedStatement = con.prepareStatement(SQLQueries.INSERT_READ_ARTICLE)) {
                preparedStatement.setInt(1, Integer.parseInt(idArticle));
                preparedStatement.setInt(2, Integer.parseInt(idReader));
                preparedStatement.setInt(3, Integer.parseInt(rating));
                preparedStatement.executeUpdate();
            } catch (SQLException ex) {
                throw new DatabaseException(Constantes.ERROR_AL_INSERTAR_EL_READ_ARTICLE);
            }
        } else {
            return false;
        }
        return true;
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
            throw new DatabaseException(Constantes.ERROR_AL_LEER_LOS_READ_ARTICLES);
        }
        return articles;
    }
}
