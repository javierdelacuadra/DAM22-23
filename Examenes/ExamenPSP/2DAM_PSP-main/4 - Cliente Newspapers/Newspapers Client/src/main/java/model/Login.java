package model;

import lombok.Data;

@Data
public class Login {
    private String user;
    private String password;
    private int idReader;

    public Login(String user, String password, int idReader) {
        this.user = user;
        this.password = password;
        this.idReader = idReader;
    }

    public Login(String user, String password) {
        this.user = user;
        this.password = password;
    }
}
