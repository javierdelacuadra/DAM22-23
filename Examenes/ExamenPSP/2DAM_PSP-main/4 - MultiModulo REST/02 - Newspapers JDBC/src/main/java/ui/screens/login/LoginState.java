package ui.screens.login;

import lombok.Data;
import model.Reader;

@Data
public class LoginState {

    private String error;

    private Reader readerLogged;

    public LoginState(String error, Reader readerLogged) {
        this.error = error;
        this.readerLogged = readerLogged;
    }
}
