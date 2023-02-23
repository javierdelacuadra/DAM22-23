package com.examen.servidorgraphql.domain.servicios;

import com.examen.servidorgraphql.domain.modelo.Alumno;
import com.examen.servidorgraphql.data.ExamenRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiciosAlumnos {

    private final ExamenRepository repository;

    public ServiciosAlumnos(ExamenRepository repository) {
        this.repository = repository;
    }

    public List<Alumno> getAll() {
        return repository.getAllAlumnos();
    }

    public void save(Alumno alumno) {
        repository.saveAlumno(alumno);
    }
}
