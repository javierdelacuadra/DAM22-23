package model;

import common.Constantes;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Newspaper {
    private int id;
    private String name;
    private String release_date;

    public Newspaper(String line) {
        String[] split = line.split(Constantes.PUNTO_Y_COMA);
        this.id = Integer.parseInt(split[0]);
        this.name = split[1];
        this.release_date = split[2];
    }

    @Override
    public String toString() {
        return id +
                ", name='" + name + '\'' +
                ", Date='" + release_date + '\'';
    }
}
