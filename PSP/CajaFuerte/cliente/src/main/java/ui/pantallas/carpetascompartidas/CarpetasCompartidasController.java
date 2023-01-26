package ui.pantallas.carpetascompartidas;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import jakarta.inject.Inject;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import modelo.Mensaje;
import ui.pantallas.common.BasePantallaController;

import java.net.URL;
import java.util.ResourceBundle;

public class CarpetasCompartidasController extends BasePantallaController implements Initializable {

    private final CarpetasCompartidasViewModel viewModel;

    @Inject
    public CarpetasCompartidasController(CarpetasCompartidasViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @FXML
    private TableView<Mensaje> tablaMensajes;

    @FXML
    private TableColumn<Mensaje, String> columnaID;

    @FXML
    private TableColumn<Mensaje, String> columnaContenido;

    @FXML
    private MFXTextField textoNombreUsuario;

    @FXML
    private MFXTextField textoNombreCarpeta;

    @FXML
    private MFXTextField textoPasswordCarpeta;

    @FXML
    private MFXTextField textoMensaje;

    @FXML
    private MFXButton botonEnviarMensaje;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        columnaID.setCellValueFactory(new PropertyValueFactory<>("id"));
        columnaContenido.setCellValueFactory(new PropertyValueFactory<>("contenido"));

        textoMensaje.setVisible(false);
        botonEnviarMensaje.setVisible(false);

        viewModel.getState().addListener((observableValue, oldState, newState) -> {
            if (newState.mensaje != null) {
                Platform.runLater(() -> getPrincipalController().createAlert(newState.mensaje));
            }
            if (newState.carpeta != null) {
                Platform.runLater(() -> {
                    tablaMensajes.getItems().clear();
                    tablaMensajes.setItems(FXCollections.observableArrayList(newState.carpeta.getMensajes()));
                    if (newState.carpeta.isModoEdicion()) {
                        textoMensaje.setVisible(true);
                        botonEnviarMensaje.setVisible(true);
                    } else {
                        textoMensaje.setVisible(false);
                        botonEnviarMensaje.setVisible(false);
                    }
                });
            }
        });
    }

    public void cargarCarpetaCompartida() {
        String nombreUsuario = textoNombreUsuario.getText();
        String nombreCarpeta = textoNombreCarpeta.getText();
        String passwordCarpeta = textoPasswordCarpeta.getText();
        if (nombreUsuario.isEmpty() || nombreCarpeta.isEmpty() || passwordCarpeta.isEmpty()) {
            getPrincipalController().createAlert("Debe completar todos los campos");
        } else {
            viewModel.cargarCarpetaCompartida(nombreUsuario, nombreCarpeta, passwordCarpeta);
        }
    }

    public void addMensaje() {
        Mensaje mensaje = new Mensaje();
        String contenido = textoMensaje.getText();
        if (!contenido.isEmpty()) {
            mensaje.setContenido(contenido);
            viewModel.addMensaje(mensaje);
            textoMensaje.clear();
        } else {
            getPrincipalController().createAlert("El mensaje no puede estar vacío");
        }
    }

}