package modelo;

import ui.common.ConstantesClientes;
import ui.common.ConstantesGestionClientes;

import java.util.Objects;

public class Monedero {
    private String nombre;
    private double dinero;

    public Monedero(String nombre, double dinero) {
        this.nombre = nombre;
        this.dinero = dinero;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getDinero() {
        return dinero;
    }

    public void setDinero(double dinero) {
        this.dinero = dinero;
    }

    @Override
    public String toString() {
        return ConstantesGestionClientes.CORCHETE1 +
                ConstantesClientes.NOMBRE_MONEDERO + nombre + ConstantesGestionClientes.COMILLAS +
                ConstantesClientes.DINERO_MONEDERO + dinero +
                ConstantesGestionClientes.CORCHETE2;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Monedero monedero = (Monedero) o;
        return Objects.equals(nombre, monedero.nombre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nombre);
    }
}
