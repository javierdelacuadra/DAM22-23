package ui.pantallas.addcarpeta;

import io.github.palexdev.materialfx.controls.MFXCheckbox;
import io.github.palexdev.materialfx.controls.MFXTextField;
import jakarta.inject.Inject;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import model.Carpeta;
import ui.pantallas.common.BasePantallaController;

import java.net.URL;
import java.util.ResourceBundle;

public class AddCarpetaController extends BasePantallaController implements Initializable {

    private final AddCarpetaViewModel viewModel;

    @Inject
    public AddCarpetaController(AddCarpetaViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @FXML
    private MFXTextField textoNombreCarpeta;

    @FXML
    private MFXTextField textoPassCarpeta;

    @FXML
    private MFXCheckbox modoLecturaCheckBox;

    @FXML
    private MFXCheckbox modoEdicionCheckBox;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void addCarpeta() {
        String nombreCarpeta = textoNombreCarpeta.getText();
        String passCarpeta = textoPassCarpeta.getText();
        int IDUsuario = this.getPrincipalController().getUsuario().getId();
        if (nombreCarpeta != null && passCarpeta != null) {
            if (modoLecturaCheckBox.isSelected() && modoEdicionCheckBox.isSelected()) {
                this.getPrincipalController().createAlert("No puedes seleccionar ambos modos");
            } else if (modoLecturaCheckBox.isSelected()) {
                Carpeta carpeta = new Carpeta(nombreCarpeta, passCarpeta, false, IDUsuario);
                viewModel.addCarpeta(carpeta);
            } else if (modoEdicionCheckBox.isSelected()) {
                Carpeta carpeta = new Carpeta(nombreCarpeta, passCarpeta, true, IDUsuario);
                viewModel.addCarpeta(carpeta);
            } else {
                this.getPrincipalController().createAlert("Selecciona un modo");

            }
        } else {
            this.getPrincipalController().createAlert("Rellena todos los campos");
        }
    }
}
