package domain.servicios;

import dao.DaoCalls;
import jakarta.inject.Inject;

public class ServiciosCalls {

    private final DaoCalls dao;

    @Inject
    public ServiciosCalls(DaoCalls dao) {
        this.dao = dao;
    }

    public int getCallsByName(String name) {
        return dao.getCallsByName(name);
    }

    public void registerCall(String name) {
        dao.registerCall(name);
    }
}
