package com.serverql.graphqlserver.domain.servicios;

import com.serverql.graphqlserver.dao.MascotasDao;
import com.serverql.graphqlserver.dao.PersonasDao;
import com.serverql.graphqlserver.dao.modelo.DataMapper;
import com.serverql.graphqlserver.dao.modelo.MascotaDTO;
import com.serverql.graphqlserver.domain.excepciones.DataIntegrityException;
import com.serverql.graphqlserver.domain.excepciones.NotFoundException;
import com.serverql.graphqlserver.domain.modelo.Mascota;
import com.serverql.graphqlserver.domain.modelo.Persona;
import com.serverql.graphqlserver.domain.servicios.common.ServicesConstants;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiciosMascota {

    private final MascotasDao mascotasDao;

    private final DataMapper dataMapper;
    private final PersonasDao personasDao;

    public ServiciosMascota(MascotasDao mascotasDao, DataMapper dataMapper,
                            PersonasDao personasDao) {
        this.mascotasDao = mascotasDao;
        this.dataMapper = dataMapper;
        this.personasDao = personasDao;
    }

    public List<Mascota> getMascotasByPersonaId(int id) {
        List<MascotaDTO> mascotaDTOS = mascotasDao.findByPersonaId(id);
        return mascotaDTOS.stream().map(dataMapper::mascotaDTOToMascota).toList();
    }


    @PreAuthorize("hasRole('ADMIN')")
    public List<Mascota> getAll() {
        return mascotasDao.findAll().stream().map(dataMapper::mascotaDTOToMascota).toList();
    }

    public List<Mascota> getAllByUserLogged(Persona persona){
        return mascotasDao.findByPersonaId(persona.getId()).stream().map(dataMapper::mascotaDTOToMascota).toList();
    }

    public void createMascota(Mascota m) {
        MascotaDTO mascotaDTO = dataMapper.mascotaToMascotaDTO(m);
        personasDao.findById(m.getPersona()).ifPresent(mascotaDTO::setPersona);
        if (mascotaDTO.getPersona() == null)
            throw new NotFoundException(ServicesConstants.LA_PERSONA_NO_EXISTE);
        mascotaDTO = mascotasDao.save(mascotaDTO);
        m.setId(mascotaDTO.getId());
    }

    @PreAuthorize("isAuthenticated()")
        public Mascota updateMascota(Mascota m) {
            MascotaDTO mascotaDTO = mascotasDao.findById(m.getId()).orElseThrow(() -> new NotFoundException(ServicesConstants.LA_MASCOTA_NO_EXISTE));
            if (isOwnerOrAdmin(mascotaDTO.getPersona().getLogin().getUsername())) {
                mascotaDTO.setName(m.getName());
                mascotaDTO.setType(m.getType());
                mascotaDTO.setAge(m.getAge());
                mascotaDTO = mascotasDao.save(mascotaDTO);
                return dataMapper.mascotaDTOToMascota(mascotaDTO);
            }
            throw new DataIntegrityException(ServicesConstants.LA_MASCOTA_NO_ES_EDITABLE);
        }

        @PreAuthorize("isAuthenticated()")
        public Mascota deleteMascota(int id) {
            MascotaDTO mascotaDTO = mascotasDao.findById(id).orElseThrow(() -> new NotFoundException(ServicesConstants.LA_MASCOTA_NO_EXISTE));
        if (isOwnerOrAdmin(mascotaDTO.getPersona().getLogin().getUsername())) {
            mascotasDao.deleteById(id);
            return dataMapper.mascotaDTOToMascota(mascotaDTO);
        }
        throw new DataIntegrityException(ServicesConstants.LA_MASCOTA_NO_ES_ELIMINABLE);
    }

    private boolean isOwnerOrAdmin(String username) {
        return username.equals(SecurityContextHolder.getContext().getAuthentication().getName())
                || SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
    }
}
