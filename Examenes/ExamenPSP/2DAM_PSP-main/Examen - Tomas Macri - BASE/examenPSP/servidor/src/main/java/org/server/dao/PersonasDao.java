package org.server.dao;

import org.utils.domain.modelo.Persona;

import java.util.List;

public interface PersonasDao {
    List<Persona> getAll();

    Persona getPersona(String index);

    int addPersona(Persona persona);

    int updatePersona(Persona persona);

    int deletePersona(String id);

    List<Persona> getMenores();
}
