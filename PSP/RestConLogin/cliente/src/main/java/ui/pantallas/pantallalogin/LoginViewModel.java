package ui.pantallas.pantallalogin;

import io.reactivex.rxjava3.schedulers.Schedulers;
import jakarta.inject.Inject;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import model.Reader;
import model.ReaderLogin;
import servicios.ServicesReadersSQL;
import ui.pantallas.deletereaderscreen.DeleteReaderState;

import java.util.concurrent.atomic.AtomicReference;

;

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


    public Reader getReader(int id) {
        return servicesReadersSQL.getReadersById(id);
    }

    public Integer register(ReaderLogin readerLogin) {
        AtomicReference<Integer> id = new AtomicReference<>(0);
        servicesReadersSQL.register(readerLogin)
                .observeOn(Schedulers.from(Platform::runLater))
                .subscribe(either -> {
                    if (either.isRight()) {
                        id.getAndSet(either.get().getId_reader());
                    } else {
                        id.getAndSet(-1);
                    }
                });
        return id.get();
        //TODO: si se te pasan los 5 minutos no deja pasar (cambiar de 10 a 5), filtros de permisos,
        //TODO: al hacer logout, se borra el atributo LOGIN de la sesion
        //TODO: getReaderById para el principalController
    }

    public void recoverPassword(String email) {
        servicesReadersSQL.recoverPassword(email)
                .observeOn(Schedulers.from(Platform::runLater))
                .subscribe(s -> state.set(new LoginState("Se ha enviado un correo para recuperar la contraseña a:\n" + email, null)));
    }

    public void sendEmail(String email) {
        servicesReadersSQL.sendEmail(email)
                .observeOn(Schedulers.from(Platform::runLater))
                .subscribe(s -> state.set(new LoginState("Se ha enviado un correo para activar tu cuenta a:\n" + email, null)));
    }
}
