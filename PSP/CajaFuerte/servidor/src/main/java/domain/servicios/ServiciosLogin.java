package domain.servicios;

import dao.DaoLogin;
import domain.exceptions.EmailServicesFailedException;
import domain.exceptions.ObjectNotFoundException;
import domain.servicios.common.ConstantesServicios;
import domain.utils.GenerateToken;
import domain.utils.MandarMail;
import domain.utils.MessageGenerator;
import jakarta.inject.Inject;
import jakarta.mail.MessagingException;
import jakarta.security.enterprise.identitystore.Pbkdf2PasswordHash;
import model.ReaderLogin;

public class ServiciosLogin {

    private final DaoLogin daoLogin;
    private final Pbkdf2PasswordHash passwordHash;

    @Inject
    public ServiciosLogin(DaoLogin daoLogin, Pbkdf2PasswordHash passwordHash) {
        this.daoLogin = daoLogin;
        this.passwordHash = passwordHash;
    }

    public ReaderLogin getLogin(String user, char[] pass) {
        ReaderLogin readerLogin = daoLogin.checkLogin(user);
        if (readerLogin == null) {
            throw new ObjectNotFoundException(ConstantesServicios.USUARIO_NO_ENCONTRADO);
        } else if (readerLogin.getActive() == 0) {
            throw new ObjectNotFoundException(ConstantesServicios.EL_USUARIO_NO_ESTA_ACTIVADO_REVISA_TU_CORREO);
        } else {
            if (passwordHash.verify(pass, readerLogin.getPassword())) {
                return readerLogin;
            } else {
                throw new ObjectNotFoundException(ConstantesServicios.PASSWORD_INCORRECTA);
            }
        }
    }

    public ReaderLogin addLogin(ReaderLogin login) {
        login.setPassword(passwordHash.generate(login.getPassword().toCharArray()));
        GenerateToken generateToken = new GenerateToken();
        login.setActivation_code(generateToken.generateUniqueToken());
        ReaderLogin readerLogin = daoLogin.addLogin(login);
        MandarMail mandarMail = new MandarMail();
        MessageGenerator messageGenerator = new MessageGenerator();
        try {
            mandarMail.generateAndSendEmail(login.getEmail(), messageGenerator.generateActivationMessage(login.getActivation_code()), ConstantesServicios.ACTIVACION_DE_CUENTA);
        } catch (MessagingException e) {
            throw new EmailServicesFailedException(ConstantesServicios.ERROR_AL_ENVIAR_EL_CORREO_DE_ACTIVACION);
        }
        return readerLogin;
    }

    public void activarUsuario(String code) {
        daoLogin.activarUsuario(code);
    }

    public void passwordRecovery(String email) {
        String activationCode = daoLogin.passwordRecovery(email);
        MandarMail mandarMail = new MandarMail();
        MessageGenerator messageGenerator = new MessageGenerator();
        try {
            mandarMail.generateAndSendEmail(email, messageGenerator.generatePasswordRecoveryMessage(activationCode), ConstantesServicios.RECUPERACION_DE_PASSWORD);
        } catch (MessagingException e) {
            throw new EmailServicesFailedException(ConstantesServicios.ERROR_AL_ENVIAR_EL_CORREO_DE_RECUPERACION);
        }
    }

    public void emailResend(String email) {
        String activationCode = daoLogin.emailResend(email);
        MandarMail mandarMail = new MandarMail();
        MessageGenerator messageGenerator = new MessageGenerator();
        try {
            mandarMail.generateAndSendEmail(email, messageGenerator.generateActivationMessage(activationCode), ConstantesServicios.ACTIVACION_DE_CUENTA);
        } catch (MessagingException e) {
            throw new EmailServicesFailedException(ConstantesServicios.ERROR_AL_ENVIAR_EL_CORREO_DE_ACTIVACION);
        }
    }

    public void crearNuevaPassword(String password, String code) {
        String passwordHasheada = passwordHash.generate(password.toCharArray());
        daoLogin.crearNuevaPassword(passwordHasheada, code);
    }
}
