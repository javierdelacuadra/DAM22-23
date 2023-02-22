package ui.screens.login;

import io.github.palexdev.materialfx.controls.MFXPasswordField;
import io.github.palexdev.materialfx.controls.MFXTextField;
import jakarta.inject.Inject;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import ui.screens.common.BaseScreenController;

public class LoginController extends BaseScreenController {

    @FXML
    private MFXTextField mfxUser;

    @FXML
    private MFXPasswordField mfxPassword;

        private LoginViewModel loginViewModel;

        @Inject
        public LoginController(LoginViewModel loginViewModel) {
            this.loginViewModel = loginViewModel;
        }

        @Override
        public void principalCargado(){
            addListenerState();
        }
        public void tryLogin(ActionEvent actionEvent) {
            loginViewModel.tryLogin(mfxUser.getText(), mfxPassword.getText());
        }

    private void addListenerState() {
        loginViewModel.getState().addListener((observable, principalState, principalStateNew) -> {
            if (principalStateNew.getReaderLogged() != null) {
                getPrincipalController().onLoginHecho(principalStateNew.getReaderLogged());
            }
            if (principalStateNew.getError() != null) {
                getPrincipalController().showAlertError(principalStateNew.getError());
            }
        });
    }

}
