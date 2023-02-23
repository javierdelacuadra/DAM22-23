package com.examen.domain.servicios;

import com.examen.data.ExamenRepository;
import com.examen.domain.modelo.Asignatura;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiciosAsignaturas {

    private final ExamenRepository examenRepository;

    public ServiciosAsignaturas(ExamenRepository examenRepository) {
        this.examenRepository = examenRepository;
    }

    public List<Asignatura> getAllByAlumnoId(Integer id) {
        return examenRepository.getAsignaturas().stream()
                .filter(asignatura -> asignatura.getIdAlumno().equals(id))
                .toList();
    }
}
