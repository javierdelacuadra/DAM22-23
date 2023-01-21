package model;

import lombok.Data;

import java.util.List;

@Data
public class Carpeta {

    private int id;
    private String nombreCarpeta;
    private String password;
    private boolean modoEdicion;
    private String nombreUsuario;
    private List<Mensaje> mensajes;
}