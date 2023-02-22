package ui.screens.principal;

import domain.serivces.impl.LoginServicesImpl;
import jakarta.inject.Inject;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import ui.screens.common.ScreenConstants;

public class PrincipalViewModel {

    private final LoginServicesImpl loginServices;

    @Inject
    public PrincipalViewModel(LoginServicesImpl loginServices) {
        this.loginServices = loginServices;
        _state = new SimpleObjectProperty<>(new PrincipalState(null, null));
    }

    private final ObjectProperty<PrincipalState> _state;

    public ReadOnlyObjectProperty<PrincipalState> getState() {
        return _state;
    }


    public void logout(){
        loginServices.logout().subscribe(responses -> {
            if (responses.isRight()) {
                _state.setValue(new PrincipalState(null, ScreenConstants.YOU_LOGGED_OUT_SUCCESSFULLY));
            } else {
                _state.setValue(new PrincipalState(responses.getLeft(), null));
            }
        });
    }
}
