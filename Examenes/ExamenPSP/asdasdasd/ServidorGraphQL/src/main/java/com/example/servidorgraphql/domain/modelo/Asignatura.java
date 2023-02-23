package com.example.servidorgraphql.domain.modelo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Asignatura {
    private Integer id;
    private String nombre;
    private Integer nota;
}