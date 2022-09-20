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
import modelo.ClienteNormal;
import ui.pantallas.common.BasePantallaController;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

@Log4j2
public class PantallaLoginController extends BasePantallaController implements Initializable {

    private PantallaLoginViewModel viewModel;

    @Inject
    public PantallaLoginController(PantallaLoginViewModel viewModel) {
        this.viewModel = viewModel;
    }

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
        if ((!Objects.equals(textfieldDNI.getText(), "") && !Objects.equals(textfieldNombre.getText(), "")) || textfieldNombre.getText().equalsIgnoreCase("admin")) {
            viewModel.iniciarSesion(textfieldNombre.getText(), textfieldDNI.getText());
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("El nombre o el DNI están vacíos");
            alert.setContentText("Escribe un nombre y un DNI para iniciar sesión");
            alert.showAndWait();
        }
    }

    @FXML
    public void registro() {
        String nombre = textfieldNombre.getText();
        String dni = textfieldDNI.getText();
        viewModel.registro(nombre, dni);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try (var inputStream = getClass().getResourceAsStream("/images/Mercadona-Logo.png")) {
            assert inputStream != null;
            Image logoImage = new Image(inputStream);
            logoMercadona.setImage(logoImage);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        viewModel.getState().addListener((observableValue, oldState, newState) -> {
            if (newState.getError() != null) {
                this.getPrincipalController().sacarAlertError(newState.getError());
            }
            if (newState.isLoginOK()) {
                this.getPrincipalController().onLoginHecho(new ClienteNormal(textfieldDNI.getText(), textfieldNombre.getText()));
            }
        });
    }
}