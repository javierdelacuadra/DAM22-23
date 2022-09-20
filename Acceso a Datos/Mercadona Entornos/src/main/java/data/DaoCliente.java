package data;

import modelo.Cliente;
import modelo.Ingrediente;
import modelo.LineaCompra;
import modelo.Producto;

import java.util.List;

public interface DaoCliente {
    boolean registrarCliente(Cliente c);

    boolean eliminarCliente(Cliente c);

    boolean modificarNombre(String nombreViejo, String nombreNuevo, String dni);

    boolean modificarDNI(Cliente c, Cliente cviejo);

    boolean addAlergeno(String dni, Ingrediente i);

    List<Cliente> verClientes();

    List<List<Producto>> verComprasAntiguas(String dni);

    List<Cliente> verClientesOrdenadosPorGasto();

    double gastoTotalCliente(String dni);

    boolean existeCliente(Cliente c);

    boolean modificarCliente(Cliente c1, Cliente c2);

    Cliente getCliente(String nombre);
}
