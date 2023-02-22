package ui.screens.register;

import lombok.Data;

@Data
public class RegisterState {
    private String error;
    private String success;

    public RegisterState(String error, String success) {
        this.error = error;
        this.success = success;
    }
}
