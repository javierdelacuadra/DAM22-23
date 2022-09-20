package data;

import modelo.Monedero;

import java.util.List;

public interface DaoMonedero {
    boolean addSaldo(String dni, Monedero m);

    boolean addMonedero(String dni, Monedero m);

    List<Monedero> verMonederos(String dni);
}
