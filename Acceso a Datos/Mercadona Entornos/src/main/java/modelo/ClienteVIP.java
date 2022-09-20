package modelo;

import java.util.Objects;

public class ClienteVIP extends Cliente {

    private final int descuento;

    public ClienteVIP(String dni, String nombre, int descuento) {
        super(dni, nombre);
        this.descuento = descuento;
        type = "ClienteVIP";
    }

    public int getDescuento() {
        return descuento;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        ClienteVIP that = (ClienteVIP) o;
        return descuento == that.descuento;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), descuento);
    }

    @Override
    public Cliente clonar() {
        return null;
    }
}