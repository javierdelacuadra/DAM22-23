package dao;

import dao.common.ConstantesDaoLogin;
import dao.common.SQLQueries;
import domain.exceptions.DatabaseException;
import domain.exceptions.ObjectAlreadyExistsException;
import domain.exceptions.ObjectNotFoundException;
import jakarta.inject.Inject;
import modelo.Reader;
import modelo.ReaderLogin;
import modelo.Usuario;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DaoLogin {
    private final DBConnection db;

    @Inject
    public DaoLogin(DBConnection db) {
        this.db = db;
    }

    public Usuario checkLogin(String username) {
        List<Usuario> usuarios;
        try {
            String query = SQLQueries.SELECT_USER_BY_NAME;
            JdbcTemplate jdbc = new JdbcTemplate(db.getHikariDataSource());
            usuarios = jdbc.query(query, BeanPropertyRowMapper.newInstance(Usuario.class), username);
            return usuarios.isEmpty() ? null : usuarios.get(0);
        } catch (DataAccessException e) {
            throw new DatabaseException(ConstantesDaoLogin.ERROR_EN_LA_BASE_DE_DATOS);
        }
    }

    public ReaderLogin addLogin(ReaderLogin login) {
        Integer readerId = getGeneratedReaderID(new Reader(login.getUsername(), LocalDate.now()));
        if (readerId == -1) {
            throw new ObjectAlreadyExistsException(ConstantesDaoLogin.YA_EXISTE_UN_USUARIO_CON_ESE_NOMBRE);
        } else {
            try (Connection connection = db.getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(SQLQueries.INSERT_LOGIN)) {
                preparedStatement.setString(1, login.getUsername());
                preparedStatement.setString(2, login.getPassword());
                preparedStatement.setString(3, login.getEmail());
                preparedStatement.setInt(4, readerId);
                preparedStatement.setString(5, login.getActivation_code());
                preparedStatement.setInt(6, 0);
                preparedStatement.setTimestamp(7, Timestamp.valueOf(LocalDateTime.now()));
                preparedStatement.setString(8, ConstantesDaoLogin.ROLE_USER);
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                throw new ObjectAlreadyExistsException(ConstantesDaoLogin.YA_EXISTE_UN_USUARIO_CON_ESE_EMAIL);
            }
            return login;
        }
    }

    private Integer getGeneratedReaderID(Reader reader) {
        int idReader = 0;
        try (Connection con = db.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(SQLQueries.INSERT_READER, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, reader.getName());
            preparedStatement.setDate(2, Date.valueOf(reader.getDateOfBirth()));
            preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.getGeneratedKeys();
            if (rs.next()) {
                idReader = rs.getInt(1);
            }
            return idReader;
        } catch (SQLException ex) {
            Logger.getLogger(DaoReaders.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        }
    }

    public void activarUsuario(String code) {
        try (Connection connection = db.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQLQueries.ACTIVATE_USER)) {
            preparedStatement.setString(1, code);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException(ConstantesDaoLogin.ERROR_AL_ACTIVAR_EL_USUARIO);
        }
    }

    public String passwordRecovery(String email) {
        String activationCode;
        try (Connection connection = db.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQLQueries.SELECT_USER_BY_EMAIL)) {
            preparedStatement.setString(1, email);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                activationCode = rs.getString(ConstantesDaoLogin.ACTIVATION_CODE);
            } else {
                throw new ObjectNotFoundException(ConstantesDaoLogin.NO_EXISTE_NINGUN_USUARIO_CON_ESE_EMAIL);
            }
        } catch (SQLException e) {
            throw new DatabaseException(ConstantesDaoLogin.ERROR_EN_LA_BASE_DE_DATOS);
        }
        return activationCode;
    }

    public String emailResend(String email) {
        String activationCode;
        try (Connection connection = db.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQLQueries.SELECT_USER_BY_EMAIL)) {
            preparedStatement.setString(1, email);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                try (PreparedStatement preparedStatement2 = connection.prepareStatement(SQLQueries.UPDATE_REGISTER_DATE)) {
                    preparedStatement2.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now()));
                    preparedStatement2.setString(2, email);
                    preparedStatement2.executeUpdate();
                }
                activationCode = rs.getString(ConstantesDaoLogin.ACTIVATION_CODE);
            } else {
                throw new ObjectNotFoundException(ConstantesDaoLogin.NO_EXISTE_NINGUN_USUARIO_CON_ESE_EMAIL);
            }
        } catch (SQLException e) {
            throw new DatabaseException(ConstantesDaoLogin.ERROR_EN_LA_BASE_DE_DATOS);
        }
        return activationCode;
    }

    public void crearNuevaPassword(String password, String code) {
        try (Connection connection = db.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQLQueries.UPDATE_PASSWORD)) {
            preparedStatement.setString(1, password);
            preparedStatement.setString(2, code);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException(ConstantesDaoLogin.ERROR_AL_ACTUALIZAR_LA_PASSWORD);
        }
    }

    public Usuario getUsuarioByName(String name) {
        Usuario usuario;
        try (Connection connection = db.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQLQueries.SELECT_USER_BY_NAME)) {
            preparedStatement.setString(1, name);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                usuario = new Usuario(rs.getInt("id"), rs.getString("nombre"), rs.getString("password"), rs.getString("rol"));
            } else {
                throw new ObjectNotFoundException("No existe ningún usuario con ese nombre");
            }
        } catch (SQLException e) {
            throw new DatabaseException(ConstantesDaoLogin.ERROR_EN_LA_BASE_DE_DATOS);
        }
        return usuario;
    }
}