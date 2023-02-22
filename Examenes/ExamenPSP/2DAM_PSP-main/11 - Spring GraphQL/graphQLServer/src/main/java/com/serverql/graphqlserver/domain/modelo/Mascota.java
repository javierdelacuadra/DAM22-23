package com.serverql.graphqlserver.domain.modelo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@AllArgsConstructor
@NoArgsConstructor
public class Mascota {

    private int id;

    private String name;

    private String type;
    private int age;

    private int persona;
}
