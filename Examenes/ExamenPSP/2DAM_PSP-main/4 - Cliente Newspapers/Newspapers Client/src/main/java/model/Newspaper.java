package model;

import lombok.Data;

import java.time.LocalDate;
import java.util.Objects;

@Data
public class Newspaper {
    private int id;
    private String name_newspaper;
    private LocalDate release_date;

    public Newspaper() {
    }

    public Newspaper(int id, String name_newspaper, LocalDate release_date) {
        this.id = id;
        this.name_newspaper = name_newspaper;
        this.release_date = release_date;
    }

    public Newspaper(String name_newspaper, LocalDate release_date) {
        this.name_newspaper = name_newspaper;
        this.release_date = release_date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Newspaper newspaper = (Newspaper) o;
        // id = id || name=name && releaseDate=releaseDate
        return id == newspaper.id ||(Objects.equals(name_newspaper, newspaper.name_newspaper) && Objects.equals(release_date, newspaper.release_date));
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name_newspaper, release_date);
    }

    @Override
    public String toString() {
        return name_newspaper;
    }
}
