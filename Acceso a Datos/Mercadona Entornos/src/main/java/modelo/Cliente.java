package modelo;

import ui.common.ConstantesGestionClientes;

import java.util.*;

public abstract class Cliente implements Clonable<Cliente> {
    private String dni;
    private String nombre;
    public String type;
    private final Set<Monedero> monederos;
    private List<Producto> carrito;
    private final List<List<Producto>> comprasAntiguas;
    private List<Ingrediente> ingredientes;

    protected Cliente() {
        monederos = new HashSet<>();
        carrito = new ArrayList<>();
        comprasAntiguas = new ArrayList<>();
        ingredientes = new ArrayList<>();
    }

    public Cliente(String dni) {
        this();
        this.dni = dni;
    }

    public Cliente(String dni, String nombre) {
        this(dni);
        this.nombre = nombre;
    }

    public Cliente(String dni, String nombre, List<Ingrediente> ingredientes) {
        this(dni);
        this.nombre = nombre;
        this.ingredientes = ingredientes;
    }

    public Cliente(String dni, String nombre, Set<Monedero> monederos, List<Producto> carrito, List<List<Producto>> comprasAntiguas) {
        this.dni = dni;
        this.nombre = nombre;
        this.monederos = monederos;
        this.carrito = carrito;
        this.comprasAntiguas = comprasAntiguas;
    }

    public List<Ingrediente> getIngredientes() {
        return ingredientes;
    }

    public String getDni() {
        return dni;
    }

    public List<Producto> getCarrito() {
        return carrito;
    }

    public void setCarrito(List<Producto> carrito) {
        this.carrito = carrito;
    }

    public List<List<Producto>> getComprasAntiguas() {
        return comprasAntiguas;
    }

    public Set<Monedero> getMonederos() {
        return monederos;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return ConstantesGestionClientes.ESPACIO_CON_N + ConstantesGestionClientes.CORCHETE1 +
                ConstantesGestionClientes.NOMBRE_DE_USUARIO + nombre + ConstantesGestionClientes.COMILLAS +
                ConstantesGestionClientes.DNI + dni + ConstantesGestionClientes.COMILLAS +
                ConstantesGestionClientes.CORCHETE2;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cliente cliente = (Cliente) o;
        return Objects.equals(dni, cliente.dni) && Objects.equals(nombre, cliente.nombre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dni, nombre);
    }


}