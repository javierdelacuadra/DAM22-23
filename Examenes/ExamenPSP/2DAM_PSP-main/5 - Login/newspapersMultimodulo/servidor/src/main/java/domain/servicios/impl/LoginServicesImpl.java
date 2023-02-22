package domain.servicios.impl;

import dao.impl.LoginDAOImpl;
import domain.modelo.errores.InvalidFieldsException;
import domain.servicios.LoginServices;
import domain.servicios.common.ServicesConstants;
import jakarta.inject.Inject;
import jakarta.security.enterprise.identitystore.Pbkdf2PasswordHash;
import org.miutils.domain.modelo.Reader;

public class LoginServicesImpl implements LoginServices {

    private final LoginDAOImpl loginDAOImpl;
    private final Pbkdf2PasswordHash passwordHash;

    @Inject
    public LoginServicesImpl(LoginDAOImpl loginDAOImpl, Pbkdf2PasswordHash passwordHash) {
        this.loginDAOImpl = loginDAOImpl;
        this.passwordHash = passwordHash;
    }

    @Override
    public Reader login(String email, String password) {
        return loginDAOImpl.login(email, password);
    }

    @Override
    public Reader register(Reader reader) {
        reader.getLogin().setPassword(passwordHash.generate(reader.getLogin().getPassword().toCharArray()));
        if (loginDAOImpl.add(reader) == 1) {
            return reader;
        } else {
            throw new InvalidFieldsException(ServicesConstants.NO_SE_PUDO_AGREGAR_EL_LECTOR);
        }
    }

    @Override
    public boolean validate(String token){
        return loginDAOImpl.validate(token);
    }

    @Override
    public boolean resetTime(String token){
        return loginDAOImpl.resetTime(token);
    }

    @Override
    public boolean forgot(String email){
        return loginDAOImpl.validForgot(email);
    }

    @Override
    public boolean resetPass(String token, String pass){
        return loginDAOImpl.resetPass(token, passwordHash.generate(pass.toCharArray()));
    }
}
