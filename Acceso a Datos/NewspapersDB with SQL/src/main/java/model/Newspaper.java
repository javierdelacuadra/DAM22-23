package model;

import common.Constantes;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Newspaper {
    private int id;
    private String name;
    private String releaseDate;

    public Newspaper(String line) {
        String[] split = line.split(Constantes.PUNTO_Y_COMA);
        this.id = Integer.parseInt(split[0]);
        this.name = split[1];
        this.releaseDate = split[2];
    }

    public String toLine() {
        return id + Constantes.PUNTO_Y_COMA + name + Constantes.PUNTO_Y_COMA + releaseDate + Constantes.ESPACIO;
    }

    @Override
    public String toString() {
        return id +
                ", name='" + name + '\'' +
                ", Date='" + releaseDate + '\'';
    }
}
