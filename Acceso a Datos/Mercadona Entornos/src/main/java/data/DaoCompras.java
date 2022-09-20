package data;

import modelo.LineaCompra;
import modelo.Producto;

import java.util.List;

public interface DaoCompras {
    boolean addProducto(Producto p, String dni);

    List<Producto> verProductos();

    List<Producto> verCarrito(String dni);

    boolean pagar(String nombreMonedero, String dni);

    boolean existeProducto(Producto p);

    double precio(String dni);

    boolean removeProducto(Producto p, String dni);
}