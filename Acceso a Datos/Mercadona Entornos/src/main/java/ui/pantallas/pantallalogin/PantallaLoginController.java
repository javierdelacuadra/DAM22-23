package ui.pantallas.pantallalogin;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import lombok.extern.log4j.Log4j2;
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
    private Label textoNombre;

    @FXML
    private Label textoDNI;

    @FXML
    private MFXButton botonInicioSesion;

    @FXML
    private MFXButton botonRegistro;

    @FXML
    private ImageView logoSQL;

    @FXML
    public void inicioSesion() {
        if (textfieldNombre.getText().equals("root") || textfieldDNI.getText().equals("root")) {
            this.getPrincipalController().onLoginHecho();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error al iniciar sesión");
            alert.setContentText("El nombre o el DNI son incorrectos");
            alert.showAndWait();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try (var inputStream = getClass().getResourceAsStream("/images/MySQL-Logo.png")) {
            assert inputStream != null;
            Image logoImage = new Image(inputStream);
            logoSQL.setImage(logoImage);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }
}