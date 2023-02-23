package com.example.servidorgraphql.data;

import com.example.servidorgraphql.domain.modelo.Alumno;
import com.example.servidorgraphql.domain.modelo.Asignatura;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ExamenRepository {

    static List<Alumno> alumnos = new ArrayList<>();
    static List<Asignatura> asignaturas = new ArrayList<>();

    static {
        Asignatura asignatura1 = new Asignatura(1, "Matemáticas", 2);
        Asignatura asignatura2 = new Asignatura(2, "Lengua", 6);
        Asignatura asignatura3 = new Asignatura(3, "Historia", 8);
        Asignatura asignatura4 = new Asignatura(4, "Geografía", 7);
        Asignatura asignatura5 = new Asignatura(5, "Física", 4);
        Alumno alumno = new Alumno(1, "Juan", List.of(asignatura1, asignatura2));
        Alumno alumno2 = new Alumno(2, "Pedro", List.of(asignatura3, asignatura4));
        Alumno alumno3 = new Alumno(3, "Luis", List.of(asignatura5, asignatura1));
        alumnos.add(alumno);
        alumnos.add(alumno2);
        alumnos.add(alumno3);
        asignaturas.add(asignatura1);
        asignaturas.add(asignatura2);
        asignaturas.add(asignatura3);
        asignaturas.add(asignatura4);
        asignaturas.add(asignatura5);
    }

    private ExamenRepository() {
    }

    public static List<Alumno> getAllAlumnos() {
        return alumnos;
    }

    public static List<String> getAllNombresAlumnos() {
        List<String> nombres = new ArrayList<>();
        for (Alumno alumno : alumnos) {
            nombres.add(alumno.getNombre());
        }
        return nombres;
    }

    public static void saveAlumno(Alumno alumno) {
        alumnos.add(alumno);
    }
}