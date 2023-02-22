package ui.screens.principal;

import lombok.Data;

@Data
public class PrincipalState {

    private String error;
    private String success;

    public PrincipalState(String error, String success) {
        this.error = error;
        this.success = success;
    }
}
