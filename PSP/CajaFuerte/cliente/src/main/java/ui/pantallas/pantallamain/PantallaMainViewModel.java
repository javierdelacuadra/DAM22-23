package ui.pantallas.pantallamain;

import io.reactivex.rxjava3.schedulers.Schedulers;
import jakarta.inject.Inject;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import servicios.ServiciosLogin;
import ui.common.ConstantesUI;

public class PantallaMainViewModel {

    private final ServiciosLogin serviciosLogin;
    private final ObjectProperty<PantallaMainState> state;

    @Inject
    public PantallaMainViewModel(ServiciosLogin serviciosLogin) {
        this.serviciosLogin = serviciosLogin;
        state = new SimpleObjectProperty<>(new PantallaMainState(null));
    }

    public ObjectProperty<PantallaMainState> getState() {
        return state;
    }

    public void logout() {
        serviciosLogin.logout()
                .observeOn(Schedulers.from(Platform::runLater))
                .subscribe(stringResponse ->
                                state.set(new PantallaMainState(stringResponse.body())),
                        throwable -> state.set(new PantallaMainState(ConstantesUI.ERROR_INESPERADO))
                );
    }
}
