package ui.pantallas.pantallalogin;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import jakarta.inject.Inject;
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
import java.util.Objects;
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
    private ImageView logoMercadona;


    @FXML
    public void inicioSesion() {
        this.getPrincipalController().onLoginHecho();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try (var inputStream = getClass().getResourceAsStream("/images/MySQL-Logo.png")) {
            assert inputStream != null;
            Image logoImage = new Image(inputStream);
            logoMercadona.setImage(logoImage);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }
}