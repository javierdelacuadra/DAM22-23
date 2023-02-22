package ui.screens.login;

import lombok.Data;
import org.utils.domain.modelo.User;

@Data
public class LoginState {

    private String error;

    private User userLogged;

    private String success;

    public LoginState(String error, User userLogged, String success) {
        this.error = error;
        this.userLogged = userLogged;
        this.success = success;
    }
}
