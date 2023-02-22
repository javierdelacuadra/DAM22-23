package org.miutils.domain.modelo;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Login {
    private String email;
    private String password;
    private int idReader;

    public Login(String email, String password, int idReader) {
        this.email = email;
        this.password = password;
        this.idReader = idReader;
    }

    public Login(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public Login(String email, int id) {
        this.email = email;
        this.idReader = id;
    }
}
