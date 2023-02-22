package dominio.modelo;

import java.util.Objects;

public record Pais(int id, String nombre, String iso2) {

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pais pais = (Pais) o;
        // Pueden tener el mismo nombre o el mismo ISO, depende de la pantalla tendremos un valor o el otro
        return Objects.equals(nombre, pais.nombre) || Objects.equals(iso2, pais.iso2);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nombre, iso2);
    }

    @Override
    public String toString() {
        return nombre;
    }
}

