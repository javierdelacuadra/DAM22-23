package modelo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Carpeta {

    private int id;
    private String nombreCarpeta;
    private String password;
    private boolean modoEdicion;
    private int IDUsuario;
    private List<Mensaje> mensajes;

    public Carpeta(String nombreCarpeta, String password, boolean modoEdicion, int IDUsuario) {
        this.nombreCarpeta = nombreCarpeta;
        this.password = password;
        this.modoEdicion = modoEdicion;
        this.IDUsuario = IDUsuario;
    }
}