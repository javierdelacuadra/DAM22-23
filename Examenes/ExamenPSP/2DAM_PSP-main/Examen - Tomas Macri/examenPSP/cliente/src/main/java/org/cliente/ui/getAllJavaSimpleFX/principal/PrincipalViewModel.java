package org.cliente.ui.getAllJavaSimpleFX.principal;

import io.vavr.control.Either;
import jakarta.inject.Inject;
import org.cliente.domain.servicios.ServiciosEquipos;
import org.utils.domain.modelo.Equipo;

import java.util.List;


public class PrincipalViewModel {

    ServiciosEquipos serviciosEquipos;

    @Inject
    public PrincipalViewModel(ServiciosEquipos serviciosEquipos) {
        this.serviciosEquipos = serviciosEquipos;
    }

    public Either<String, List<Equipo>> getAllAsyncTask() {
        return serviciosEquipos.getAllAsynConTask();
    }
}
