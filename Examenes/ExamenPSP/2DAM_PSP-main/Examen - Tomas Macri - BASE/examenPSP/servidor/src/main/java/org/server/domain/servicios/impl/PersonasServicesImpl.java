package org.server.domain.servicios.impl;

import jakarta.inject.Inject;
import org.server.dao.impl.PersonasDaoImpl;
import org.server.domain.modelo.errores.BaseDeDatosException;
import org.server.domain.modelo.errores.InvalidFieldsException;
import org.server.domain.servicios.PersonasServices;
import org.server.domain.servicios.common.ServConst;
import org.utils.domain.modelo.Persona;

import java.time.LocalDate;
import java.util.List;

public class PersonasServicesImpl implements PersonasServices {

    private final PersonasDaoImpl personasDao;

    @Inject
    public PersonasServicesImpl(PersonasDaoImpl personasDao) {
        this.personasDao = personasDao;
    }

    @Override
    public List<Persona> getAll() {
        return personasDao.getAll();
    }

    @Override
    public Persona getPersona(String index) {
        return personasDao.getPersona(index);
    }

    @Override
    public Persona addPersona(Persona persona) {
        validations(persona);
        if (personasDao.addPersona(persona) == 1) {
            return persona;
        } else {
            throw new BaseDeDatosException(ServConst.NO_SE_HA_PODIDO_ANADIR_LA_PERSONA);
        }
    }

    @Override
    public Persona updatePersona(Persona persona) {
        validations(persona);
        if (personasDao.updatePersona(persona) == 1) {
            return persona;
        } else {
            throw new BaseDeDatosException(ServConst.NO_SE_HA_PODIDO_ACTUALIZAR_LA_PERSONA);
        }
    }

    @Override
    public boolean deletePersona(String id) {
        return personasDao.deletePersona(id) == 1;
    }

    @Override
    public List<Persona> getMenores() {
        return personasDao.getMenores();
    }

    private void validations(Persona persona){
        try {
            if (persona.getId() < 0 || persona.getNombre().equals("") || persona.getFechaNacimiento() == null
                    || persona.getPassword().equals("")) {
                throw new InvalidFieldsException(ServConst.LOS_CAMPOS_NO_PUEDEN_ESTAR_VACIOS);
            } else if (persona.getFechaNacimiento().isAfter(LocalDate.now())) {
                throw new InvalidFieldsException(ServConst.LA_FECHA_DE_NACIMIENTO_NO_PUEDE_SER_POSTERIOR_A_LA_FECHA_ACTUAL);
            }
        } catch (NullPointerException e) {
            throw new InvalidFieldsException(ServConst.LOS_CAMPOS_NO_PUEDEN_ESTAR_VACIOS);
        }
    }
}
