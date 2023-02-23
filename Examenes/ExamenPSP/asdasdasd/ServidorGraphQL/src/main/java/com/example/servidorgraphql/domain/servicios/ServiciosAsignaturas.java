package com.example.servidorgraphql.domain.servicios;

import com.example.servidorgraphql.data.ExamenRepository;
import com.example.servidorgraphql.domain.modelo.Asignatura;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiciosAsignaturas {

    private final ExamenRepository examenRepository;

    public ServiciosAsignaturas(ExamenRepository examenRepository) {
        this.examenRepository = examenRepository;
    }

    public List<Asignatura> getAllByAlumnoId(Integer id) {
        return examenRepository.getAllAlumnos().stream()
                .filter(alumno -> alumno.getId().equals(id))
                .findFirst()
                .orElseThrow()
                .getAsignaturas();
    }
}
