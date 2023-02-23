package com.examen.servidorgraphql.domain.modelo;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor

@Getter
@Setter
@ToString
public class Asignatura {
    private Integer idAlumno;
    private String nombre;
    private Integer nota;
}