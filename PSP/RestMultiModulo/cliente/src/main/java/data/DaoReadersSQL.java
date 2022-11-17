package data;

import common.Constantes;
import data.common.SQLQueries;
import data.retrofit.NewspapersApi;
import io.reactivex.rxjava3.core.Single;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import model.Reader;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DaoReadersSQL extends DaoGenerics {
    private final NewspapersApi newspapersApi;

    @Inject
    public DaoReadersSQL(NewspapersApi newspapersApi) {
        this.newspapersApi = newspapersApi;
    }

    public Single<Either<String, List<Reader>>> getAll() {
        return createSafeSingleApiCall(newspapersApi.getReaders());
    }

    public Single<Either<String, Reader>> add(Reader reader) {
        return createSafeSingleApiCall(newspapersApi.addReader(reader));
    }

    public Single<Either<String, Reader>> update(Reader reader) {
        return createSafeSingleApiCall(newspapersApi.updateReader(reader));
    }

    public Single<Either<String, Reader>> delete(String id) {
        return createSafeSingleApiCall(newspapersApi.deleteReader(id));
    }

//    public Either<Integer, List<Reader>> getAll(int id) {
//        List<Reader> readers = new ArrayList<>();
//        try (Connection con = db.getConnection();
//             PreparedStatement preparedStatement = con.prepareStatement(SQLQueries.SELECT_READERS_BY_NEWSPAPER)) {
//            preparedStatement.setInt(1, id);
//            ResultSet rs = preparedStatement.executeQuery();
//            readers = readRS(rs);
//        } catch (SQLException e) {
//            Logger.getLogger(DaoReadersSQL.class.getName()).log(Level.SEVERE, null, e);
//        }
//        return readers.isEmpty() ? Either.left(-1) : Either.right(readers);
//    }
//
//    public Either<Integer, List<Reader>> getAll(String articleType) {
//        List<Reader> readers = new ArrayList<>();
//        try (Connection con = db.getConnection();
//             PreparedStatement preparedStatement = con.prepareStatement(SQLQueries.SELECT_READERS_BY_ARTICLE_TYPE)) {
//            preparedStatement.setString(1, articleType);
//            ResultSet rs = preparedStatement.executeQuery();
//            readers = readRS(rs);
//        } catch (SQLException e) {
//            Logger.getLogger(DaoReadersSQL.class.getName()).log(Level.SEVERE, null, e);
//        }
//        return readers.isEmpty() ? Either.left(-1) : Either.right(readers);
//    }
//
//    private void saveLogin(int id, String name, String password) {
//        try (Connection con = db.getConnection();
//             PreparedStatement preparedStatement = con.prepareStatement(SQLQueries.INSERT_LOGIN)) {
//            preparedStatement.setString(1, name);
//            preparedStatement.setString(2, password);
//            preparedStatement.setInt(3, id);
//            preparedStatement.executeUpdate();
//        } catch (SQLException e) {
//            Logger.getLogger(DaoReadersSQL.class.getName()).log(Level.SEVERE, null, e);
//        }
//    }
//
//    private void deleteLogin(String name) {
//        try (Connection con = db.getConnection();
//             PreparedStatement preparedStatement = con.prepareStatement(SQLQueries.DELETE_FROM_LOGIN)) {
//            preparedStatement.setString(1, name);
//            preparedStatement.executeUpdate();
//        } catch (SQLException e) {
//            Logger.getLogger(DaoReadersSQL.class.getName()).log(Level.SEVERE, null, e);
//        }
//    }
//
//    private void deleteSubscriptions(int id) {
//        try (Connection con = db.getConnection();
//             PreparedStatement preparedStatement = con.prepareStatement(SQLQueries.DELETE_FROM_SUBSCRIPTIONS)) {
//            preparedStatement.setInt(1, id);
//            preparedStatement.executeUpdate();
//        } catch (SQLException e) {
//            Logger.getLogger(DaoReadersSQL.class.getName()).log(Level.SEVERE, null, e);
//        }
//    }
//
//    private void deleteReadArticles(int id) {
//        try (Connection con = db.getConnection();
//             PreparedStatement preparedStatement = con.prepareStatement(SQLQueries.DELETE_FROM_READARTICLES)) {
//            preparedStatement.setInt(1, id);
//            preparedStatement.executeUpdate();
//        } catch (SQLException e) {
//            Logger.getLogger(DaoReadersSQL.class.getName()).log(Level.SEVERE, null, e);
//        }
//    }
//
//    private void updateLogin(Integer id, String name) {
//        try (Connection con = db.getConnection();
//             PreparedStatement preparedStatement = con.prepareStatement(SQLQueries.UPDATE_READER_LOGIN)) {
//            preparedStatement.setString(1, name);
//            preparedStatement.setInt(2, id);
//            preparedStatement.executeUpdate();
//        } catch (SQLException e) {
//            Logger.getLogger(DaoReadersSQL.class.getName()).log(Level.SEVERE, null, e);
//        }
//    }

    private List<Reader> readRS(ResultSet rs) {
        List<Reader> readers = new ArrayList<>();
        try {
            while (rs.next()) {
                Reader reader = new Reader();
                reader.setId(rs.getInt(Constantes.ID));
                reader.setName(rs.getString(Constantes.NAME));
                reader.setDateOfBirth(rs.getDate(Constantes.DATE_OF_BIRTH).toLocalDate());
                readers.add(reader);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DaoReadersSQL.class.getName()).log(Level.SEVERE, null, ex);
        }
        return readers;
    }

//    public Reader get(int id) {
//        Reader reader = new Reader();
//        try (Connection con = db.getConnection();
//             PreparedStatement preparedStatement = con.prepareStatement(SQLQueries.SELECT_READER_BY_ID)) {
//            preparedStatement.setInt(1, id);
//            ResultSet rs = preparedStatement.executeQuery();
//            if (rs.next()) {
//                reader.setId(rs.getInt(Constantes.ID));
//                reader.setName(rs.getString(Constantes.NAME));
//                reader.setDateOfBirth(rs.getDate(Constantes.DATE_OF_BIRTH).toLocalDate());
//            }
//        } catch (SQLException e) {
//            Logger.getLogger(DaoReadersSQL.class.getName()).log(Level.SEVERE, null, e);
//        }
//        return reader;
//    }
//
//    public Either<Integer, List<Reader>> getOldestSubscribers() {
//        List<Reader> readers = new ArrayList<>();
//        try (Connection con = db.getConnection();
//             PreparedStatement preparedStatement = con.prepareStatement(SQLQueries.SELECT_OLDEST_SUBSCRIBERS)) {
//            ResultSet rs = preparedStatement.executeQuery();
//            readers = readRS(rs, 5);
//        } catch (SQLException e) {
//            Logger.getLogger(DaoReadersSQL.class.getName()).log(Level.SEVERE, null, e);
//        }
//        return readers.isEmpty() ? Either.left(-1) : Either.right(readers);
//    }

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
            Logger.getLogger(DaoReadersSQL.class.getName()).log(Level.SEVERE, null, ex);
        }
        return readers;
    }
}