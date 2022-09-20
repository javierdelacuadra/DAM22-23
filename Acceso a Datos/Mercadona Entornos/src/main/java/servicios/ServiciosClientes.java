package servicios;

import modelo.Cliente;
import modelo.Ingrediente;
import modelo.Producto;

import java.util.List;

public interface ServiciosClientes {
    boolean eliminarCliente(Cliente c);

    boolean modificarNombre(String nombreViejo, String nombreNuevo, String dni);

    boolean modificarDNI(Cliente c, Cliente cviejo);

    List<Cliente> verClientes();

    boolean registrarCliente(Cliente c);

    List<List<Producto>> verComprasAntiguas(String dni);

    boolean inicioSesion(Cliente c);

    double gastoTotalCliente(String dni);

    boolean addAlergeno(String dni, Ingrediente i);

    List<Cliente> verClientesOrdenados();

    boolean modificarCliente(Cliente c1, Cliente c2);

    Cliente getCliente(String nombre);
}
