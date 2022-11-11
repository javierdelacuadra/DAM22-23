package dao;

import dao.common.Constantes;
import dao.common.SQLQueries;
import dao.modelo.Query1;
import dao.modelo.Reader;
import domain.exceptions.DatabaseException;
import jakarta.inject.Inject;

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
            readers = readRS(rs, 5);
        } catch (SQLException e) {
            throw new DatabaseException(Constantes.ERROR_AL_REALIZAR_LA_CONSULTA);
        }
        return readers;
    }

    private List<Reader> readRS(ResultSet rs, Integer limit) {
        List<Reader> readers = new ArrayList<>();
        int count = 0;
        try {
            while (rs.next() && count < limit) {
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
}
