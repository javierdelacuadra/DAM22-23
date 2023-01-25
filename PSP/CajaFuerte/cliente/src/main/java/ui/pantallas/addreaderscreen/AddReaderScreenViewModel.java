package ui.pantallas.addreaderscreen;

import io.reactivex.rxjava3.schedulers.Schedulers;
import jakarta.inject.Inject;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import modelo.Usuario;
import servicios.ServiciosUsuario;

import java.util.List;

public class AddReaderScreenViewModel {

    private final ServiciosUsuario serviciosUsuario;
    private final ObjectProperty<AddReaderState> state;

    @Inject
    public AddReaderScreenViewModel(ServiciosUsuario serviciosUsuario) {
        this.serviciosUsuario = serviciosUsuario;
        state = new SimpleObjectProperty<>(new AddReaderState(null, null));
    }

    public ObjectProperty<AddReaderState> getState() {
        return state;
    }

    public void getUsuarios() {
        serviciosUsuario.getAllUsers()
                .observeOn(Schedulers.single())
                .subscribe(either -> {
                    if (either.isRight()) {
                        state.set(new AddReaderState(null, either.get()));
                    } else {
                        state.set(new AddReaderState(either.getLeft(), null));
                    }
                });
    }

    public void addUsuario(Usuario usuario) {
        serviciosUsuario.saveUsuario(usuario)
                .subscribeOn(Schedulers.single())
                .subscribe(either -> {
                    if (either.isRight()) {
                        List<Usuario> usuarios = state.get().usuarios;
                        usuarios.add(either.get());
                        state.set(new AddReaderState(null, usuarios));
                    } else {
                        state.set(new AddReaderState(either.getLeft(), null));
                    }
                });
    }
}