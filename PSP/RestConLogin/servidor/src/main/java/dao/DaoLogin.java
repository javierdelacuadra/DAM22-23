package dao;

import dao.common.Constantes;
import dao.common.SQLQueries;
import domain.exceptions.DatabaseException;
import domain.exceptions.ObjectNotFoundException;
import jakarta.inject.Inject;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import jakarta.security.enterprise.identitystore.Pbkdf2PasswordHash;
import jakarta.mail.Session;
import model.Reader;
import model.ReaderLogin;

import java.security.SecureRandom;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Properties;
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

    public ReaderLogin getLogin(String user, String pass) {
        ReaderLogin readerLogin = new ReaderLogin();
        try (Connection connection = db.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQLQueries.SELECT_LOGIN)) {
            preparedStatement.setString(1, user);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                if (passwordHash.verify(pass.toCharArray(), rs.getString("password"))) {
                    readerLogin.setId_reader(rs.getInt("id_reader"));
                    readerLogin.setUsername(rs.getString("username"));
                } else {
                    throw new ObjectNotFoundException("Contraseña incorrecta");
                }
            } else {
                throw new ObjectNotFoundException("Usuario o contraseña incorrectos");
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error en la base de datos");
        }
        return readerLogin;
    }

    public ReaderLogin addLogin(ReaderLogin login) {
        Reader reader = save(new Reader(login.getUsername(), LocalDate.now()));
        String activationCode = randomBytes();
        try (Connection connection = db.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQLQueries.INSERT_LOGIN)) {
            preparedStatement.setString(1, login.getUsername());
            preparedStatement.setString(2, passwordHash.generate(login.getPassword().toCharArray()));
            preparedStatement.setString(3, login.getEmail());
            preparedStatement.setInt(4, reader.getId());
            preparedStatement.setString(5, activationCode);
            preparedStatement.setInt(6, 0);
            preparedStatement.setTimestamp(7, Timestamp.valueOf(LocalDateTime.now()));
            preparedStatement.executeUpdate();
            generateAndSendEmail(login.getEmail(), generateActivationMessage(activationCode), "Activación de cuenta");
        } catch (SQLException e) {
            throw new DatabaseException("Ya existe un usuario con ese nombre");
        } catch (MessagingException e) {
            throw new DatabaseException("Error al enviar el correo de activación");
        }
        return login;
    }

    public String generateActivationMessage(String activationCode) {
        return "http://localhost:8080/RestLogin-1.0-SNAPSHOT/activar?code=" + activationCode;
    }

    public String generatePasswordRecoveryMessage(String activationCode) {
        return "http://localhost:8080/RestLogin-1.0-SNAPSHOT/passwordRecovery.jsp?code=" + activationCode;
    }

    public Reader save(Reader reader) {
        try (Connection con = db.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(SQLQueries.INSERT_READER, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, reader.getName());
            preparedStatement.setDate(2, Date.valueOf(reader.getDateOfBirth()));
            preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.getGeneratedKeys();
            if (rs.next()) {
                reader.setId(rs.getInt(1));
            }
            return reader;
        } catch (SQLException ex) {
            Logger.getLogger(DaoReaders.class.getName()).log(Level.SEVERE, null, ex);
            throw new ObjectNotFoundException(Constantes.NO_SE_HA_PODIDO_GUARDAR_EL_READER);
        }
    }

    public String randomBytes() {
        SecureRandom sr = new SecureRandom();
        byte[] bits = new byte[32];
        sr.nextBytes(bits);
        return Base64.getUrlEncoder().encodeToString(bits);
    }

    public void generateAndSendEmail(String to, String msg, String subject) throws MessagingException {
        Properties mailServerProperties;
        Session getMailSession;
        MimeMessage generateMailMessage;

        // Step1

        mailServerProperties = System.getProperties();
        mailServerProperties.put("mail.smtp.port", Integer.parseInt("587"));
        mailServerProperties.put("mail.smtp.auth", "true");
        mailServerProperties.put("mail.smtp.ssl.trust", "smtp.gmail.com");
//        mailServerProperties.put("mail.smtp.ssl.trust", "smtp01.educa.madrid.org");
        mailServerProperties.put("mail.smtp.starttls.enable", "true");

        // Step2

        getMailSession = Session.getDefaultInstance(mailServerProperties, null);
        generateMailMessage = new MimeMessage(getMailSession);
        generateMailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
        generateMailMessage.setSubject(subject);
        String emailBody = msg;
        generateMailMessage.setContent(emailBody, "text/html");


        // Step3

        Transport transport = getMailSession.getTransport("smtp");

        // Enter your correct gmail UserID and Password
        // if you have 2FA enabled then provide App Specific Password
        transport.connect("smtp.gmail.com",
                "alumnosdamquevedo@gmail.com",
                "ayuaklckgxbbooph");

        transport.sendMessage(generateMailMessage, generateMailMessage.getAllRecipients());
        transport.close();
    }

    public void activarUsuario(String code) {
        try (Connection connection = db.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQLQueries.ACTIVATE_USER)) {
            preparedStatement.setString(1, code);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException("Error al activar el usuario");
        }
    }

    public Object passwordRecovery(String email) {
        try (Connection connection = db.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQLQueries.SELECT_USER_BY_EMAIL)) {
            preparedStatement.setString(1, email);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                String activationCode = rs.getString("activation_code");
                generateAndSendEmail(email, generatePasswordRecoveryMessage(activationCode), "Recuperación de contraseña");
            } else {
                throw new ObjectNotFoundException("No existe ningún usuario con ese email");
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error en la base de datos");
        } catch (MessagingException e) {
            throw new DatabaseException("Error al enviar el correo de recuperación");
        }
        return null;
    }

    public Object emailResend(String email) {
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
                String activationCode = rs.getString("activation_code");
                generateAndSendEmail(email, generateActivationMessage(activationCode), "Activación de cuenta");
            } else {
                throw new ObjectNotFoundException("No existe ningún usuario con ese email");
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error en la base de datos");
        } catch (MessagingException e) {
            throw new DatabaseException("Error al enviar el correo de activación");
        }
        return null;
    }

    public void crearNuevaPassword(String password, String code) {
        String passwordHasheada = passwordHash.generate(password.toCharArray());
        try (Connection connection = db.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQLQueries.UPDATE_PASSWORD)) {
            preparedStatement.setString(1, passwordHasheada);
            preparedStatement.setString(2, code);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException("Error al actualizar la contraseña");
        }
    }
}