package modelo;

import ui.common.ConstantesGestionClientes;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ClienteNormal extends Cliente {

    public ClienteNormal(String dni) {
        super(dni);
        type = "ClienteNormal";
    }

    public ClienteNormal(String dni, String nombre) {
        super(dni, nombre);
        type = "ClienteNormal";
    }

    public ClienteNormal(String dni, String nombre, List<Ingrediente> ingredientes) {
        super(dni, nombre, ingredientes);
        type = "ClienteNormal";
    }

    public ClienteNormal(String dni, String nombre, Set<Monedero> monederos, List<Producto> carrito, List<List<Producto>> comprasAntiguas) {
        super(dni, nombre, monederos, carrito, comprasAntiguas);
        type = "ClienteNormal";
    }

    @Override
    public String toString() {
        return ConstantesGestionClientes.ESPACIO_CON_N + ConstantesGestionClientes.CORCHETE1 +
                ConstantesGestionClientes.NOMBRE_DE_USUARIO + super.getNombre() + ConstantesGestionClientes.COMILLAS +
                ConstantesGestionClientes.DNI + super.getDni() + ConstantesGestionClientes.COMILLAS +
                ConstantesGestionClientes.CORCHETE2;
    }

    @Override
    public Cliente clonar() {
        return new ClienteNormal(super.getDni(), super.getNombre(), super.getMonederos(), super.getCarrito(), super.getComprasAntiguas()
                .stream().collect(Collectors.toUnmodifiableList()));
    }
}