package domain.servicios.impl;

import dao.impl.LoginDAOImpl;
import domain.modelo.Reader;
import domain.servicios.LoginServices;
import jakarta.inject.Inject;

public class LoginServicesImpl implements LoginServices {

    private final LoginDAOImpl loginDAOImpl;

    @Inject
    public LoginServicesImpl(LoginDAOImpl loginDAOImpl) {
        this.loginDAOImpl = loginDAOImpl;
    }

    @Override
    public Reader login(String user, String password) {
        return loginDAOImpl.login(user, password);
    }
}
