package model;

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

    public TypeArt(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
