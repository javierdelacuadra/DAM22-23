package com.serverql.graphqlserver.domain.modelo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Persona {

    private int id;

    private String name;

    private String surname;

    private Login login;
    private List<Mascota> mascotas;

    public Persona(int id, String name, String surname) {
        this.id = id;
        this.name = name;
        this.surname = surname;
    }

    public Persona(String name, String surname) {
        this.name = name;
        this.surname = surname;
    }
}
