package ui.pantallas.pantalladminclientes;

import jakarta.inject.Inject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import modelo.Cliente;
import servicios.ServiciosClientes;

public class PantallaAdminClientesViewModel {

    private final ServiciosClientes sc;
    private final ObservableList<Cliente> clientes;

    @Inject
    public PantallaAdminClientesViewModel(ServiciosClientes sc) {
        this.sc = sc;
        clientes = FXCollections.observableArrayList(sc.verClientes());
    }

    public void addCliente(Cliente c) {
        sc.registrarCliente(c);
        clientes.clear();
        clientes.addAll(sc.verClientes());
    }

    public void eliminarCliente(Cliente c) {
        sc.eliminarCliente(c);
        clientes.clear();
        clientes.addAll(sc.verClientes());
    }

    public void modificarCliente(Cliente c1, Cliente c2) {
        sc.modificarCliente(c1, c2);
        clientes.clear();
        clientes.addAll(sc.verClientes());
    }

    public ObservableList<Cliente> getClientes() {
        return clientes;
    }
}