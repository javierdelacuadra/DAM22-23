package com.examen.data;

import com.examen.domain.modelo.Alumno;
import com.examen.domain.modelo.Asignatura;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ExamenRepository {

    static List<Alumno> alumnos = new ArrayList<>();
    static List<Asignatura> asignaturas = new ArrayList<>();

    static {
        Alumno alumno = new Alumno(1, "Juan");
        Alumno alumno2 = new Alumno(2, "Pedro");
        Alumno alumno3 = new Alumno(3, "Luis");
        alumnos.add(alumno);
        alumnos.add(alumno2);
        alumnos.add(alumno3);
        Asignatura asignatura1 = new Asignatura(1, "Matemáticas", 2);
        Asignatura asignatura2 = new Asignatura(2, "Lengua", 6);
        Asignatura asignatura3 = new Asignatura(3, "Historia", 8);
        Asignatura asignatura4 = new Asignatura(1, "Geografía", 4);
        Asignatura asignatura5 = new Asignatura(2, "Física", 7);
        Asignatura asignatura6 = new Asignatura(3, "Química", 9);
        asignaturas.add(asignatura1);
        asignaturas.add(asignatura2);
        asignaturas.add(asignatura3);
        asignaturas.add(asignatura4);
        asignaturas.add(asignatura5);
        asignaturas.add(asignatura6);
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
        List<Integer> alumnosIds = new ArrayList<>();
        for (Alumno alumno1 : alumnos) {
            alumnosIds.add(alumno1.getId());
        }
        int maxId = alumnosIds.stream().mapToInt(v -> v).max().orElse(0);
        alumno.setId(maxId + 1);
        alumnos.add(alumno);
    }

    public List<Asignatura> getAsignaturas() {
        return asignaturas;
    }
}