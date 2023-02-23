package com.example.servidorgraphql.domain.modelo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Alumno {
    private Integer id;
    private String nombre;
    private List<Asignatura> asignaturas;
}