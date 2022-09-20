package servicios.impl;

import data.DaoMonedero;
import jakarta.inject.Inject;
import modelo.Monedero;
import servicios.ServiciosMonedero;

import java.util.List;

public class ServiciosMonederoImpl implements ServiciosMonedero {

    private final DaoMonedero dm;

    @Inject
    public ServiciosMonederoImpl(DaoMonedero dm) {
        this.dm = dm;
    }

    @Override
    public boolean addMonedero(String dni, Monedero m) {
        if (m.getDinero() > 0) {
            return dm.addMonedero(dni, m);
        } else return false;
    }

    @Override
    public boolean addSaldo(String dni, Monedero m) {
        if (m.getDinero() > 0) {
            return dm.addSaldo(dni, m);
        } else return false;
    }

    @Override
    public List<Monedero> verMonederos(String dni) {
        return dm.verMonederos(dni);
    }
}
