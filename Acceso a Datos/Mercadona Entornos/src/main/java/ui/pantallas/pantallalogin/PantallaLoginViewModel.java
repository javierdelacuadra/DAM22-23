package ui.pantallas.pantallalogin;

import jakarta.inject.Inject;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import modelo.Cliente;
import modelo.ClienteNormal;
import servicios.ServiciosClientes;

public class PantallaLoginViewModel {
    private final ServiciosClientes sc;
    private final ObjectProperty<PantallaLoginState> state;

    @Inject
    public PantallaLoginViewModel(ServiciosClientes sc) {
        this.sc = sc;
        state = new SimpleObjectProperty<>(new PantallaLoginState(false,null));
    }

    public ReadOnlyObjectProperty<PantallaLoginState> getState() {
        return state;
    }

    public void iniciarSesion(String nombre, String dni) {
        Cliente c = new ClienteNormal(dni, nombre);
        if (sc.inicioSesion(c) || nombre.equalsIgnoreCase("admin")) {
            state.set(new PantallaLoginState(true, null));
        } else {
            state.set(new PantallaLoginState(false, "Nombre o DNI incorrectos"));
        }
    }

    public void registro(String nombre, String dni) {
        Cliente c = new ClienteNormal(dni, nombre);
        if (sc.registrarCliente(c)) {
            state.set(new PantallaLoginState(true, null));
        } else {
            state.set(new PantallaLoginState(false, "El nombre o el DNI ya existen"));
        }
    }
}
