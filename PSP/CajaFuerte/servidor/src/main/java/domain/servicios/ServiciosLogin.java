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
import modelo.ReaderLogin;
import modelo.Usuario;

public class ServiciosLogin {

    private final DaoLogin daoLogin;
    private final Pbkdf2PasswordHash passwordHash;

    @Inject
    public ServiciosLogin(DaoLogin daoLogin, Pbkdf2PasswordHash passwordHash) {
        this.daoLogin = daoLogin;
        this.passwordHash = passwordHash;
    }

    public Usuario getLogin(String user, char[] pass) {
        Usuario usuario = daoLogin.checkLogin(user);
        if (usuario == null) {
            throw new ObjectNotFoundException(ConstantesServicios.USUARIO_NO_ENCONTRADO);
        } else {
            if (passwordHash.verify(pass, usuario.getPassword())) {
                return usuario;
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

    public Usuario getUsuarioByName(String name) {
        return daoLogin.getUsuarioByName(name);
    }
}
