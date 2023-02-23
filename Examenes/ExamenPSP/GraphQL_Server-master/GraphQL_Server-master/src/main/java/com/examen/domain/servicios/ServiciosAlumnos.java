package com.examen.domain.servicios;

import com.examen.data.ExamenRepository;
import com.examen.domain.modelo.Alumno;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiciosAlumnos {

    public ServiciosAlumnos() {
    }

    public List<Alumno> getAll() {
        return ExamenRepository.getAllAlumnos();
    }

    public List<String> getAllNombres() {
        return ExamenRepository.getAllNombresAlumnos();
    }

    public void save(Alumno alumno) {
        ExamenRepository.saveAlumno(alumno);
    }
}
