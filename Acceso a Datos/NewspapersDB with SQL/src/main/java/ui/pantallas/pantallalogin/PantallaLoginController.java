package ui.pantallas.pantallalogin;

import io.github.palexdev.materialfx.controls.MFXTextField;
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

    @FXML
    private MFXTextField textfieldNombre;

    @FXML
    private MFXTextField textfieldDNI;

    @FXML
    private ImageView logoSQL;

    @FXML
    public void inicioSesion() {
        if (textfieldNombre.getText().equals(ConstantesUI.ROOT) && textfieldDNI.getText().equals(ConstantesUI.ROOT)) {
            this.getPrincipalController().onLoginHecho();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(ConstantesUI.ERROR);
            alert.setHeaderText(ConstantesUI.COULD_NOT_LOG_IN);
            alert.setContentText(ConstantesUI.PLEASE_CHECK_YOUR_CREDENTIALS);
            alert.showAndWait();
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