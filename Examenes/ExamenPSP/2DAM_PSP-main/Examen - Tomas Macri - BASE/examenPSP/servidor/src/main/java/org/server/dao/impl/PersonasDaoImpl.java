package org.server.dao.impl;

import org.server.dao.PersonasDao;
import org.server.dao.common.DaoConstants;
import org.server.domain.modelo.errores.BaseDeDatosException;
import org.server.domain.modelo.errores.InvalidFieldsException;
import org.server.domain.modelo.errores.NoFoundException;
import org.utils.domain.modelo.Persona;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PersonasDaoImpl implements PersonasDao {

    private static final List<Persona> personas = new ArrayList<>();

    @Override public List<Persona> getAll() {
        return personas;
    }

    @Override public Persona getPersona(String index) {
        try {
            int idx = Integer.parseInt(index);
            return personas.get(personas.indexOf(new Persona(idx, null, null, null)));
        }catch (NumberFormatException e) {
            throw new InvalidFieldsException(DaoConstants.EL_ID_DE_LAS_PERSONAS_DEBE_SER_NUMERICO);
        } catch (IndexOutOfBoundsException e){
            throw new NoFoundException(DaoConstants.LA_PERSONA_CON_EL_ID + index + DaoConstants.NO_SE_ENCUENTRA_EN_LA_LISTA);
        }
    }

    @Override public int addPersona(Persona persona) {
        personas.add(persona);
        return 1;
    }

    @Override public int updatePersona(Persona persona) {
        try {
            if (personas.set(personas.indexOf(persona), persona) != null) {
                return 1;
            } else {
                throw new BaseDeDatosException(DaoConstants.NO_SE_HA_PODIDO_ACTUALIZAR_LA_PERSONA_CON_EL_ID + persona.getId());
            }
        }catch (NumberFormatException e) {
            throw new InvalidFieldsException(DaoConstants.EL_ID_DE_LAS_PERSONAS_DEBE_SER_NUMERICO);
        } catch (IndexOutOfBoundsException e){
            throw new NoFoundException(DaoConstants.LA_PERSONA_CON_EL_ID + persona.getId() + DaoConstants.NO_SE_ENCUENTRA_EN_LA_LISTA);
        }
    }

    @Override public int deletePersona(String id){
        try {
            int idx = Integer.parseInt(id);
            Persona persona = new Persona(idx, null, null, null);
            if (personas.contains(persona)) {
                personas.remove(persona);
                return 1;
            } else {
                throw new NoFoundException(DaoConstants.LA_PERSONA_CON_EL_ID + id + DaoConstants.NO_SE_ENCUENTRA_EN_LA_LISTA);
            }
        }catch (NumberFormatException e) {
            throw new InvalidFieldsException(DaoConstants.EL_ID_DE_LAS_PERSONAS_DEBE_SER_NUMERICO);
        } catch (IndexOutOfBoundsException e){
            throw new NoFoundException(DaoConstants.LA_PERSONA_CON_EL_ID + id + DaoConstants.NO_SE_ENCUENTRA_EN_LA_LISTA);
        }
    }

    @Override public List<Persona> getMenores(){
        List<Persona> menores =  personas.stream().filter(persona -> persona.getFechaNacimiento().isAfter(LocalDate.now().minusYears(18))).toList();
        if (!menores.isEmpty()){
            return menores;
        } else {
            throw new NoFoundException(DaoConstants.NO_HAY_MENORES_DE_EDAD_EN_LA_LISTA);
        }
    }

}
