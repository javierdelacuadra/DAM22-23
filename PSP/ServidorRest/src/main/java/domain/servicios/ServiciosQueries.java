package domain.servicios;

import dao.DaoQueries;
import jakarta.inject.Inject;

public class ServiciosQueries {

    private final DaoQueries dao;

    @Inject
    public ServiciosQueries(DaoQueries dao) {
        this.dao = dao;
    }
}
