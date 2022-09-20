package ui.pantallas.pantalladminclientes;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import jakarta.inject.Inject;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import modelo.Cliente;
import modelo.ClienteNormal;
import ui.pantallas.common.BasePantallaController;

import java.net.URL;
import java.util.ResourceBundle;

public class PantallaAdminClientesController extends BasePantallaController implements Initializable {

    private final PantallaAdminClientesViewModel viewModel;

    @Inject
    public PantallaAdminClientesController(PantallaAdminClientesViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @FXML
    private MFXButton botonadd;

    @FXML
    private MFXButton botonremove;

    @FXML
    private MFXButton botonupdate;

    @FXML
    private TableView<Cliente> tablaclientes;

    @FXML
    private TableColumn<Cliente, String> columnaNombre;

    @FXML
    private TableColumn<Cliente, String> columnaDNI;

    @FXML
    private MFXTextField textoNombre;

    @FXML
    private MFXTextField textoDNI;

    @FXML
    public void addCliente() {
        if (!textoDNI.getText().isEmpty() && !textoNombre.getText().isEmpty()) {
            Cliente c = new ClienteNormal(textoDNI.getText(), textoNombre.getText());
            viewModel.addCliente(c);
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error");
            alert.setContentText("No puede dejar campos vacíos");
            alert.showAndWait();
        }
    }

    @FXML
    public void removeCliente() {
        if (tablaclientes.getSelectionModel().getSelectedItem() != null) {
            Cliente c = tablaclientes.getSelectionModel().getSelectedItem();
            viewModel.eliminarCliente(c);
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error");
            alert.setContentText("No ha seleccionado ningún cliente");
            alert.showAndWait();
        }
    }

    @FXML
    public void updateCliente() {
        if (tablaclientes.getSelectionModel().getSelectedItem() != null && !textoDNI.getText().isEmpty() && !textoNombre.getText().isEmpty()) {
            Cliente c1 = new ClienteNormal(textoDNI.getText(), textoNombre.getText());
            Cliente c2 = tablaclientes.getSelectionModel().getSelectedItem();
            viewModel.modificarCliente(c1, c2);
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error");
            alert.setContentText("No ha seleccionado ningún cliente o no puede dejar campos vacíos");
            alert.showAndWait();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tablaclientes.setItems(viewModel.getClientes());
        columnaNombre.setCellValueFactory(new PropertyValueFactory<>("Nombre"));
        columnaDNI.setCellValueFactory(new PropertyValueFactory<>("dni"));
    }
}