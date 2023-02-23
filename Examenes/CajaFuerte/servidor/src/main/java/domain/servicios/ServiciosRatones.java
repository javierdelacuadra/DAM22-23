package domain.servicios;

import dao.DaoExamen;
import modelo.Raton;

import java.util.List;

public class ServiciosRatones {

    public ServiciosRatones() {
    }

    public List<Raton> getRatones() {
        return DaoExamen.getRatones();
    }

    public void addRaton(Raton raton) {
        DaoExamen.addRaton(raton);
    }
}
