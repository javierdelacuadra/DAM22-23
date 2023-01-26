package ui.pantallas.addcarpeta;

import io.github.palexdev.materialfx.controls.MFXCheckbox;
import io.github.palexdev.materialfx.controls.MFXTextField;
import jakarta.inject.Inject;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import modelo.Carpeta;
import ui.pantallas.common.BasePantallaController;
import ui.pantallas.common.constantes.ConstantesPantallas;

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
                this.getPrincipalController().createAlert(ConstantesPantallas.NO_PUEDES_SELECCIONAR_AMBOS_MODOS);
            } else if (modoLecturaCheckBox.isSelected()) {
                Carpeta carpeta = new Carpeta(nombreCarpeta, passCarpeta, false, IDUsuario);
                String mensaje = viewModel.addCarpeta(carpeta);
                this.getPrincipalController().createAlert(mensaje);
            } else if (modoEdicionCheckBox.isSelected()) {
                Carpeta carpeta = new Carpeta(nombreCarpeta, passCarpeta, true, IDUsuario);
                viewModel.addCarpeta(carpeta);
            } else {
                this.getPrincipalController().createAlert(ConstantesPantallas.SELECCIONA_UN_MODO);

            }
        } else {
            this.getPrincipalController().createAlert(ConstantesPantallas.RELLENA_TODOS_LOS_CAMPOS);
        }
    }
}
