package ui.pantallas.pantallalogin;

import io.reactivex.rxjava3.schedulers.Schedulers;
import jakarta.inject.Inject;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import model.ReaderLogin;
import servicios.ServicesReadersSQL;
import ui.common.ConstantesUI;

public class LoginViewModel {

    private final ServicesReadersSQL servicesReadersSQL;
    private final ObjectProperty<LoginState> state;

    @Inject
    public LoginViewModel(ServicesReadersSQL servicesReadersSQL) {
        this.servicesReadersSQL = servicesReadersSQL;
        state = new SimpleObjectProperty<>(new LoginState(null, null));
    }

    public ObjectProperty<LoginState> getState() {
        return state;
    }

    public void login(ReaderLogin readerLogin) {
        servicesReadersSQL.login(readerLogin)
                .observeOn(Schedulers.from(Platform::runLater))
                .subscribe(either -> {
                    if (either.isLeft()) {
                        state.set(new LoginState(either.getLeft(), null));
                    } else {
                        ReaderLogin reader = either.get();
                        state.set(new LoginState(null, reader));
                    }
                });
    }

    public void register(ReaderLogin readerLogin) {
        servicesReadersSQL.register(readerLogin)
                .observeOn(Schedulers.from(Platform::runLater))
                .subscribe(either -> {
                    if (either.isRight()) {
                        state.set(new LoginState(ConstantesUI.USUARIO_REGISTRADO_CORRECTAMENTE, readerLogin));
                    } else {
                        state.set(new LoginState(either.getLeft(), null));
                    }
                });
    }

    public void recoverPassword(String email) {
        servicesReadersSQL.recoverPassword(email)
                .observeOn(Schedulers.from(Platform::runLater))
                .subscribe(stringResponse ->
                                state.set(new LoginState(ConstantesUI.SE_HA_ENVIADO_UN_CORREO_PARA_RECUPERAR_LA_PASSWORD_A + email, null)),
                        throwable -> state.set(new LoginState(ConstantesUI.ERROR_INESPERADO, null))
                );
    }

    public void sendEmail(String email) {
        servicesReadersSQL.sendEmail(email)
                .observeOn(Schedulers.from(Platform::runLater))
                .subscribe(stringResponse -> state.set(new LoginState(ConstantesUI.SE_HA_ENVIADO_UN_CORREO_PARA_ACTIVAR_TU_CUENTA_A + email, null)),
                        throwable -> state.set(new LoginState(ConstantesUI.ERROR_INESPERADO, null))
                );
    }
}