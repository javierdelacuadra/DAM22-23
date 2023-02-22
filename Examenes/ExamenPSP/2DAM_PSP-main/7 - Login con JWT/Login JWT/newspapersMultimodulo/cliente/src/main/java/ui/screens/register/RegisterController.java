package ui.screens.register;

import io.github.palexdev.materialfx.controls.MFXPasswordField;
import io.github.palexdev.materialfx.controls.MFXTextField;
import jakarta.inject.Inject;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import ui.screens.common.BaseScreenController;

public class RegisterController extends BaseScreenController {

    @FXML
    private MFXTextField txtName;

    @FXML
    private DatePicker dpkBirth;

    @FXML
    private MFXTextField mfxUser;

    @FXML
    private MFXPasswordField mfxPassword;

    private final RegisterViewModel registerViewModel;

    @Inject
    public RegisterController(RegisterViewModel registerViewModel) {
        this.registerViewModel = registerViewModel;
    }

    @Override
    public void principalCargado() {
        addListener();
    }

    public void addListener() {
        registerViewModel.getState().addListener((observable, registerState, registerStateNew) ->
                Platform.runLater(() -> {
                    if (registerStateNew.getError() != null) {
                        getPrincipalController().showAlertError(registerStateNew.getError());
                    }
                    if (registerStateNew.getSuccess() != null) {
                        getPrincipalController().showAlertInformation(registerStateNew.getSuccess());
                        getPrincipalController().backToLogin();
                    }
                }));
    }

    public void tryRegister() {
        registerViewModel.register(txtName.getText(), dpkBirth.getValue(), mfxUser.getText(), mfxPassword.getText());
    }

    public void goLogin() {
        getPrincipalController().backToLogin();
    }
}
