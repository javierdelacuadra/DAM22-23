package modelo;

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

    @Override
    public String toString() {
        return id +
                ", name='" + name + '\'' +
                ", Date='" + release_date + '\'';
    }
}
