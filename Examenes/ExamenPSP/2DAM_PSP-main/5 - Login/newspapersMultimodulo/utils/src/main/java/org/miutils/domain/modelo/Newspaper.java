package org.miutils.domain.modelo;

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

    public Newspaper(int id, String nameNews, LocalDate releaseDate) {
        this.id = id;
        this.name_newspaper = nameNews;
        this.release_date = releaseDate;
    }

    public Newspaper(String nameNews, LocalDate releaseDate) {
        this.name_newspaper = nameNews;
        this.release_date = releaseDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Newspaper newspaper = (Newspaper) o;
        return id == newspaper.id || (Objects.equals(name_newspaper, newspaper.name_newspaper) && Objects.equals(Newspaper.this.release_date, newspaper.release_date));
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name_newspaper, Newspaper.this.release_date);
    }

    @Override
    public String toString() {
        return name_newspaper;
    }
}
