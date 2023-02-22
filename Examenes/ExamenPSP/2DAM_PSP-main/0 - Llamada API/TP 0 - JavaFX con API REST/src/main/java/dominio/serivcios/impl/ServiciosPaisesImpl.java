package dominio.serivcios.impl;

import dao.DaoPaises;
import dominio.modelo.PaisDetalle;
import dominio.serivcios.ServiciosPaises;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import dominio.modelo.Pais;

import java.util.List;

public class ServiciosPaisesImpl implements ServiciosPaises {

    private final DaoPaises daoPaisesImpl;

    @Inject
    public ServiciosPaisesImpl(DaoPaises daoPaisesImpl) {
        this.daoPaisesImpl = daoPaisesImpl;
    }

    @Override
    public Either<String, List<Pais>> getTodosLosPaises() {
        return daoPaisesImpl.getTodosLosPaises();
    }

    @Override
    public Either<String, Pais> getPais(String pais) {
        return daoPaisesImpl.getPais(pais);
    }

    @Override
    public Either<String, PaisDetalle> getPaisDetalle(String iso) {
        return daoPaisesImpl.getPaisDetalle(iso);
    }
}
