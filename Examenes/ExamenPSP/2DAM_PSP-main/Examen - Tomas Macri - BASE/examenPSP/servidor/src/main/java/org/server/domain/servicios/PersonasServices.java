package org.server.domain.servicios;

import org.utils.domain.modelo.Persona;

import java.util.List;

public interface PersonasServices {
    List<Persona> getAll();

    Persona getPersona(String index);

    Persona addPersona(Persona persona);

    Persona updatePersona(Persona persona);

    boolean deletePersona(String id);

    List<Persona> getMenores();
}
