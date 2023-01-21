package model;

import lombok.Data;

@Data
public class Mensaje {

    private int id;
    private String nombreCarpeta;
    private String contenido;
}