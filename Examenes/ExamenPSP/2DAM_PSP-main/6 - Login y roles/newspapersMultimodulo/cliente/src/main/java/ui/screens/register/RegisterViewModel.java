package ui.screens.register;

import domain.serivces.impl.LoginServicesImpl;
import jakarta.inject.Inject;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import org.miutils.domain.modelo.Login;
import org.miutils.domain.modelo.Reader;
import ui.screens.common.ScreenConstants;

import java.time.LocalDate;

public class RegisterViewModel {
    private final LoginServicesImpl loginServices;

    @Inject
    public RegisterViewModel(LoginServicesImpl loginServices) {
        this.loginServices = loginServices;
        _state = new SimpleObjectProperty<>(new RegisterState(null, null));
    }

    private final ObjectProperty<RegisterState> _state;

    public ReadOnlyObjectProperty<RegisterState> getState() {
        return _state;
    }


    public void register(String name, LocalDate birth, String email, String password) {
        if (!name.equals("") && birth != null && !email.equals("") && !password.equals("")) {
            Reader newReader = new Reader(name, birth, new Login(email, password));
            loginServices.add(newReader).subscribe(readers -> {
                if (readers.isRight()) {
                    _state.setValue(new RegisterState(null, ScreenConstants.BIENVENIDO + ScreenConstants.SPACE + readers.get().getName() + ScreenConstants.REVISA_TU_CORREO_PARA_ACTIVAR_TU_CUENTA_Y_YA_PODRAS_ENTRAR_LIBREMENTE));
                } else {
                    _state.setValue(new RegisterState(readers.getLeft(), null));
                }
            });
        } else {
            _state.setValue(new RegisterState(ScreenConstants.POR_FAVOR_COMPLETE_TODOS_LOS_CAMPOS, null));
        }
    }

}
