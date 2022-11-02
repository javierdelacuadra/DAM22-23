package ui.pantallas.pantallalogin;

import io.github.palexdev.materialfx.controls.MFXTextField;
import jakarta.inject.Inject;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import lombok.extern.log4j.Log4j2;
import ui.common.ConstantesUI;
import ui.pantallas.common.BasePantallaController;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

@Log4j2
public class PantallaLoginController extends BasePantallaController implements Initializable {

    private final LoginViewModel viewModel;

    @Inject
    public PantallaLoginController(LoginViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @FXML
    private MFXTextField textfieldNombre;

    @FXML
    private MFXTextField textfieldPassword;

    @FXML
    private ImageView logoSQL;

    @FXML
    public void inicioSesion() {
        String nombre = textfieldNombre.getText();
        String password = textfieldPassword.getText();

        if (nombre == null || nombre.isEmpty() || password == null || password.isEmpty()) {
            this.getPrincipalController().createAlert(ConstantesUI.PLEASE_CHECK_YOUR_CREDENTIALS);
        } else {
            Integer id = viewModel.login(nombre, password);
            if (id == -2) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle(ConstantesUI.ERROR);
                alert.setHeaderText(ConstantesUI.COULD_NOT_LOG_IN);
                alert.setContentText(ConstantesUI.PLEASE_CHECK_YOUR_CREDENTIALS);
                alert.showAndWait();
            } else if (id == -3) {
                this.getPrincipalController().createAlert(ConstantesUI.THERE_WAS_AN_ERROR_TRYING_TO_LOG_IN);
            } else {
                this.getPrincipalController().setReader(viewModel.getReader(id));
                this.getPrincipalController().onLoginHecho(id <= 0);
            }
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try (var inputStream = getClass().getResourceAsStream(ConstantesUI.MY_SQL_LOGO_PATH)) {
            assert inputStream != null;
            Image logoImage = new Image(inputStream);
            logoSQL.setImage(logoImage);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }
}