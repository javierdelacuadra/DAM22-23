package servicios;

import modelo.Producto;

import java.util.List;

public interface ServiciosCompras {
    boolean addProducto(Producto p, String dni);

    List<Producto> verProductos();

    List<Producto> verCarrito(String dni);

    boolean pagar(String nombreMonedero, String dni);

    boolean existeProducto(Producto p);

    boolean removeProducto(Producto p, String dni);
}
