package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReaderLogin {
    private String username;
    private String password;
    private String email;
    private int id_reader;
    private String activation_code;
    private int active;
    private LocalDateTime registration_date;

    public ReaderLogin(String nombre, String password, String email) {
        this.username = nombre;
        this.password = password;
        this.email = email;
    }
}
