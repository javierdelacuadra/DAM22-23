package ui.screens.login;

import common.CommonConstants;
import domain.serivces.impl.LoginServicesImpl;
import io.reactivex.rxjava3.schedulers.Schedulers;
import jakarta.inject.Inject;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import org.utils.domain.modelo.Rol;
import org.utils.domain.modelo.User;

public class LoginViewModel {

    private final LoginServicesImpl loginServicesImpl;

    @Inject
    public LoginViewModel(LoginServicesImpl loginServicesImpl) {
        this.loginServicesImpl = loginServicesImpl;
        _state = new SimpleObjectProperty<>(new LoginState(null, null, null));
    }


    private final ObjectProperty<LoginState> _state;

    public ReadOnlyObjectProperty<LoginState> getState() {
        return _state;
    }

    public void tryLogin(String name, String password) {
        loginServicesImpl.login(name, password)
                .observeOn(Schedulers.single())
                .subscribe(login -> {
                    _state.setValue(new LoginState(null, null, null));
                    LoginState ps;
                    if (login.isRight()) {
                        if (name.equals(CommonConstants.ROOT)) {
                            ps = new LoginState(null, new User(CommonConstants.ROOT, Rol.ADMIN), null);
                        } else {
                            ps = new LoginState(null, new User(name, Rol.USER), null);
                        }
                    } else {
                        ps = new LoginState(login.getLeft(), null, null);
                    }
                    _state.setValue(ps);
                });
    }
}
