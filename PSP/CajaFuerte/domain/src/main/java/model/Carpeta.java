package model;

import lombok.Data;

import java.util.List;

@Data
public class Carpeta {

    private String nombre;
    private String password;
    private int modo;
    private List<Mensaje> mensajes;
}