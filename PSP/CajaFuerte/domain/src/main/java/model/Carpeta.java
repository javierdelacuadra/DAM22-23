package model;

import lombok.Data;

import java.util.List;

@Data
public class Carpeta {

    private String nombre;
    private String password;
    private boolean modoEdicion;
    private String nombreUsuario;
    private List<Mensaje> mensajes;
}