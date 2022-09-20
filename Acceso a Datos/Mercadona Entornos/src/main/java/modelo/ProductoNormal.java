package modelo;

import java.util.List;
import java.util.stream.Collectors;

public class ProductoNormal extends Producto {

    public ProductoNormal(String nombre, double precio, int stock, List<Ingrediente> ingredientes) {
        super(nombre, precio, stock, ingredientes);
        type = "ProductoNormal";
    }

    public ProductoNormal(String nombre, int stock) {
        super(nombre, stock);
        type = "ProductoNormal";
    }

    public ProductoNormal(String nombre, double precio, int stock) {
        super(nombre, precio, stock);
        type = "ProductoNormal";
    }

    public ProductoNormal(String nombre) {
        super(nombre);
        type = "ProductoNormal";
    }

    public String toString() {
        return super.toString();
    }

    @Override
    public Producto clonar() {
        return new ProductoNormal(super.getNombre(), super.getPrecio(), super.getStock(), super.getIngredientes()
                .stream()
                .map(Ingrediente::clonar)
                .collect(Collectors.toUnmodifiableList()));
    }
}