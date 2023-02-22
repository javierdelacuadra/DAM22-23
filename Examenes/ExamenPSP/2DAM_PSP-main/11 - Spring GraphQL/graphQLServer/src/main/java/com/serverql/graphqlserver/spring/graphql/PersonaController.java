package com.serverql.graphqlserver.spring.graphql;

import com.serverql.graphqlserver.domain.excepciones.DataIntegrityException;
import com.serverql.graphqlserver.domain.modelo.Login;
import com.serverql.graphqlserver.domain.modelo.Mascota;
import com.serverql.graphqlserver.domain.modelo.Persona;
import com.serverql.graphqlserver.domain.servicios.ServiciosPersona;
import com.serverql.graphqlserver.domain.servicios.common.ServicesConstants;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;

@Controller
public class PersonaController {

    private final ServiciosPersona serviciosPersona;


    public PersonaController(ServiciosPersona serviciosPersona) {
        this.serviciosPersona = serviciosPersona;
    }

    @QueryMapping
    public List<Persona> allPersons() {
        return serviciosPersona.getAllPersonas();
    }

    @MutationMapping
    public Persona createPersona(@Argument String name, @Argument String surname, @Argument String username, @Argument String password) {
//        mutation {
//            createPersona(
//                    name:"nombre"
//            surname:"apellido"
//      ){
//                id
//                name
//                surname
//            }
//
//        }
        try {
            Persona p = new Persona();
            p.setId(0);
            p.setName(name);
            p.setSurname(surname);
            p.setLogin(new Login(username, password, "USER"));
            p.setMascotas(new ArrayList<>());
            // Al ser una función @Transactional, no saltará la excepción DataIntegrityViolationException
            // hasta que se haga commit, cuando termina la función
            serviciosPersona.createPersona(p);
            return p;
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityException(ServicesConstants.LA_PERSONA_YA_EXISTE);
        }
    }


    @MutationMapping
    public Persona updatePersonaLogueada(@Argument String name, @Argument String surname) {
        return serviciosPersona.updatePersonaLogueada(new Persona(name, surname));
    }

    @MutationMapping
    public Persona deletePersona(@Argument int id) {
        return serviciosPersona.deletePersona(id);
    }

    @MutationMapping
    public Persona deletePersonaLogueada() {
        return serviciosPersona.deletePersonaLogueada();
    }

    @SchemaMapping
    public Persona persona(Mascota mascota) {
        return serviciosPersona.getPersonaById(mascota.getPersona());
    }

    @SchemaMapping
    public Login login(Persona persona) {
        return persona.getLogin();
    }

    @QueryMapping
    public Persona getPersonaByUsernameLogged() {
        return serviciosPersona.getPersonaByUsername();
    }

}
