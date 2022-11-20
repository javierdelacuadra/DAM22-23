package dao;

import dao.common.Constantes;
import dao.common.SQLQueries;
import domain.exceptions.DatabaseException;
import jakarta.inject.Inject;
import modelo.Query1;
import modelo.Query2;
import modelo.Query3;
import modelo.Reader;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DaoQueries {

    private final DBConnection db;

    @Inject
    public DaoQueries(DBConnection db) {
        this.db = db;
    }

    public List<Query1> getArticlesQuery() {
        List<Query1> articles;
        try (Connection con = db.getConnection();
             Statement statement = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                     ResultSet.CONCUR_READ_ONLY)) {

            ResultSet rs = statement.executeQuery(SQLQueries.SELECT_ARTICLE_TYPE_ARTICLE_NAME_AND_READERS);
            articles = readRSQuery(rs);
        } catch (SQLException ex) {
            throw new DatabaseException(Constantes.ERROR_AL_REALIZAR_LA_CONSULTA);
        }
        return articles;
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
            throw new DatabaseException(Constantes.ERROR_AL_REALIZAR_LA_CONSULTA);
        }
        return articles;
    }

    public List<Reader> getOldestSubscribers() {
        List<Reader> readers;
        try (Connection con = db.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(SQLQueries.SELECT_OLDEST_SUBSCRIBERS)) {
            ResultSet rs = preparedStatement.executeQuery();
            readers = readRS(rs);
        } catch (SQLException e) {
            throw new DatabaseException(Constantes.ERROR_AL_REALIZAR_LA_CONSULTA);
        }
        return readers;
    }

    private List<Reader> readRS(ResultSet rs) {
        List<Reader> readers = new ArrayList<>();
        int count = 0;
        try {
            while (rs.next() && count < 5) {
                Reader reader = new Reader();
                reader.setId(rs.getInt(Constantes.ID));
                reader.setName(rs.getString(Constantes.NAME));
                reader.setDateOfBirth(rs.getDate(Constantes.DATE_OF_BIRTH).toLocalDate());
                readers.add(reader);
                count++;
            }
        } catch (SQLException ex) {
            throw new DatabaseException(Constantes.ERROR_AL_LEER_LOS_READER);
        }
        return readers;
    }

    public List<Query2> getArticlesByTypeAndNameNewspaper(String type) {
        List<Query2> articles;
        try (Connection con = db.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(SQLQueries.SELECT_ARTICLES_BY_TYPE_AND_NEWSPAPER)) {
            preparedStatement.setString(1, type);
            ResultSet rs = preparedStatement.executeQuery();
            articles = readRSQuery2(rs);
        } catch (SQLException e) {
            throw new DatabaseException(Constantes.ERROR_AL_REALIZAR_LA_CONSULTA);
        }
        return articles;
    }

    private List<Query2> readRSQuery2(ResultSet rs) {
        List<Query2> articles = new ArrayList<>();
        try {
            while (rs.next()) {
                Query2 article = new Query2();
                article.setId(rs.getInt(Constantes.ID));
                article.setName_article(rs.getString(Constantes.NAME_ARTICLE));
                article.setIdType(rs.getInt(Constantes.ID_TYPE));
                article.setName_newspaper(rs.getString(Constantes.NAME));
                articles.add(article);
            }
        } catch (SQLException ex) {
            throw new DatabaseException(Constantes.ERROR_AL_REALIZAR_LA_CONSULTA);
        }
        return articles;
    }

    public List<Query3> getArticlesByNewspaperWithBadRatings(String idNewspaper) {
        List<Query3> articles;
        try (Connection con = db.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(SQLQueries.SELECT_ARTICLES_BY_NEWSPAPER_AND_BAD_RATINGS)) {
            preparedStatement.setInt(1, Integer.parseInt(idNewspaper));
            ResultSet rs = preparedStatement.executeQuery();
            articles = readRSQuery3(rs);
        } catch (SQLException e) {
            throw new DatabaseException(Constantes.ERROR_AL_REALIZAR_LA_CONSULTA);
        }
        return articles;
    }

    private List<Query3> readRSQuery3(ResultSet rs) {
        List<Query3> articles = new ArrayList<>();
        try {
            while (rs.next()) {
                Query3 article = new Query3();
                article.setId(rs.getInt(Constantes.ID));
                article.setName_article(rs.getString(Constantes.NAME_ARTICLE));
                article.setId_reader(rs.getInt(Constantes.ID_READER));
                article.setRating(rs.getInt(Constantes.RATING));
                article.setBad_ratings(rs.getInt(Constantes.BAD_RATINGS));
                articles.add(article);
            }
        } catch (SQLException ex) {
            throw new DatabaseException(Constantes.ERROR_AL_REALIZAR_LA_CONSULTA);
        }
        return articles;
    }
}
