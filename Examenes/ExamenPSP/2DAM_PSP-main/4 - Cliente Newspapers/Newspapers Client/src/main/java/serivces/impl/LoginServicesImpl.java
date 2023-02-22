package serivces.impl;

import dao.impl.LoginDAOImpl;
import model.Reader;
import serivces.LoginServices;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import serivces.common.ServicesConstants;

public class LoginServicesImpl implements LoginServices {

    private LoginDAOImpl loginDAOImpl;

    @Inject
    public LoginServicesImpl(LoginDAOImpl loginDAOImpl) {
        this.loginDAOImpl = loginDAOImpl;
    }

    @Override
    public Either<String, Reader> login(String user, String password) {
        Either<Integer, Reader> response = loginDAOImpl.login(user, password);
        Either<String, Reader> either;
        if (response.isLeft()){
            switch (response.getLeft()) {
                case -1 -> either = Either.left(ServicesConstants.USER_OR_PASSWORD_INCORRECT);
                case -2 ->
                        either = Either.left(ServicesConstants.ERROR_GETTING_LOGIN + ServicesConstants.BREAK + ServicesConstants.NON_RELATED_WITH_THE_DB);
                case -3 -> either = Either.left(ServicesConstants.ERROR_GETTING_LOGIN);
                default -> either = Either.left(ServicesConstants.UNKNOWN_ERROR);
            }
        }
        else {
            either = Either.right(response.get());
        }
        return either;
    }
}
