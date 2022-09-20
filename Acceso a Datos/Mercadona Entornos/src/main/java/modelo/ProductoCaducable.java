package modelo;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public class ProductoCaducable extends Producto {

    private final LocalDateTime caducidad;

    public ProductoCaducable(String nombre, double precio, int stock, List<Ingrediente> ingredientes, LocalDateTime caducidad) {
        super(nombre, precio, stock, ingredientes);
        this.caducidad = caducidad;
        type = "ProductoCaducable";
    }

    public LocalDateTime getCaducidad() {
        return caducidad;
    }

    @Override
    public String toString() {
        return super.toString() + '{' + caducidad + "caducidad" +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        ProductoCaducable that = (ProductoCaducable) o;
        return Objects.equals(caducidad, that.caducidad);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), caducidad);
    }

    @Override
    public Producto clonar() {
        return null;
    }
}