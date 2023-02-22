package ui.screens.login;

import io.vavr.control.Either;
import jakarta.inject.Inject;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import model.Reader;
import serivces.impl.LoginServicesImpl;

public class LoginViewModel {

        private LoginServicesImpl loginServicesImpl;

        @Inject
        public LoginViewModel(LoginServicesImpl loginServicesImpl) {
            this.loginServicesImpl = loginServicesImpl;_state = new SimpleObjectProperty<>(new LoginState(null, null));
        }


    private final ObjectProperty<LoginState> _state;

    public ReadOnlyObjectProperty<LoginState> getState() {
        return _state;
    }

    public void tryLogin(String userName, String password) {
        Either<String, Reader> either = loginServicesImpl.login(userName, password);
        _state.setValue(new LoginState(null, null));
        LoginState ps;
        if (either.isRight()) {
            ps = new LoginState(null, either.get());
        } else {
            ps = new LoginState(either.getLeft(), null);
        }
        _state.setValue(ps);
    }
}
