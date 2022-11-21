package modelo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Jugador {
    private String nombre;
    private String nombreEquipo;
    private int id;

    public Jugador(String nombre, String nombreEquipo) {
        this.nombre = nombre;
        this.nombreEquipo = nombreEquipo;
    }
}