package model;

import lombok.Data;

@Data
public class Mensaje {

    private String contenido;
    private String nombreCarpeta;
    private String nombreUsuario;
}