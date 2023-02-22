package ui.screens.login;

import lombok.Data;
import org.miutils.domain.modelo.Reader;

@Data
public class LoginState {

    private String error;

    private Reader readerLogged;

    private String success;

    public LoginState(String error, Reader readerLogged, String success) {
        this.error = error;
        this.readerLogged = readerLogged;
        this.success = success;
    }
}
