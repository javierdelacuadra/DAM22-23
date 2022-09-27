package modelo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Newspaper {
    private int id;
    private String name;
    private String releaseDate;

    public Newspaper(String line) {
        String[] split = line.split(";");
        this.id = Integer.parseInt(split[0]);
        this.name = split[1];
        this.releaseDate = split[2];
    }

    public String toLine() {
        return id + ";" + name + ";" + releaseDate + "\n";
    }
}
