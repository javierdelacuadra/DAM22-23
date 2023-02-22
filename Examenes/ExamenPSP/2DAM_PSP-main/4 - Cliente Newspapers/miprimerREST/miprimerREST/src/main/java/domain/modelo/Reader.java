package domain.modelo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

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
}
