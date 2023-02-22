package com.serverql.graphqlserver.dao.modelo;

import com.serverql.graphqlserver.domain.modelo.Login;
import com.serverql.graphqlserver.domain.modelo.Mascota;
import com.serverql.graphqlserver.domain.modelo.Persona;
import org.springframework.stereotype.Component;

@Component
public class DataMapper {

    public PersonaDTO persontaToPersonaDTO(Persona persona) {
        return new PersonaDTO(persona.getId(), persona.getName(), persona.getSurname(),  persona.getMascotas().stream().map(this::mascotaToMascotaDTO).toList());
    }

    public LoginDTO loginToLoginDTO(Login login, PersonaDTO personaDTO) {
        return new LoginDTO(login.getUsername(), login.getPassword(), login.getRole(), personaDTO);
    }

    public Login loginDTOToLogin(LoginDTO loginDTO) {
        return new Login(loginDTO.getUsername(), loginDTO.getPassword(), loginDTO.getRole());
    }

    public Persona personaDTOToPersona(PersonaDTO personaDTO) {
        return new Persona(personaDTO.getId(), personaDTO.getName(), personaDTO.getSurname(), this.loginDTOToLogin(personaDTO.getLogin()), personaDTO.getMascotas().stream().map(this::mascotaDTOToMascota).toList());
    }

    public MascotaDTO mascotaToMascotaDTO(Mascota mascota) {
        return new MascotaDTO(mascota.getId(), mascota.getName(), mascota.getType(), mascota.getAge());
    }

    public Mascota mascotaDTOToMascota(MascotaDTO mascotaDTO) {
        return new Mascota(mascotaDTO.getId(), mascotaDTO.getName(), mascotaDTO.getType(), mascotaDTO.getAge(), mascotaDTO.getPersona().getId());
    }


}
