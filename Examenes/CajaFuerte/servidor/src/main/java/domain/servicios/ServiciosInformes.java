package domain.servicios;

import dao.DaoExamen;
import domain.exceptions.DatabaseException;
import jakarta.inject.Inject;
import modelo.Informe;

import java.util.List;

public class ServiciosInformes {
    private final DaoExamen dao;

    @Inject
    public ServiciosInformes(DaoExamen dao) {
        this.dao = dao;
    }

    public List<Informe> getInformes(List<String> roles) {
        boolean nivel1 = roles.contains("NIVEL1");
        boolean nivel2 = roles.contains("NIVEL2");
        if (nivel2) {
            return dao.getInformes(true);
        } else if (nivel1) {
            return dao.getInformes(false);
        } else {
            throw new DatabaseException("No tienes permisos para ver los informes");
        }
    }

    public void addInforme(Informe informe) {
        dao.addInforme(informe);
    }

    public Informe getInforme(String nombre, List<String> roles) {
        boolean nivel1 = roles.contains("NIVEL1");
        boolean nivel2 = roles.contains("NIVEL2");
        if (nivel2) {
            return dao.getInforme(nombre, true);
        } else if (nivel1) {
            return dao.getInforme(nombre, false);
        } else {
            throw new DatabaseException("No tienes permisos para ver los informes");
        }
    }
}
