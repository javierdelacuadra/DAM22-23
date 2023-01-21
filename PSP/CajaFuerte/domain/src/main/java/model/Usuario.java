package model;

import lombok.Data;

import java.util.List;

@Data
public class Usuario {
    private int id;
    private String nombre;
    private String password;
    private String rol;
    private List<Carpeta> carpetas;
}