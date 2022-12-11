package dao;

import dao.common.Constantes;
import dao.common.ConstantesDaoLogin;
import dao.common.SQLQueries;
import domain.exceptions.DatabaseException;
import domain.exceptions.ObjectNotFoundException;
import domain.servicios.MandarMail;
import jakarta.inject.Inject;
import jakarta.mail.MessagingException;
import jakarta.security.enterprise.identitystore.Pbkdf2PasswordHash;
import model.Reader;
import model.ReaderLogin;

import java.security.SecureRandom;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DaoLogin {
    private final DBConnection db;
    private final Pbkdf2PasswordHash passwordHash;

    @Inject
    public DaoLogin(DBConnection db, Pbkdf2PasswordHash passwordHash) {
        this.db = db;
        this.passwordHash = passwordHash;
    }

    public ReaderLogin checkLogin(String user, String pass) {
        ReaderLogin readerLogin = new ReaderLogin();
        try (Connection connection = db.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQLQueries.SELECT_LOGIN)) {
            preparedStatement.setString(1, user);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                if (passwordHash.verify(pass.toCharArray(), rs.getString(ConstantesDaoLogin.PASSWORD))) {
                    if (rs.getInt(ConstantesDaoLogin.ACTIVE) == 0) {
                        throw new ObjectNotFoundException(ConstantesDaoLogin.EL_USUARIO_NO_ESTA_ACTIVADO_REVISA_TU_CORREO);
                    } else {
                        readerLogin.setId_reader(rs.getInt(ConstantesDaoLogin.ID_READER));
                        readerLogin.setUsername(rs.getString(ConstantesDaoLogin.USERNAME));
                    }
                } else {
                    throw new ObjectNotFoundException(ConstantesDaoLogin.PASSWORD_INCORRECTA);
                }
            } else {
                throw new ObjectNotFoundException(ConstantesDaoLogin.USUARIO_O_PASSWORD_INCORRECTOS);
            }
        } catch (SQLException e) {
            throw new DatabaseException(ConstantesDaoLogin.ERROR_EN_LA_BASE_DE_DATOS);
        }
        return readerLogin;
    }

    private String generateUniqueToken() {
        SecureRandom sr = new SecureRandom();
        byte[] bits = new byte[32];
        sr.nextBytes(bits);
        return Base64.getUrlEncoder().encodeToString(bits);
    }

    public ReaderLogin addLogin(ReaderLogin login) {
        Integer readerId = getGeneratedReaderID(new Reader(login.getUsername(), LocalDate.now()));
        if (readerId == -1) {
            throw new DatabaseException(ConstantesDaoLogin.YA_EXISTE_UN_USUARIO_CON_ESE_NOMBRE);
        } else {
            MandarMail mandarMail = new MandarMail();
            String activationCode = generateUniqueToken();
            try (Connection connection = db.getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(SQLQueries.INSERT_LOGIN)) {
                preparedStatement.setString(1, login.getUsername());
                preparedStatement.setString(2, passwordHash.generate(login.getPassword().toCharArray()));
                preparedStatement.setString(3, login.getEmail());
                preparedStatement.setInt(4, readerId);
                preparedStatement.setString(5, activationCode);
                preparedStatement.setInt(6, 0);
                preparedStatement.setTimestamp(7, Timestamp.valueOf(LocalDateTime.now()));
                preparedStatement.executeUpdate();
                mandarMail.generateAndSendEmail(login.getEmail(), generateActivationMessage(activationCode), ConstantesDaoLogin.ACTIVACION_DE_CUENTA);
            } catch (SQLException e) {
                throw new DatabaseException(ConstantesDaoLogin.YA_EXISTE_UN_USUARIO_CON_ESE_EMAIL);
            } catch (MessagingException e) {
                throw new DatabaseException(ConstantesDaoLogin.ERROR_AL_ENVIAR_EL_CORREO_DE_ACTIVACION);
            }
            return login;
        }
    }

    public String generateActivationMessage(String activationCode) {
        return ConstantesDaoLogin.PARA_ACTIVAR_SU_CUENTA_HAGA_CLICK_EN_EL_SIGUIENTE_ENLACE + Constantes.ACTIVATION_URL + activationCode;
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
        MandarMail mandarMail = new MandarMail();
        try (Connection connection = db.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQLQueries.SELECT_USER_BY_EMAIL)) {
            preparedStatement.setString(1, email);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                String activationCode = rs.getString(ConstantesDaoLogin.ACTIVATION_CODE);
                mandarMail.generateAndSendEmail(email, generatePasswordRecoveryMessage(activationCode), ConstantesDaoLogin.RECUPERACION_DE_PASSWORD);
            } else {
                throw new ObjectNotFoundException(ConstantesDaoLogin.NO_EXISTE_NINGUN_USUARIO_CON_ESE_EMAIL);
            }
        } catch (SQLException e) {
            throw new DatabaseException(ConstantesDaoLogin.ERROR_EN_LA_BASE_DE_DATOS);
        } catch (MessagingException e) {
            throw new DatabaseException(ConstantesDaoLogin.ERROR_AL_ENVIAR_EL_CORREO_DE_RECUPERACION);
        }
        return ConstantesDaoLogin.REVISAR_CORREO;
    }

    public String generatePasswordRecoveryMessage(String activationCode) {
        return ConstantesDaoLogin.PARA_RECUPERAR_SU_PASSWORD_HAGA_CLICK_EN_EL_SIGUIENTE_ENLACE + Constantes.PASSWORD_RECOVERY_URL + activationCode;
    }

    public String emailResend(String email) {
        MandarMail mandarMail = new MandarMail();
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
                String activationCode = rs.getString(ConstantesDaoLogin.ACTIVATION_CODE);
                mandarMail.generateAndSendEmail(email, generateActivationMessage(activationCode), ConstantesDaoLogin.ACTIVACION_DE_CUENTA);
            } else {
                throw new ObjectNotFoundException(ConstantesDaoLogin.NO_EXISTE_NINGUN_USUARIO_CON_ESE_EMAIL);
            }
        } catch (SQLException e) {
            throw new DatabaseException(ConstantesDaoLogin.ERROR_EN_LA_BASE_DE_DATOS);
        } catch (MessagingException e) {
            throw new DatabaseException(ConstantesDaoLogin.ERROR_AL_ENVIAR_EL_CORREO_DE_ACTIVACION);
        }
        return ConstantesDaoLogin.CORREO_REENVIADO;
    }

    public void crearNuevaPassword(String password, String code) {
        String passwordHasheada = passwordHash.generate(password.toCharArray());
        try (Connection connection = db.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQLQueries.UPDATE_PASSWORD)) {
            preparedStatement.setString(1, passwordHasheada);
            preparedStatement.setString(2, code);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException(ConstantesDaoLogin.ERROR_AL_ACTUALIZAR_LA_PASSWORD);
        }
    }
}