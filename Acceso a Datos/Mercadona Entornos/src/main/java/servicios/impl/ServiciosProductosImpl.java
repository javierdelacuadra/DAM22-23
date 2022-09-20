package servicios.impl;

import data.DaoProductos;
import jakarta.inject.Inject;
import modelo.Producto;
import servicios.ServiciosProductos;

import java.util.List;

public class ServiciosProductosImpl implements ServiciosProductos {

    private final DaoProductos d;

    @Inject
    public ServiciosProductosImpl(DaoProductos d) {
        this.d = d;
    }

    @Override
    public boolean addProducto(Producto p) {
        if (p.getPrecio() > 0 && p.getStock() >= 1) {
            return d.addProducto(p);
        } else {
            return false;
        }
    }

    @Override
    public boolean editarNombre(String nombreViejo, String nombreNuevo) {
        return d.editarNombre(nombreViejo, nombreNuevo);
    }

    @Override
    public boolean editarPrecio(String nombre, double precio) {
        if (precio > 0) {
            return d.editarPrecio(nombre, precio);
        } else return false;
    }

    @Override
    public boolean editarStock(String nombre, int stock) {
        if (stock < 0) {
            return false;
        } else {
            return d.editarCantidad(nombre, stock);
        }
    }

    @Override
    public List<String> verClientesOrdenadosPorGasto() {
        return d.listaProductosOrdenadosPorCantidadComprada();
    }

    @Override
    public String getProducto(String nombre) {
        return d.getProducto(nombre);
    }

    @Override
    public List<Producto> mostrarProductos() {
        return d.verProductos();
    }

    @Override
    public boolean hayProductos() {
        return d.verProductos().isEmpty();
    }

    @Override
    public boolean eliminarProducto(Producto producto) {
        return d.eliminarProducto(producto);
    }

    @Override
    public boolean updateProducto(Producto producto1, Producto producto2) {
        return d.updateProducto(producto1, producto2);
    }
}