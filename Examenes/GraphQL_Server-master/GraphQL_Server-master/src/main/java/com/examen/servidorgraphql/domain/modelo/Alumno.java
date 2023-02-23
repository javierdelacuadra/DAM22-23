package com.examen.servidorgraphql.domain.modelo;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor

@Getter
@Setter
@ToString
public class Alumno {
    private Integer id;
    private String nombre;
}