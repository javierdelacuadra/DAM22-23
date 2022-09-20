package data.impl;

import data.DaoCompras;
import jakarta.inject.Inject;
import modelo.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

public class DaoComprasImpl extends DaoBase implements DaoCompras {

    private final Database db;

    @Inject
    public DaoComprasImpl(Database db) {
        this.db = db;
    }

    @Override
    public boolean addProducto(Producto p, String dni) {
        Map<String, Cliente> clientes = db.loadClientes();
        List<Producto> productos = db.loadProductos();
        String nombreProducto = p.getNombre();
        int numero = 0;
        for (int i = 0; i < productos.size(); i++) {
            if (productos.get(i).getNombre().equalsIgnoreCase(nombreProducto)) {
                numero = i;
            }
        }
        double precio = productos.get(numero).getPrecio();
        Producto p1 = new ProductoNormal(productos.get(numero).getNombre(), precio, 1);
        if (productos.get(numero).getStock() > 0) {
            clientes.get(dni).getCarrito().add(p1);
            productos.get(numero).setStock(productos.get(numero).getStock() - p1.getStock());
            db.saveClientes(clientes);
            return db.saveProductos(productos);
        }
        return false;
    }

    @Override
    public List<Producto> verProductos() {
        List<Producto> productos = db.loadProductos();
        return productos.stream()
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public List<Producto> verCarrito(String dni) {
        Map<String, Cliente> clientes = db.loadClientes();
        return clientes.get(dni).getCarrito().stream().collect(Collectors.toUnmodifiableList());
    }

    @Override
    public boolean pagar(String nombreMonedero, String dni) {
        Map<String, Cliente> clientes = db.loadClientes();
        AtomicBoolean hayDinero = new AtomicBoolean(false);

        clientes.get(dni).getMonederos().forEach(monedero1 -> {
            if (monedero1.getNombre().equalsIgnoreCase(nombreMonedero) && monedero1.getDinero() >= precio(dni)) {
                hayDinero.set(true);
                if (clientes.get(dni) instanceof ClienteVIP) {
                    double precio = precio(dni);
                    double descuento = ((ClienteVIP) clientes.get(dni)).getDescuento();
                    precio = (precio / 100) * (100 - descuento);
                    monedero1.setDinero(monedero1.getDinero() - precio);
                } else {
                    monedero1.setDinero(monedero1.getDinero() - precio(dni));
                }
                clientes.get(dni).getComprasAntiguas().add(clientes.get(dni).getCarrito());
                clientes.get(dni).setCarrito(new ArrayList<>());
            }
        });
        db.saveClientes(clientes);
        return hayDinero.get();
    }

    @Override
    public boolean existeProducto(Producto p) {
        List<Producto> productos = db.loadProductos();
        return productos.stream().anyMatch(producto -> producto.getNombre().equalsIgnoreCase(p.getNombre()));
    }

    @Override
    public double precio(String dni) {
        Map<String, Cliente> clientes = db.loadClientes();
        double precio = 0;
        for (int i = 0; i < clientes.get(dni).getCarrito().size(); i++) {
            precio += ((clientes.get(dni).getCarrito().get(i).getPrecio()) * (clientes.get(dni).getCarrito().get(i).getStock()));
        }
        return precio;
    }

    @Override
    public boolean removeProducto(Producto p, String dni) {
        Map<String, Cliente> clientes = db.loadClientes();
        List<Producto> carrito = clientes.get(dni).getCarrito();
        for (int i = 0; i < carrito.size(); i++) {
            if (carrito.get(i).getNombre().equalsIgnoreCase(p.getNombre())) {
                carrito.remove(i);
                return true;
            }
        }
        return false;
    }
}