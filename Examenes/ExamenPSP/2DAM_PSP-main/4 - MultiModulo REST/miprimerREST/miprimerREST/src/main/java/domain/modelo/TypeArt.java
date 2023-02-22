package domain.modelo;

import lombok.Data;

@Data
public class TypeArt {
    private int id;
    private String name;

    public TypeArt(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public TypeArt() {
    }

    @Override
    public String toString() {
        return name;
    }
}
