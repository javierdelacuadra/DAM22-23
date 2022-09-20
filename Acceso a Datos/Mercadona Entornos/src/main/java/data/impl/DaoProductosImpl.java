package data.impl;

import data.DaoProductos;
import jakarta.inject.Inject;
import modelo.Cliente;
import modelo.LineaCompra;
import modelo.Producto;
import modelo.ProductoNormal;
import ui.common.ConstantesAdmin;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class DaoProductosImpl extends DaoBase implements DaoProductos {

    private final Database db;

    @Inject
    public DaoProductosImpl(Database db) {
        this.db = db;
    }

    @Override
    public boolean addProducto(Producto p) {
        List<Producto> productos = db.loadProductos();
        if (!productoExiste(p.getNombre())) {
            productos.add(new ProductoNormal(p.getNombre(), p.getPrecio(), p.getStock()));
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
    public boolean editarNombre(String nombreViejo, String nombreNuevo) {
        List<Producto> productos = db.loadProductos();
        int numero;
        numero = getNumero(nombreViejo);
        productos.get(numero).setNombre(nombreNuevo);
        return db.saveProductos(productos);
    }

    @Override
    public boolean editarPrecio(String nombre, double precio) {
        List<Producto> productos = db.loadProductos();
        int numero;
        numero = getNumero(nombre);
        productos.get(numero).setPrecio(precio);
        return db.saveProductos(productos);
    }

    @Override
    public boolean editarCantidad(String nombre, int cantidad) {
        List<Producto> productos = db.loadProductos();
        int numero;
        numero = getNumero(nombre);
        productos.get(numero).setStock(productos.get(numero).getStock() + cantidad);
        return db.saveProductos(productos);
    }

    @Override
    public List<String> listaProductosOrdenadosPorCantidadComprada() {
        /*Map<String, Cliente> clientes = db.loadClientes();
        Map<String, Double> map = clientes.values().stream()
                .flatMap(cliente -> cliente.getComprasAntiguas().stream())
                .flatMap(Collection::stream)
                .collect(Collectors.groupingBy(producto -> producto.getProducto().getNombre(), Collectors.summingDouble(Producto::getCantidad)));

        return map.entrySet().stream()
                .sorted(Map.Entry.<String, Double>comparingByValue().reversed())
                .map(stringDoubleEntry -> stringDoubleEntry.getKey() + " " + stringDoubleEntry.getValue())
                .collect(Collectors.toUnmodifiableList());*/
        return null;
    }

    @Override
    public int getNumero(String nombre) {
        List<Producto> productos = db.loadProductos();
        int numero = 0;
        for (int i = 0; i < productos.size(); i++) {
            if (Objects.equals(productos.get(i).getNombre(), nombre)) {
                numero = i;
                break;
            }
        }
        return numero;
    }

    @Override
    public String getProducto(String nombre) {
        List<Producto> productos = db.loadProductos();
        String producto;
        Producto p = new ProductoNormal(nombre);
        if (productos.contains(p)) {
            int index = productos.indexOf(p);
            producto = productos.get(index).toString();
        } else {
            producto = ConstantesAdmin.NO_SE_HA_ENCONTRADO_EL_PRODUCTO;
        }
        return producto;
    }

    @Override
    public boolean eliminarProducto(Producto producto) {
        List<Producto> productos = db.loadProductos();
        productos.remove(producto);
        return db.saveProductos(productos);
    }

    @Override
    public boolean updateProducto(Producto producto1, Producto producto2) {
        List<Producto> productos = db.loadProductos();
       productos.remove(producto1);
       productos.add(producto2);
        return db.saveProductos(productos);
    }

    @Override
    public boolean productoExiste(String nombre) {
        List<Producto> productos = db.loadProductos();
        return productos.stream().anyMatch(producto -> Objects.equals(producto.getNombre(), nombre));
    }
}
