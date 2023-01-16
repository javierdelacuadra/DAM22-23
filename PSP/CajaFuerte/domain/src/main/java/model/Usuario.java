package model;

import lombok.Data;

import java.util.List;

@Data
public class Usuario {
    private String username;
    private String password;
    private List<Carpeta> carpetas;
}