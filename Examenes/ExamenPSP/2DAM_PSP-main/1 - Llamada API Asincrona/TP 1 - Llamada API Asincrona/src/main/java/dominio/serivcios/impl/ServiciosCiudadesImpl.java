package dominio.serivcios.impl;

import dao.impl.DaoCiudadesImpl;
import dominio.modelo.Estado;
import dominio.serivcios.ServiciosCiudades;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import dominio.modelo.Ciudad;
import dominio.modelo.Pais;

import java.util.List;

public class ServiciosCiudadesImpl implements ServiciosCiudades {
    private final DaoCiudadesImpl daoCiudadesImpl;

    @Inject
    public ServiciosCiudadesImpl(DaoCiudadesImpl daoCiudadesImpl) {
        this.daoCiudadesImpl = daoCiudadesImpl;
    }

    @Override
    public Either<String, List<Ciudad>> getCiudadesPorEstado(Estado estado, Pais paisSeleccionado) {
        return daoCiudadesImpl.getCiudadesPorEstado(estado, paisSeleccionado);
    }
}
