package org.cliente.domain.servicios.impl;

import io.reactivex.rxjava3.core.Single;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import org.cliente.dao.DaoPersonas;
import org.cliente.domain.modelo.PersonaCliente;
import org.utils.domain.modelo.Persona;
import retrofit2.Call;
import retrofit2.Response;

import java.util.List;

public class ServiciosPersona {

    private final DaoPersonas daoPersonas;

    @Inject
    public ServiciosPersona(DaoPersonas daoPersonas) {
        this.daoPersonas = daoPersonas;
    }

    public Either<String, List<PersonaCliente>> getAllAsynConTask() {
        return daoPersonas.getAllCall();
    }

    public Single<Either<String, PersonaCliente>> getUnaConSingleEnDAO(int id) {
        return daoPersonas.getUnaPersona(id);
    }

    public Either<String, PersonaCliente> updateSingleEnUI(PersonaCliente persona) {
        if (persona.getPassword().equals(persona.getConfirmPassword())) {
            return daoPersonas.updatePersonaCall(persona);
        } else {
            return Either.left("Las contraseñas no coinciden");
        }
    }

    public Single<Either<String, Response<Object>>> deleteSingleEnDAO(int id) {
        return daoPersonas.deletePersona(id);
    }


}
