package modelo;

import ui.common.ConstantesAdmin;
import ui.common.ConstantesGestionClientes;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class Producto implements Clonable<Producto> {

    private String nombre;
    private double precio;
    public String type;
    private int stock;
    private List<Ingrediente> ingredientes;

    public Producto(String nombre, double precio, int stock, List<Ingrediente> ingredientes) {
        this.nombre = nombre;
        this.precio = precio;
        this.stock = stock;
        this.ingredientes = ingredientes;
    }

    public Producto(String nombre, int stock) {
        this.nombre = nombre;
        this.stock = stock;
    }

    public Producto(String nombre, double precio, int stock) {
        this();
        this.nombre = nombre;
        this.precio = precio;
        this.stock = stock;
    }

    public Producto(String nombre) {
        this.nombre = nombre;
    }

    protected Producto() {
        ingredientes = new ArrayList<>();
    }

    public List<Ingrediente> getIngredientes() {
        return ingredientes;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    @Override
    public String toString() {
        return ConstantesGestionClientes.ESPACIO_CON_N +
                ConstantesAdmin.NOMBRE_PRODUCTOS + nombre + ConstantesGestionClientes.COMILLAS +
                ConstantesAdmin.PRECIO_PRODUCTOS + precio +
                ConstantesAdmin.CANTIDAD_PRODUCTOS + stock +
                ", ingredientes=" + ingredientes +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Producto producto = (Producto) o;
        return Objects.equals(nombre, producto.nombre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nombre);
    }

}