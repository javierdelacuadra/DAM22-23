package modelo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private String nombre;
    private String password;
    private List<String> roles;

    public User(String nombre, String pass) {
        this.nombre = nombre;
        this.password = pass;
    }
}
