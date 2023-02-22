package dao.impl;

import dao.ReadersDAO;
import dao.common.DAOConstants;
import dao.common.DBConstants;
import dao.common.SQLQueries;
import domain.modelo.Login;
import domain.modelo.Reader;
import domain.modelo.errores.BaseDeDatosException;
import domain.modelo.errores.NotFoundException;
import domain.modelo.errores.OtherErrorException;
import jakarta.inject.Inject;
import lombok.extern.log4j.Log4j2;

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

    @Override
    public List<Reader> getAll() {
        try (Connection con = dbConnection.getConnection();
             Statement statement = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                     ResultSet.CONCUR_READ_ONLY)) {

            ResultSet rs = statement.executeQuery(SQLQueries.SELECT_ALL_READERS_WITH_LOGIN);
            List<Reader> readers = new ArrayList<>();
            while (rs.next()) {
                int id = rs.getInt(DBConstants.ID);
                String nameReader = rs.getString(DBConstants.NAME_READER);
                LocalDate dateOfBirth = rs.getDate(DBConstants.DATE_OF_BIRTH).toLocalDate();
                String user = rs.getString(DBConstants.USER);
                String password = rs.getString(DBConstants.PASSWORD);
                readers.add(new Reader(id, nameReader, dateOfBirth, new Login(user, password, id)));
            }
            return readers;
        } catch (SQLException e) {
            throw new BaseDeDatosException(e.getMessage());
        } catch (Exception e) {
            throw new OtherErrorException(e.getMessage());
        }
    }

    @Override
    public List<Reader> getAllByType(String nameType) {
        return getAllWithOneParameter(SQLQueries.SELECT_ALL_READERS_BY_TYPE_NAME, nameType);
    }

    private List<Reader> getAllWithOneParameter(String query, String parameter) {
        try (Connection con = dbConnection.getConnection();
             // EL DUPLICADO ESTA EN EL DAO DE NEWSPAPERS, NO TIENE MUCHO SENTIDO CREAR UNA CLASE NUEVA POR ESTE METODO
             PreparedStatement prpstatement = con.prepareStatement(query)) {
            prpstatement.setString(1, parameter);
            ResultSet rs = prpstatement.executeQuery();
            return readRS(rs);
        } catch (SQLException e) {
            throw new BaseDeDatosException(e.getMessage());
        } catch (Exception e) {
            throw new OtherErrorException(e.getMessage());
        }
    }

    @Override
    public List<Reader> getAll(String idNews) {
        return getAllWithOneParameter(SQLQueries.SELECT_ALL_READERS_BY_NEWSPAPER, idNews);
    }

    @Override
    public List<String> getNameOldestSuscriptors(String idNews) {
        return getListOfReadersNamesQuery(idNews);
    }

    private List<String> getListOfReadersNamesQuery(String idNews) {
        try (Connection con = dbConnection.getConnection();
             PreparedStatement prpstatement = con.prepareStatement(SQLQueries.GET_TOP_5_OLDEST_SUSCRIPTOR)) {
            prpstatement.setString(1, idNews);
            ResultSet rs = prpstatement.executeQuery();
            List<String> names = new ArrayList<>();
            while (rs.next()) {
                names.add(rs.getString(DBConstants.NAME_READER));
            }
            return names;
        } catch (SQLException e) {
            throw new BaseDeDatosException(e.getMessage());
        } catch (Exception e) {
            throw new OtherErrorException(e.getMessage());
        }
    }


    @Override
    public Reader get(String id) {
        List<Reader> readers;
        try (Connection con = dbConnection.getConnection();
             PreparedStatement prpStatement = con.prepareStatement(SQLQueries.SELECT_READER_BY_ID)) {
            prpStatement.setString(1, id);
            ResultSet rs = prpStatement.executeQuery();
            readers = readRS(rs);
            if (readers.isEmpty()) {
                throw new NotFoundException(DAOConstants.NO_SE_HA_ENCONTRADO_EL_LECTOR_CON_ID + id);
            }
            return readers.get(0);
        } catch (SQLException e) {
            throw new BaseDeDatosException(e.getMessage());
        }
    }


    @Override
    public int delete(String id) {
        if (deleteQuery(id) < 1) {
            throw new NotFoundException(DAOConstants.NO_SE_HA_ENCONTRADO_EL_LECTOR_CON_ID + id + DAOConstants.PARA_ELIMINARLO);
        } else {
            return 1;
        }
    }

    private int deleteQuery(String id) {
        int code;
        try (Connection con = dbConnection.getConnection();
             PreparedStatement prpStatement = con.prepareStatement(SQLQueries.DELETE_FROM_READERS_BY_ID)) {
            prpStatement.setString(1, id);
            code = prpStatement.executeUpdate();
        } catch (SQLException e) {
            if (e.getErrorCode() != 1451) {
                throw new BaseDeDatosException(e.getMessage());
            } else {
                // Integrity error. Borramos las otras tablas primero.
                code = deleteTransaction(id);
            }
        } catch (Exception e) {
            throw new OtherErrorException(e.getMessage());
        }
        if (code < 1) {
            throw new NotFoundException(DAOConstants.NO_SE_HA_ENCONTRADO_EL_LECTOR_CON_ID + id + DAOConstants.PARA_ELIMINARLO);
        }
        return code;
    }

    private int deleteTransaction(String id) {
        int code;

        try (Connection con = dbConnection.getConnection()) {

            code = tryDeleteTransaction(id, con);

        } catch (SQLException e) {
            throw new BaseDeDatosException(e.getMessage());
        } catch (Exception e) {
            throw new OtherErrorException(e.getMessage());
        }
        return code;
    }

    private int tryDeleteTransaction(String id, Connection con) {
        int code;
        try (PreparedStatement delSB = con.prepareStatement(SQLQueries.DELETE_FROM_SUBSCRIPTIONS_WHERE_ID_READER);
             PreparedStatement delRA = con.prepareStatement(SQLQueries.DELETE_FROM_READARTICLES_WHERE_ID_READER);
             PreparedStatement delLog = con.prepareStatement(SQLQueries.DELETE_FROM_LOGIN_WHERE_ID_READER);
             PreparedStatement delReader = con.prepareStatement(SQLQueries.DELETE_FROM_READERS_BY_ID)) {

            // Disables autocommit
            con.setAutoCommit(false);

            delRA.setString(1, id);
            delSB.setString(1, id);
            delLog.setString(1, id);
            delReader.setString(1, id);

            delRA.executeUpdate();
            delSB.executeUpdate();
            delLog.executeUpdate();
            code = delReader.executeUpdate();
            // Commit when all movements have been done
            con.commit();
        } catch (SQLException sqle) {
            code = tryRollback(con);
        }
        return code;
    }

    private int tryRollback(Connection con) {
        int code;
        try {
            con.rollback();
            code = -1;
        } catch (SQLException e) {
            throw new BaseDeDatosException(e.getMessage());
        }
        return code;
    }

    private List<Reader> readRS(ResultSet rs) {
        try {
            List<Reader> readers = new ArrayList<>();
            while (rs.next()) {
                int id = rs.getInt(DBConstants.ID);
                String nameReader = rs.getString(DBConstants.NAME_READER);
                LocalDate dateOfBirth = rs.getDate(DBConstants.DATE_OF_BIRTH).toLocalDate();
                readers.add(new Reader(id, nameReader, dateOfBirth));
            }
            return readers;
        } catch (SQLException e) {
            throw new BaseDeDatosException(e.getMessage());
        } catch (Exception e) {
            throw new OtherErrorException(e.getMessage());
        }
    }

    @Override
    public int add(Reader newReader) {
        int code;
        try (Connection con = dbConnection.getConnection()) {

            try (PreparedStatement insertReader = con.prepareStatement(SQLQueries.INSERT_INTO_READERS, Statement.RETURN_GENERATED_KEYS);
                 PreparedStatement insertLogin = con.prepareStatement(SQLQueries.INSERT_INTO_LOGIN)) {

                // Disables autocommit
                con.setAutoCommit(false);

                insertReader.setString(1, newReader.getName());
                insertReader.setDate(2, Date.valueOf(newReader.getBirthDate()));
                insertReader.executeUpdate();
                ResultSet rs = insertReader.getGeneratedKeys();
                if (rs.next()) {
                    newReader.setId(rs.getInt(1));
                    setLoginParams(newReader, insertLogin);
                    code = insertLogin.executeUpdate();
                    newReader.getLogin().setIdReader(newReader.getId());
                    // Commit when all movements have been done
                    con.commit();
                } else {
                    // if there is no generated key, the transaction is rolled back
                    con.rollback();
                    throw new BaseDeDatosException(DAOConstants.NO_SE_HA_PODIDO_CREAR_EL_LECTOR);
                }
            }
        } catch (SQLException e) {
            throw new BaseDeDatosException(e.getMessage());
        } catch (Exception e) {
            throw new OtherErrorException(e.getMessage());
        }
        return code;
    }

    private void setLoginParams(Reader newReader, PreparedStatement insertLogin) throws SQLException {
        Login login = newReader.getLogin();
        insertLogin.setString(1, login.getUser());
        insertLogin.setString(2, login.getPassword());
        insertLogin.setInt(3, newReader.getId());
    }

    @Override
    public Reader update(Reader newReader) {
        if (updateTransaction(newReader) == 1) {
            return newReader;
        } else {
            throw new NotFoundException(DAOConstants.NO_EXISTE_UN_LECTOR_CON_EL_ID + newReader.getId());
        }
    }

    private int updateTransaction(Reader reader) {
        try (Connection con = dbConnection.getConnection()) {
            return transaccionValida(reader, con);
        } catch (SQLException e) {
            throw new BaseDeDatosException(e.getMessage());
        } catch (Exception e) {
            throw new OtherErrorException(e.getMessage());
        }
    }

    private int transaccionValida(Reader reader, Connection con) {
        int code = 0;
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
            reader.getLogin().setIdReader(reader.getId());
            // Commit when all movements have been done
            con.commit();

        } catch (SQLException sqle) {
            doRollback(con);
        }
        return code;
    }

    private void doRollback(Connection con) {
        try {
            con.rollback();
        } catch (SQLException e) {
            throw new BaseDeDatosException(e.getMessage());
        }

    }

}
