package ui.screens.login;

import io.github.palexdev.materialfx.controls.MFXPasswordField;
import io.github.palexdev.materialfx.controls.MFXTextField;
import jakarta.inject.Inject;
import javafx.application.Platform;
import javafx.fxml.FXML;
import ui.screens.common.BaseScreenController;

public class LoginController extends BaseScreenController {

    @FXML
    private MFXTextField mfxUser;

    @FXML
    private MFXPasswordField mfxPassword;

    private final LoginViewModel loginViewModel;

    @Inject
    public LoginController(LoginViewModel loginViewModel) {
        this.loginViewModel = loginViewModel;
    }

    @Override
    public void principalCargado() {
        addListenerState();
    }

    public void tryLogin() {
        loginViewModel.tryLogin(mfxUser.getText(), mfxPassword.getText());
    }

    private void addListenerState() {
        loginViewModel.getState().addListener((observable, principalState, principalStateNew) ->
                Platform.runLater(() -> {
                    if (principalStateNew.getReaderLogged() != null) {
                        getPrincipalController().onLoginHecho(principalStateNew.getReaderLogged());
                    }
                    if (principalStateNew.getError() != null) {
                        getPrincipalController().showAlertError(principalStateNew.getError());
                    }
                    if (principalStateNew.getSuccess() != null){
                        getPrincipalController().showAlertInformation(principalStateNew.getSuccess());
                    }

                }));
    }

    public void loginInvitado() {
        loginViewModel.loginInvitado();
    }

    public void forgot() {
        loginViewModel.forgotPassword(mfxUser.getText());
    }

    public void goToRegistro() {
        getPrincipalController().register();
    }
}
