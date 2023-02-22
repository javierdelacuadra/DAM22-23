package org.miutils.domain.modelo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Reader {
    private int id;
    private String name;
    private LocalDate birthDate;

    private Login login;

    public Reader(int id, String name, LocalDate birthDate) {
        this.id = id;
        this.name = name;
        this.birthDate = birthDate;
    }


    public Reader(String name, LocalDate birthDate) {
        this.name = name;
        this.birthDate = birthDate;
    }

    public Reader(String name, LocalDate birthDate, Login login) {
        this.name = name;
        this.birthDate = birthDate;
        this.login = login;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reader reader = (Reader) o;
        return id == reader.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
