package com.examen.servidorgraphql.spring.controllers;

import com.examen.servidorgraphql.domain.modelo.Alumno;
import com.examen.servidorgraphql.domain.modelo.Asignatura;
import com.examen.servidorgraphql.domain.servicios.ServiciosAlumnos;
import com.examen.servidorgraphql.domain.servicios.ServiciosAsignaturas;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class AlumnosController {

    private final ServiciosAlumnos serviciosAlumnos;
    private final ServiciosAsignaturas serviciosAsignaturas;

    public AlumnosController(ServiciosAlumnos serviciosAlumnos, ServiciosAsignaturas serviciosAsignaturas) {
        this.serviciosAlumnos = serviciosAlumnos;
        this.serviciosAsignaturas = serviciosAsignaturas;
    }

    @QueryMapping
    public List<Alumno> getAllAlumnos() {
        return serviciosAlumnos.getAll();
    }

    @MutationMapping
    public Alumno addAlumno(@Argument String nombre) {
        Alumno alumno = new Alumno();
        alumno.setNombre(nombre);
        serviciosAlumnos.save(alumno);
        return alumno;
    }

    @SchemaMapping
    public List<Asignatura> asignaturas(Alumno alumno) {
        return serviciosAsignaturas.getAllByAlumnoId(alumno.getId());
    }

}