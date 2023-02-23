package modelo;

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
    private String role;

    public ReaderLogin(String username, String password, String email, int id_reader, String activation_code, int active, LocalDateTime registration_date) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.id_reader = id_reader;
        this.activation_code = activation_code;
        this.active = active;
        this.registration_date = registration_date;
    }
}
