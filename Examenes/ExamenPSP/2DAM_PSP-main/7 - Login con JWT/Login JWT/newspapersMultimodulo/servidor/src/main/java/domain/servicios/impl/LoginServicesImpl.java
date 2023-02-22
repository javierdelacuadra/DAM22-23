package domain.servicios.impl;

import dao.common.DAOConstants;
import dao.impl.LoginDAOImpl;
import dao.impl.SendEmail;
import domain.modelo.errores.InvalidFieldsException;
import domain.modelo.errores.OtherErrorException;
import domain.servicios.LoginServices;
import domain.servicios.common.ServicesConstants;
import jakarta.inject.Inject;
import jakarta.mail.MessagingException;
import jakarta.security.enterprise.identitystore.Pbkdf2PasswordHash;
import org.miutils.common.AplicationConstants;
import org.miutils.domain.modelo.Reader;

import java.time.LocalDate;
import java.util.List;

public class LoginServicesImpl implements LoginServices {

    private final LoginDAOImpl loginDAOImpl;
    private final Pbkdf2PasswordHash passwordHash;

    private final SendEmail sendEmail;

    @Inject
    public LoginServicesImpl(LoginDAOImpl loginDAOImpl, Pbkdf2PasswordHash passwordHash, SendEmail sendEmail) {
        this.loginDAOImpl = loginDAOImpl;
        this.passwordHash = passwordHash;
        this.sendEmail = sendEmail;
    }

    @Override
    public Reader login(String email, String password) {
        if (email.isEmpty() || password.isEmpty()) {
            throw new InvalidFieldsException(AplicationConstants.COMPLETA_TODOS_LOS_CAMPOS);
        }
        return loginDAOImpl.login(email, password);
    }

    @Override
    public Reader register(Reader reader) {
        validations(reader);
        reader.getLogin().setPassword(passwordHash.generate(reader.getLogin().getPassword().toCharArray()));
        String token = loginDAOImpl.add(reader);
        if (token != null) {
            try {
                sendEmail.generateAndSendEmail(reader.getLogin().getEmail(), ServicesConstants.MAIL_BIENVENIDA.formatted(token, token), ServicesConstants.BIENVENIDO_A_LOS_NEWSPAPERS);
                return reader;
            } catch (MessagingException e) {
                throw new OtherErrorException(ServicesConstants.NO_SE_PUDO_ENVIAR_EL_MAIL_A + reader.getLogin().getEmail());
            }
        } else {
            throw new InvalidFieldsException(ServicesConstants.NO_SE_PUDO_AGREGAR_EL_LECTOR);
        }
    }

    @Override
    public boolean validate(String token){
        return loginDAOImpl.validate(token);
    }

    @Override
    public boolean resetTime(String token) {
        String email = loginDAOImpl.resetTime(token);
        try {
            if (email != null){
                sendEmail.generateAndSendEmail(email,ServicesConstants.MAIL_BIENVENIDA.formatted(token, token), ServicesConstants.RE_VALIDAR_USER_NEWSPAPERS);
            }
        } catch (MessagingException e) {
            throw new OtherErrorException(ServicesConstants.NO_SE_PUDO_ENVIAR_EL_MAIL_A + email);
        }

        return email != null;
    }

    @Override
    public boolean forgot(String email){
        String token = loginDAOImpl.validForgot(email);
        if (token != null){
            try {
                sendEmail.generateAndSendEmail(email, ServicesConstants.MAIL_RESET_PASS.formatted(token), DAOConstants.CAMBIA_TU_CONTRASENA_NEWSPAPERS);
                return true;
            } catch (MessagingException e) {
                throw new OtherErrorException(ServicesConstants.NO_SE_PUDO_ENVIAR_EL_MAIL_A + email);
            }
        } else {
            throw new InvalidFieldsException(ServicesConstants.NO_EXISTE_EL_USUARIO_CON_EL_EMAIL + email);
        }
    }

    @Override
    public boolean resetPass(String token, String pass){
        return loginDAOImpl.resetPass(token, passwordHash.generate(pass.toCharArray()));
    }

    @Override
    public List<String> getRoles(int id){
        return loginDAOImpl.getRoles(id);
    }

    private void validations(Reader newReader) {
        if (newReader.getName().equals("") || newReader.getBirthDate() == null || newReader.getLogin().getEmail().equals("") || newReader.getLogin().getPassword().equals("")) {
            throw new InvalidFieldsException(ServicesConstants.PLEASE_FILL_ALL_THE_FIELDS);
        } else if (newReader.getBirthDate().isAfter(LocalDate.now().minusYears(1))) {
            throw new InvalidFieldsException(ServicesConstants.THE_NEW_READER_MUST_BE_AT_LEAST_1_YEARS_OLD);
        } else if (newReader.getLogin().getPassword().length() > 15) {
            throw new InvalidFieldsException(ServicesConstants.REMEMBER_THAT_THE_CREDENTIALS_CAN_NOT_BE_LONGER_THAN_15_CHARACTERS);
        }
    }

    @Override
    public boolean hasPetitionsLeft(String email){
        return loginDAOImpl.changePetitonsStatus(email);
    }
}
