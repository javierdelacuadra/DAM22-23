package ui.pantallas.pantallaadminproductos;

import jakarta.inject.Inject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import modelo.Producto;
import servicios.ServiciosProductos;

public class PantallaAdminProductosViewModel {
    private final ServiciosProductos sp;
    private final ObservableList<Producto> productos;

    @Inject
    public PantallaAdminProductosViewModel(ServiciosProductos sp) {
        this.sp = sp;
        productos = FXCollections.observableArrayList(sp.mostrarProductos());
    }

    public void addProducto(Producto producto) {
        if (sp.addProducto(producto)) {
            productos.clear();
            productos.addAll(sp.mostrarProductos());
        }
    }

    public void removeProducto(Producto producto) {
        sp.eliminarProducto(producto);
        productos.clear();
        productos.addAll(sp.mostrarProductos());
    }

    public void updateProducto(Producto producto1, Producto producto2) {
        sp.updateProducto(producto1, producto2);
        productos.clear();
        productos.addAll(sp.mostrarProductos());
    }

    public ObservableList<Producto> getProductos() {
        return productos;
    }
}