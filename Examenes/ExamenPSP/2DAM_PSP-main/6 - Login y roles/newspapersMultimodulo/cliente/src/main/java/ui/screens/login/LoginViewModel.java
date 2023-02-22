package ui.screens.login;

import common.CommonConstants;
import domain.serivces.impl.LoginServicesImpl;
import io.reactivex.rxjava3.schedulers.Schedulers;
import jakarta.inject.Inject;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import org.miutils.common.AplicationConstants;
import org.miutils.domain.modelo.Reader;
import ui.screens.common.ScreenConstants;

import java.time.LocalDate;

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

    public void tryLogin(String email, String password) {
        loginServicesImpl.login(email, password)
                .observeOn(Schedulers.single())
                .subscribe(login -> {
                    _state.setValue(new LoginState(null, null, null));
                    LoginState ps;
                    if (login.isRight()) {
                        ps = new LoginState(null, new Reader(CommonConstants.ID_ADMIN, CommonConstants.ADMIN, LocalDate.of(1967, 11, 4)), null);
                    } else {
                        ps = new LoginState(login.getLeft(), null, null);
                    }
                    _state.setValue(ps);
                });
    }

    public void forgotPassword(String email) {
        loginServicesImpl.forgotPassword(email)
                .observeOn(Schedulers.single())
                .subscribe(login -> {
                    _state.setValue(new LoginState(null, null, null));
                    LoginState ps;
                    if (login.isRight()) {
                        ps = new LoginState(null, null, ScreenConstants.SE_HA_ENVIADO_UN_MAIL_A + email + ScreenConstants.CON_LAS_INSTRUCCIONES_PARA_RECUPERAR_LA_CONTRASENA);
                    } else {
                        ps = new LoginState(login.getLeft(), null, null);
                    }
                    _state.setValue(ps);
                });
    }

    public void loginInvitado() {
        loginServicesImpl.loginInvitado().observeOn(Schedulers.single())
                .subscribe(responses ->
                {
                    if (responses.isRight()) {
                        _state.setValue(new LoginState(null, new Reader(CommonConstants.ID_INVITADO, AplicationConstants.INVITADO, LocalDate.of(1967, 11, 4)), null));
                    } else {
                        _state.setValue(new LoginState(responses.getLeft(), null, null));
                    }

                });
    }
}
