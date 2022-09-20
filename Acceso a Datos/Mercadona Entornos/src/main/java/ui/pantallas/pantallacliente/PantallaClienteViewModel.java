package ui.pantallas.pantallacliente;

import jakarta.inject.Inject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import modelo.Monedero;
import modelo.Producto;
import servicios.ServiciosCompras;
import servicios.ServiciosMonedero;
import servicios.ServiciosProductos;

public class PantallaClienteViewModel {

    private final ServiciosCompras scompras;
    private final ServiciosMonedero sm;
    private final ServiciosProductos sp;
    private final ObservableList<Producto> productos;
    private final ObservableList<Monedero> monederos;
    private final ObservableList<Producto> carrito;

    @Inject
    public PantallaClienteViewModel(ServiciosCompras scompras, ServiciosMonedero sm, ServiciosProductos sp) {
        this.scompras = scompras;
        this.sm = sm;
        this.sp = sp;
        this.productos = FXCollections.observableArrayList(sp.mostrarProductos());
        this.monederos = FXCollections.observableArrayList();
        this.carrito = FXCollections.observableArrayList();
    }

    public ObservableList<Monedero> getMonederos() {
        return monederos;
    }

    public void setMonederos(String dni) {
        monederos.setAll(sm.verMonederos(dni));
    }

    public ObservableList<Producto> getCarrito() {
        return carrito;
    }

    public void setCarrito(String dni) {
        carrito.setAll(scompras.verCarrito(dni));
    }

    public ObservableList<Producto> getProductos() {
        return productos;
    }

    public void setProductos() {
        productos.setAll(sp.mostrarProductos());
    }

    public boolean addProducto(Producto p, String dni) {
        if (scompras.addProducto(p, dni)) {
            carrito.clear();
            setCarrito(dni);
            setProductos();
            return true;
        }
        return false;
    }

    public void removeProducto(Producto p, String dni) {
        scompras.removeProducto(p, dni);
        carrito.clear();
        setCarrito(dni);
    }

    public boolean pagar(String nombreMonedero, String dni) {
         if (scompras.pagar(nombreMonedero, dni)) {
             carrito.clear();
             setMonederos(dni);
             return true;
         }
         return false;
    }
}