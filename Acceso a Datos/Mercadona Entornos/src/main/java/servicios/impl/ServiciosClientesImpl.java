package servicios.impl;

import data.DaoCliente;
import jakarta.inject.Inject;
import modelo.Cliente;
import modelo.Ingrediente;
import modelo.Producto;
import servicios.ServiciosClientes;
import ui.common.ConstantesAdmin;

import java.util.List;

public class ServiciosClientesImpl implements ServiciosClientes {

    private final DaoCliente dc;

    @Inject
    public ServiciosClientesImpl(DaoCliente dc) {
        this.dc = dc;
    }

    @Override
    public boolean eliminarCliente(Cliente c) {
        return dc.eliminarCliente(c);
    }

    @Override
    public boolean modificarNombre(String nombreViejo, String nombreNuevo, String dni) {
        return dc.modificarNombre(nombreViejo, nombreNuevo, dni);
    }

    @Override
    public boolean modificarDNI(Cliente c, Cliente cviejo) {
        return dc.modificarDNI(c, cviejo);
    }

    @Override
    public List<Cliente> verClientes() {
        return dc.verClientes();
    }

    @Override
    public boolean registrarCliente(Cliente c) {
        if (dc.existeCliente(c) && !c.getNombre().equalsIgnoreCase(ConstantesAdmin.ADMIN)) {
            return dc.registrarCliente(c);
        }
        return false;
    }

    @Override
    public List<List<Producto>> verComprasAntiguas(String dni) {
        return dc.verComprasAntiguas(dni);
    }

    @Override
    public boolean inicioSesion(Cliente c) {
        return dc.existeCliente(c);
    }

    @Override
    public double gastoTotalCliente(String dni) {
        return dc.gastoTotalCliente(dni);
    }

    @Override
    public boolean addAlergeno(String dni, Ingrediente i) {
        return dc.addAlergeno(dni, i);
    }

    @Override
    public List<Cliente> verClientesOrdenados() {
        return dc.verClientesOrdenadosPorGasto();
    }

    @Override
    public boolean modificarCliente(Cliente c1, Cliente c2) {
        if (!dc.existeCliente(c1)) {
            return dc.modificarCliente(c1, c2);
        } else {
            return false;
        }
    }

    @Override
    public Cliente getCliente(String nombre) {
        return dc.getCliente(nombre);
    }
}