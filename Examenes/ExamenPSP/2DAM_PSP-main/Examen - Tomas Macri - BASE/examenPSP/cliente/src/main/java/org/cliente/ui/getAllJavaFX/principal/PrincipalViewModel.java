package org.cliente.ui.getAllJavaFX.principal;

import io.vavr.control.Either;
import jakarta.inject.Inject;
import org.cliente.domain.modelo.PersonaCliente;
import org.cliente.domain.servicios.impl.ServiciosPersona;

import java.util.List;


public class PrincipalViewModel {

    ServiciosPersona serviciosPersona;

    @Inject
    public PrincipalViewModel(ServiciosPersona serviciosPersona) {
        this.serviciosPersona = serviciosPersona;
    }

    public Either<String, List<PersonaCliente>> getAllAsyncTask() {
        return serviciosPersona.getAllAsynConTask();
    }
}
