package modelo;

public class Ingrediente implements Clonable<Ingrediente> {

    private final String nombre;

    public Ingrediente(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public Ingrediente clonar() {
        return new Ingrediente(this.nombre);
    }
}