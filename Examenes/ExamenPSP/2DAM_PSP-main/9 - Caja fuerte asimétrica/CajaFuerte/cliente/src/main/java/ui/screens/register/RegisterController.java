package ui.screens.register;

import io.github.palexdev.materialfx.controls.MFXTextField;
import jakarta.inject.Inject;
import javafx.application.Platform;
import javafx.fxml.FXML;
import ui.screens.common.BaseScreenController;

public class RegisterController extends BaseScreenController {

    private final RegisterViewModel registerViewModel;

    @FXML
    private MFXTextField mfxName;
    @FXML
    private MFXTextField mfxPassword;

    @Inject
    public RegisterController(RegisterViewModel registerViewModel) {
        this.registerViewModel = registerViewModel;
    }

    @Override
    public void principalCargado() {
        registerViewModel.getState().addListener((observableValue, registerState, newValue) -> Platform.runLater(() -> {
                    if (newValue.getError() != null) {
                        getPrincipalController().showAlertError(newValue.getError());

                    }
                    if (newValue.getSuccess() != null) {
                        getPrincipalController().showAlertInformation(newValue.getSuccess());
                        mfxName.setText("");
                        mfxPassword.setText("");
                    }
                    registerViewModel.clearMessages();
                })
        );
    }

    public void addUser() {
        registerViewModel.register(mfxName.getText(), mfxPassword.getText());
    }

    public void backToLogin() {
        getPrincipalController().backToLogin();
    }
}
