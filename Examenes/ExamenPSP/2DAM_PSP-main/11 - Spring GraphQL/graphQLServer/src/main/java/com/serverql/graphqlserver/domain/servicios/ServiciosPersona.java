package com.serverql.graphqlserver.domain.servicios;

import com.serverql.graphqlserver.dao.LoginDao;
import com.serverql.graphqlserver.dao.PersonasDao;
import com.serverql.graphqlserver.dao.modelo.DataMapper;
import com.serverql.graphqlserver.dao.modelo.PersonaDTO;
import com.serverql.graphqlserver.domain.excepciones.DataIntegrityException;
import com.serverql.graphqlserver.domain.excepciones.NotFoundException;
import com.serverql.graphqlserver.domain.modelo.Persona;
import com.serverql.graphqlserver.domain.servicios.common.ServicesConstants;
import com.serverql.graphqlserver.spring.security.PasswordEncoder;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ServiciosPersona {
    private final PersonasDao personasDao;

    private final PasswordEncoder passwordEncoder;

    private final LoginDao loginDao;

    private final DataMapper dataMapper;

    public ServiciosPersona(PersonasDao newspapersDao, PasswordEncoder passwordEncoder, LoginDao loginDao, DataMapper dataMapper) {
        this.personasDao = newspapersDao;
        this.passwordEncoder = passwordEncoder;
        this.loginDao = loginDao;
        this.dataMapper = dataMapper;
    }


    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public List<Persona> getAllPersonas() {
        return personasDao.findAll()
                .stream()
                .map(dataMapper::personaDTOToPersona)
                .toList();
    }

    @Transactional
    public Persona getFullPersonaById(Long id) {
        return personasDao.findByIdComplete(id)
                .map(dataMapper::personaDTOToPersona)
                .orElseThrow(() -> new NotFoundException(ServicesConstants.LA_PERSONA_NO_EXISTE));

    }

    @Transactional
    public Persona getPersonaById(int id) {
        return personasDao.findById(id)
                .map(dataMapper::personaDTOToPersona)
                .orElseThrow(() -> new NotFoundException(ServicesConstants.LA_PERSONA_NO_EXISTE));

    }

    @Transactional
    public void createPersona(Persona persona) {
        //Hash password
        persona.getLogin().setPassword(passwordEncoder.getBCryptPasswordEncoder().encode(persona.getLogin().getPassword()));

        PersonaDTO personaDTO = dataMapper.persontaToPersonaDTO(persona);
        personaDTO.setId(0);
        personaDTO.setLogin(dataMapper.loginToLoginDTO(persona.getLogin(), personaDTO));
        //Para añadirlo
        personaDTO = personasDao.save(personaDTO);
        persona.setId(personaDTO.getId());
    }

    @PreAuthorize("isAuthenticated()")
    @Transactional
    public Persona updatePersonaLogueada(Persona persona) {
        persona.setId(getPersonaByUsername().getId()); //Seleccionamos el id de la persona logueada
        int numeroFilasUpdate = personasDao.updateById(persona.getName(), persona.getSurname(), persona.getId());
        if (numeroFilasUpdate == 0) {
            throw new NotFoundException(ServicesConstants.LA_PERSONA_NO_EXISTE);
        }
        return persona;
    }


    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public Persona deletePersona(int id) {
        try {
            Persona persona = getPersonaById(id);
            loginDao.deleteByUsername(persona.getLogin().getUsername());
            personasDao.deleteById(id);
            return persona;
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundException(ServicesConstants.LA_PERSONA_NO_EXISTE);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityException(ServicesConstants.NO_SE_PUEDE_ELIMINAR_LA_PERSONA_PORQUE_TIENE_MASCOTAS_ASOCIADAS);
        }
    }

    @Transactional
    @PreAuthorize("isAuthenticated()")
    public Persona deletePersonaLogueada() {
        try {
            Persona persona = getPersonaByUsername();
            loginDao.deleteByUsername(persona.getLogin().getUsername());
            personasDao.deleteById(persona.getId());
            return persona;
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundException(ServicesConstants.LA_PERSONA_NO_EXISTE);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityException(ServicesConstants.NO_SE_PUEDE_ELIMINAR_LA_PERSONA_PORQUE_TIENE_MASCOTAS_ASOCIADAS);
        }
    }

    @PreAuthorize("isAuthenticated()")
    public Persona getPersonaByUsername() {
//         get the username of the logged user
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Persona p = loginDao.findByPersonaByUsername(username)
                .map(dataMapper::personaDTOToPersona)
                .orElseThrow(() -> new NotFoundException(ServicesConstants.LA_PERSONA_NO_EXISTE));
        p.getLogin().setRole(SecurityContextHolder.getContext().getAuthentication().getAuthorities().toArray()[0].toString());
        return p;
    }
}
