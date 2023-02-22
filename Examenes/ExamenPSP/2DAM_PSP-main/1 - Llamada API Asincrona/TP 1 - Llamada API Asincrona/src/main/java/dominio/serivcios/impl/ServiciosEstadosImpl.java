package dominio.serivcios.impl;

import dao.DaoEstados;
import dominio.modelo.Estado;
import dominio.serivcios.ServiciosEstados;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import dominio.modelo.Pais;

import java.util.List;

public class ServiciosEstadosImpl implements ServiciosEstados {

    private final DaoEstados daoEstadosImpl;

    @Inject
    public ServiciosEstadosImpl(DaoEstados daoEstadosImpl) {
        this.daoEstadosImpl = daoEstadosImpl;
    }


    @Override
    public Either<String, List<Estado>> getEstadosPorPais(Pais paisSeleccionado) {
        return daoEstadosImpl.getEstadosPorPais(paisSeleccionado);
    }
}
