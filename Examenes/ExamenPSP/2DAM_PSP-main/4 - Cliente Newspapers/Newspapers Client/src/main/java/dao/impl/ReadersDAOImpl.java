package dao.impl;

import dao.ReadersDAO;
import dao.common.DBConstants;
import dao.common.SQLQueries;
import model.Login;
import model.Newspaper;
import model.Reader;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import lombok.extern.log4j.Log4j2;
import model.TypeArt;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Log4j2
public class ReadersDAOImpl implements ReadersDAO {

    DBConnection dbConnection;

    @Inject
    public ReadersDAOImpl(DBConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    // This method will return:
    // >0 -> List of readers
    // -2 -> error related not related to de DB (Exception e)
    // -3 -> error with the transaction connection related to de DB (SQLException e)
    @Override
    public Either<Integer, List<Reader>> getAll() {
        Either<Integer, List<Reader>> either;
        try (Connection con = dbConnection.getConnection();
             Statement statement = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                     ResultSet.CONCUR_READ_ONLY)) {

            ResultSet rs = statement.executeQuery(SQLQueries.SELECT_ALL_READERS_WITH_LOGIN);

            try {
                List<Reader> readers = new ArrayList<>();
                while (rs.next()) {
                    int id = rs.getInt(DBConstants.ID);
                    String nameReader = rs.getString(DBConstants.NAME_READER);
                    LocalDate dateOfBirth = rs.getDate(DBConstants.DATE_OF_BIRTH).toLocalDate();
                    String user = rs.getString(DBConstants.USER);
                    String password = rs.getString(DBConstants.PASSWORD);
                    readers.add(new Reader(id, nameReader, dateOfBirth, new Login(user, password, id)));
                }
                either = Either.right(readers);
            } catch (SQLException e) {
                either = Either.left(-3);
                log.error(e.getMessage());
            } catch (Exception e){
                either = Either.left(-2);
            }
        } catch (SQLException e) {
            either = Either.left(-3);
        } catch (Exception e) {
            either = Either.left(-2);
        }
        return either;
    }

    @Override
    public Either<Integer, List<Reader>> getAll(TypeArt typeArt) {
        return getAllWithOneParameter(SQLQueries.SELECT_ALL_READERS_BY_TYPE_NAME, typeArt.getName());
    }

    // This method will return:
    // >0 -> List of readers
    // -2 -> error related not related to de DB (Exception e)
    // -3 -> error with the transaction connection related to de DB (SQLException e)
    private Either<Integer, List<Reader>> getAllWithOneParameter(String query, String parameter) {
        Either<Integer, List<Reader>> either;
        try (Connection con = dbConnection.getConnection();
             PreparedStatement prpstatement = con.prepareStatement(query)) {
            prpstatement.setString(1, parameter);
            ResultSet rs = prpstatement.executeQuery();
            either = readRS(rs);
        } catch (SQLException e) {
            either = Either.left(-3);
        } catch (Exception e) {
            either = Either.left(-2);
        }
        return either;
    }

    @Override
    public Either<Integer, List<Reader>> getAll(Newspaper newspaper) {
        return getAllWithOneParameter(SQLQueries.SELECT_ALL_READERS_BY_NEWSPAPER, newspaper.getId() + "");
    }

    @Override
    public Either<Integer, List<String>> getAll(Newspaper newspaper, int limit) {
        return getListOfReadersNamesQuery(newspaper, limit);
    }

    private Either<Integer, List<String>> getListOfReadersNamesQuery(Newspaper newspaper, int limit) {
        Either<Integer, List<String>> either;
        try (Connection con = dbConnection.getConnection();
             PreparedStatement prpstatement = con.prepareStatement(SQLQueries.GET_OLDEST_SUSCRIPTORS)) {
            prpstatement.setInt(1, newspaper.getId());
            prpstatement.setInt(2, limit);
            ResultSet rs = prpstatement.executeQuery();
            try {
                List<String> names = new ArrayList<>();
                while (rs.next()) {
                    names.add(rs.getString(DBConstants.NAME_READER));
                }
                either = Either.right(names);
            } catch (SQLException e) {
                return Either.left(-3);
            }
        } catch (SQLException e) {
            either = Either.left(-3);
        } catch (Exception e) {
            either = Either.left(-2);
        }
        return either;
    }


    // This method will return:
    // >0 -> List of readers
    // -2 -> error related not related to de DB (Exception e)
    // -3 -> error with the transaction connection related to de DB (SQLException e)
    @Override
    public Either<Integer, Reader> get(int id) {
        Either<Integer, Reader> either;
        try (Connection con = dbConnection.getConnection();
             PreparedStatement prpStatement = con.prepareStatement(SQLQueries.SELECT_READER_BY_ID)) {
            prpStatement.setInt(1, id);
            ResultSet rs = prpStatement.executeQuery();
            either = readRS(rs).map(list -> list.get(0));
        } catch (SQLException e) {
            either = Either.left(-3);
        }
        return either;
    }


    // This method will return:
    // >0 -> ROWS DELETED (EXPECTED 1)
    // -1 -> error with the transaction, rollback done successfully
    // -2 -> error not related to de DB (Exception e)
    // -3 -> error with the transaction connection related to de DB (SQLException e)
    // -4 -> error with the transaction, rollback failed

    @Override
    public int delete(int id) {
        int code = deleteQuery(SQLQueries.DELETE_FROM_READERS_BY_ID, id);
        if (code == 1451) {
            code = deleteTransaction(id);
        }
        return code;
    }

    private int deleteQuery(String query, int id) {
        int code;
        try (Connection con = dbConnection.getConnection();
             PreparedStatement prpStatement = con.prepareStatement(query)) {
            prpStatement.setInt(1, id);
            code = prpStatement.executeUpdate();
        } catch (SQLException e) {
            log.error(e.getMessage());
            code = e.getErrorCode();
        } catch (Exception e) {
            log.error(e.getMessage());
            code = -2;
        }
        return code;
    }

    private int deleteTransaction(int id) {
        int code;

        try (Connection con = dbConnection.getConnection()) {

            try (PreparedStatement delSB = con.prepareStatement(SQLQueries.DELETE_FROM_SUBSCRIPTIONS_WHERE_ID_READER);
                 PreparedStatement delRA = con.prepareStatement(SQLQueries.DELETE_FROM_READARTICLES_WHERE_ID_READER);
                 PreparedStatement delLog = con.prepareStatement(SQLQueries.DELETE_FROM_LOGIN_WHERE_ID_READER);
                 PreparedStatement delReader = con.prepareStatement(SQLQueries.DELETE_FROM_READERS_BY_ID)) {

                // Disables autocommit
                con.setAutoCommit(false);

                delRA.setInt(1, id);
                delSB.setInt(1, id);
                delLog.setInt(1, id);
                delReader.setInt(1, id);

                delRA.executeUpdate();
                delSB.executeUpdate();
                delLog.executeUpdate();
                code = delReader.executeUpdate();
                // Commit when all movements have been done
                con.commit();
            } catch (SQLException sqle) {
                try {
                    con.rollback();
                    code = -1;
                } catch (SQLException e) {
                    code = -4;
                }
            }

        } catch (SQLException e) {
            log.error(e.getMessage());
            code = -3;
        } catch (Exception e) {
            log.error(e.getMessage());
            code = -2;
        }
        return code;
    }

    private Either<Integer, List<Reader>> readRS(ResultSet rs) {
        try {
            List<Reader> readers = new ArrayList<>();
            while (rs.next()) {
                int id = rs.getInt(DBConstants.ID);
                String nameReader = rs.getString(DBConstants.NAME_READER);
                LocalDate dateOfBirth = rs.getDate(DBConstants.DATE_OF_BIRTH).toLocalDate();

                readers.add(new Reader(id, nameReader, dateOfBirth));
            }
            return Either.right(readers);
        } catch (SQLException e) {
            return Either.left(-3);
        } catch (Exception e){
            return Either.left(-2);
        }
    }

    @Override
    public int add(Reader newReader) {
        int code = -1;

        try (Connection con = dbConnection.getConnection()) {

            try (PreparedStatement insertReader = con.prepareStatement(SQLQueries.INSERT_INTO_READERS, Statement.RETURN_GENERATED_KEYS);
                 PreparedStatement insertLogin = con.prepareStatement(SQLQueries.INSERT_INTO_LOGIN)) {

                // Disables autocommit
                con.setAutoCommit(false);

                insertReader.setString(1, newReader.getName());
                insertReader.setDate(2, Date.valueOf(newReader.getBirthDate()));
                code = insertReader.executeUpdate();
                ResultSet rs = insertReader.getGeneratedKeys();
                if (rs.next()) {
                    newReader.setId(rs.getInt(1));

                    setLoginParams(newReader, insertLogin);

//                    insertLogin.executeUpdate();
                    code = insertLogin.executeUpdate();
                    // Commit when all movements have been done
                    con.commit();
                }else {
                    // if there is no generated key, the transaction is rolled back
                  con.rollback();
                }
            }
        } catch (SQLException e) {
            // by default is -1
            code = e.getErrorCode();
            if (e.getErrorCode() == 1062) {
                code = -3;
            }
            log.error(e.getMessage());
        } catch (Exception e) {
            log.error(e.getMessage());
            code = -2;
        }
        return code;
    }

    private static void setLoginParams(Reader newReader, PreparedStatement insertLogin) throws SQLException {
        Login login = newReader.getLogin();
        insertLogin.setString(1, login.getUser());
        insertLogin.setString(2, login.getPassword());
        insertLogin.setInt(3, newReader.getId());
    }

    @Override
    public int update(Reader newReader){
        return updateTransaction(newReader);
    }

    // This method will return:
    // >0 -> ROWS UPDATED (EXPECTED 1)
    // -1 -> error with the transaction, rollback done successfully
    // -2 -> error not related to de DB (Exception e)
    // -3 -> error with the transaction connection related to de DB (SQLException e)
    // -4 -> error with the transaction, rollback failed
    private int updateTransaction(Reader reader) {
        int code;

        try (Connection con = dbConnection.getConnection()) {

            try (PreparedStatement updReader = con.prepareStatement(SQLQueries.UPDATE_READERS_SET);
                 PreparedStatement updLogin = con.prepareStatement(SQLQueries.UPDATE_LOGIN_SET)) {

                // Disables autocommit
                con.setAutoCommit(false);

                updReader.setString(1, reader.getName());
                updReader.setDate(2, Date.valueOf(reader.getBirthDate()));
                updReader.setInt(3, reader.getId());

                setLoginParams(reader, updLogin);

                updReader.executeUpdate();
                code = updLogin.executeUpdate();
                // Commit when all movements have been done
                con.commit();
            } catch (SQLException sqle) {
                try {
                    con.rollback();
                    if (sqle.getErrorCode() == 1062) {
                        code = sqle.getErrorCode();
                    } else {
                        code = -1;
                    }
                } catch (SQLException e) {
                    code = -4;
                }
            }

        } catch (SQLException e) {
            log.error(e.getMessage());
            code = -3;
        } catch (Exception e) {
            log.error(e.getMessage());
            code = -2;
        }
        return code;
    }


}
