package dao.impl;

import dao.LoginDAO;
import dao.common.DAOConstants;
import dao.common.DBConstants;
import dao.common.SQLQueries;
import domain.modelo.errores.*;
import jakarta.inject.Inject;
import jakarta.security.enterprise.identitystore.Pbkdf2PasswordHash;
import org.miutils.domain.modelo.Login;
import org.miutils.domain.modelo.Reader;

import java.security.SecureRandom;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class LoginDAOImpl implements LoginDAO {

    private final DBConnection dbConnection;
    private final Pbkdf2PasswordHash passwordHash;


    @Inject
    public LoginDAOImpl(DBConnection dbConnection, Pbkdf2PasswordHash passwordHash) {
        this.dbConnection = dbConnection;
        this.passwordHash = passwordHash;
    }

    @Override
    public Reader login(String email, String password) {
        try (Connection con = dbConnection.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(SQLQueries.LOGIN_READER)) {
            preparedStatement.setString(1, email);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                int id = rs.getInt(DBConstants.ID);
                String name = rs.getString(DBConstants.NAME_READER);
                LocalDate birthDate = rs.getDate(DBConstants.DATE_OF_BIRTH).toLocalDate();

                if (passwordHash.verify(password.toCharArray(), rs.getString(DBConstants.PASSWORD))) {
                    // user valido
                    if (rs.getBoolean(DBConstants.ACTIVATED)) {
                        // user activo
                        return new Reader(id, name, birthDate);
                    } else {
                        // user inactivo
                        throw new InvalidLoginException(DAOConstants.USER_INACTIVE);
                    }
                } else {
                    // user invalido
                    throw new InvalidLoginException(DAOConstants.INVALID_USER_OR_PASSWORD);
                }
            } else {
                throw new NotFoundException(DAOConstants.NOT_REGISTERED_USER);
            }
        } catch (NotFoundException | InvalidLoginException e) {
            throw e;
        } catch (SQLException e) {
            throw new BaseDeDatosException(e.getMessage());
        } catch (Exception e) {
            throw new OtherErrorException(e.getMessage());
        }
    }

    @Override
    public boolean validate(String token) {
        try (Connection con = dbConnection.getConnection()) {
            return validateLogin(token, con);
        } catch (SQLException e) {
            // las otras excepciones se lanzan en el try de dentro
            throw new BaseDeDatosException(e.getMessage());
        }
    }

    @Override
    public String resetTime(String token) {
        try (Connection con = dbConnection.getConnection()) {
            return resetTime(token, con);
        } catch (SQLException e) {
            // las otras excepciones se lanzan en el try de dentro
            throw new BaseDeDatosException(e.getMessage());
        }
    }

    private String resetTime(String token, Connection con) {
        String mailAResetear;
        try (PreparedStatement preparedStatement = con.prepareStatement(SQLQueries.GET_VALIDATED_FECHA_FROM_LOGIN);
             PreparedStatement updateValido = con.prepareStatement(SQLQueries.SET_FECHA_WHERE_TOKEN)) {
            con.setAutoCommit(false);
            preparedStatement.setString(1, token);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                boolean valido = rs.getBoolean(1);
                if (!valido) {
                    // no se ha validado aún, comprobamos cuanto tardo en responder el mail
                    LocalDateTime fechaRegistro = (LocalDateTime) rs.getObject(2);
                    if (LocalDateTime.now().isAfter(fechaRegistro.plusMinutes(5))) {
                        // hora valida, se valida user
                        //el email por si hay un token duplicado
                        mailAResetear = rs.getString(3);
                        updateValido.setObject(1, LocalDateTime.now());
                        updateValido.setString(2, mailAResetear);
                        updateValido.executeUpdate();
                    } else {
                        // hora no valida, que lo haga de vuelta
                        throw new InvalidLoginException(DAOConstants.AUN_NO_HA_CADUCADO_EL_TIEMPO_PARA_VALIDAR_ESTA_CUENTA_PRUEBA_A_VALIDARTE_DESDE_TU_MAIL);
                    }
                } else {
                    // el user ya se habia validado, para que vino?
                    throw new InvalidLoginException(DAOConstants.EL_USUARIO_YA_HA_SIDO_VALIDADO_PREVIAMENTE_DEBERIAS_PODER_HACER_LOGIN_NORMALMENTE);
                }
            } else {
                throw new NotFoundException(DAOConstants.NO_SE_ENCONTRO_UN_USUARIO_CON_ESE_TOKEN_ESTAS_SEGURO_QUE_ES_CORRECTO);
            }
            con.commit();
            return mailAResetear;
        } catch (NotFoundException | InvalidLoginException e) {
            throw e;
        } catch (SQLException e) {
            throw new BaseDeDatosException(e.getMessage());
        } catch (Exception e) {
            throw new OtherErrorException(e.getMessage());
        }
    }

    private boolean validateLogin(String token, Connection con) {
        try (PreparedStatement preparedStatement = con.prepareStatement(SQLQueries.GET_VALIDATED_FECHA_FROM_LOGIN);
             PreparedStatement updateValido = con.prepareStatement(SQLQueries.SET_ACTIVATED_TRUE_WHERE_TOKEN)) {
            con.setAutoCommit(false);
            preparedStatement.setString(1, token);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                boolean valido = rs.getBoolean(1);
                if (!valido) {
                    // no se ha validado aún, comprobamos cuanto tardo en responder el mail
                    LocalDateTime fechaRegistro = (LocalDateTime) rs.getObject(2);
                    if (LocalDateTime.now().isBefore(fechaRegistro.plusMinutes(5))) {
                        // hora valida, se valida user
                        //el email por si hay un token duplicado
                        String email = rs.getString(3);
                        updateValido.setString(1, email);
                        updateValido.executeUpdate();
                    } else {
                        // hora no valida, que lo haga de vuelta
                        throw new InvalidLoginException(DAOConstants.EL_TIEMPO_PARA_VALIDAR_ESTA_CUENTA_HA_CADUCADO_VUELVE_A_GENERAR_LA_CONTRASENA);
                    }
                } else {
                    // el user ya se habia validado, para que vino?
                    throw new InvalidLoginException(DAOConstants.EL_USUARIO_YA_HA_SIDO_VALIDADO_PREVIAMENTE_DEBERIAS_PODER_HACER_LOGIN_NORMALMENTE);
                }
            } else {
                throw new NotFoundException(DAOConstants.NO_SE_ENCONTRO_UN_USUARIO_CON_ESE_TOKEN_ESTAS_SEGURO_QUE_ES_CORRECTO);
            }
            con.commit();
            return true;
        } catch (NotFoundException | InvalidLoginException e) {
            throw e;
        } catch (SQLException e) {
            throw new BaseDeDatosException(e.getMessage());
        } catch (Exception e) {
            throw new OtherErrorException(e.getMessage());
        }
    }

    @Override
    public String add(Reader newReader) {
        String token;
        try (Connection con = dbConnection.getConnection()) {

            try (PreparedStatement insertReader = con.prepareStatement(SQLQueries.INSERT_INTO_READERS, Statement.RETURN_GENERATED_KEYS);
                 PreparedStatement insertLogin = con.prepareStatement(SQLQueries.INSERT_INTO_LOGIN);
                 PreparedStatement insertRoles = con.prepareStatement(SQLQueries.INSERT_INTO_ROLES)) {

                // Disables autocommit
                con.setAutoCommit(false);

                insertReader.setString(1, newReader.getName());
                insertReader.setDate(2, Date.valueOf(newReader.getBirthDate()));
                insertReader.executeUpdate();
                ResultSet rs = insertReader.getGeneratedKeys();
                if (rs.next()) {
                    newReader.setId(rs.getInt(1));
                    token = getToken();
                    setLoginParams(newReader, insertLogin, token);
                    insertLogin.executeUpdate();
                    insertRoles.setInt(1, newReader.getId());
                    insertRoles.setInt(2, 2);
                    insertRoles.executeUpdate();
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
            if (e.getErrorCode() == 1062) {
                throw new DataIntegrityException(DAOConstants.YA_EXISTE_UN_READER_CON_ESE_MAIL);
            } else {
                throw new BaseDeDatosException(e.getMessage());
            }
        }catch (DataIntegrityException e){
            throw e;
        }
        catch (Exception e) {
            throw new OtherErrorException(e.getMessage());
        }
        return token;
    }

    private void setLoginParams(Reader newReader, PreparedStatement insertLogin, String token) throws SQLException {
        Login login = newReader.getLogin();
        insertLogin.setString(1, login.getEmail());
        insertLogin.setString(2, login.getPassword());
        insertLogin.setString(3, token);
        insertLogin.setObject(4, LocalDateTime.now());
        insertLogin.setInt(5, newReader.getId());
    }

    private String getToken() {
        SecureRandom sr = new SecureRandom();
        byte[] bits = new byte[32];
        sr.nextBytes(bits);
        return Base64.getUrlEncoder().encodeToString(bits);
    }

    @Override
    public String validForgot(String email) {
        try (Connection con = dbConnection.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(SQLQueries.GET_TOKEN_BY_EMAIL)) {
            preparedStatement.setString(1, email);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()){
                //hay un usuario con ese email, le enviamos un mail con el token para cambiar la contraseña
                return rs.getString(1);
            }
            throw new NotFoundException(DAOConstants.NO_SE_ENCONTRO_UN_USUARIO_CON_ESE_EMAIL_ESTAS_SEGURO_QUE_ES_CORRECTO);
        } catch (NotFoundException e) {
            throw e;
        } catch (SQLException e) {
            throw new BaseDeDatosException(e.getMessage());
        } catch (Exception e) {
            throw new OtherErrorException(e.getMessage());
        }
    }


    @Override
    public boolean resetPass(String token, String pass){
        // guardamos la nueva password en la fila con el token correspondiente
        try(Connection con = dbConnection.getConnection();
            PreparedStatement updPass = con.prepareStatement(SQLQueries.UPDATE_PASS_BY_TOKEN))   {
            updPass.setString(1, pass);
            updPass.setString(2, token);
            int affectedRows = updPass.executeUpdate();
            if (affectedRows == 0){
                throw new NotFoundException(DAOConstants.NO_SE_ENCONTRO_UN_USUARIO_CON_ESE_TOKEN_ESTAS_SEGURO_QUE_ES_CORRECTO);
            }
            return true;
        } catch (SQLException e) {
            throw new BaseDeDatosException(e.getMessage());
        } catch (Exception e) {
            throw new OtherErrorException(e.getMessage());
        }
    }

    @Override
    public List<String> getRoles(int idReader){
        List<String> roles = new ArrayList<>();
        try(Connection con = dbConnection.getConnection();
            PreparedStatement preparedStatement = con.prepareStatement(SQLQueries.GET_ROLES_BY_ID_READER))   {
            preparedStatement.setInt(1, idReader);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()){
                roles.add(rs.getString(1));
            }
            return roles;
        } catch (SQLException e) {
            throw new BaseDeDatosException(e.getMessage());
        } catch (Exception e) {
            throw new OtherErrorException(e.getMessage());
        }
    }

}
