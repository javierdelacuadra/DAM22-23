package servicios;

import modelo.Monedero;

import java.util.List;

public interface ServiciosMonedero {
    boolean addMonedero(String dni, Monedero m);

    boolean addSaldo(String dni, Monedero m);

    List<Monedero> verMonederos(String dni);
}
