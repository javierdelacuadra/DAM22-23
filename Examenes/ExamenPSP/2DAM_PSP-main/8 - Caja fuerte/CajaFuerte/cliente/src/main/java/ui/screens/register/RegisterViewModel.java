package ui.screens.register;

import domain.serivces.impl.UserServicesImpl;
import jakarta.inject.Inject;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import org.utils.domain.modelo.Rol;
import org.utils.domain.modelo.User;
import ui.screens.common.ScreenConstants;

public class RegisterViewModel {

    private final UserServicesImpl userServices;

    @Inject
    public RegisterViewModel(UserServicesImpl userServices) {
        this.userServices = userServices;
        _state = new SimpleObjectProperty<>(new RegisterState(null, null));
    }


    private final ObjectProperty<RegisterState> _state;

    public ReadOnlyObjectProperty<RegisterState> getState() {
        return _state;
    }


    public void register(String user, String password){
        if (user.isEmpty() || password.isEmpty()) {
            RegisterState ps = new RegisterState(ScreenConstants.USUARIO_O_CONTRASENA_VACIOS, null);
            _state.setValue(ps);
        } else {
            userServices.add(new User(user, password, Rol.USER))
                    .subscribe(user1 -> {
                        RegisterState ps = new RegisterState(_state.get().getError(), _state.get().getSuccess());
                        if (user1.isRight()) {
                            ps.setSuccess(ScreenConstants.USUARIO_REGISTRADO);
                            _state.setValue(ps);
                        } else {
                            ps.setError(user1.getLeft());
                            _state.setValue(ps);
                        }
                    });
        }
    }

    public void clearMessages(){
        RegisterState ps = new RegisterState(null, null);
        _state.setValue(ps);
    }

}
