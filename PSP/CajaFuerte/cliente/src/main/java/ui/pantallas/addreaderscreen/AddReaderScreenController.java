package ui.pantallas.addreaderscreen;

import io.github.palexdev.materialfx.controls.MFXTextField;
import jakarta.inject.Inject;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import modelo.Reader;
import modelo.Usuario;
import ui.common.ConstantesUI;
import ui.pantallas.common.BasePantallaController;

import java.net.URL;
import java.util.ResourceBundle;

public class AddReaderScreenController extends BasePantallaController implements Initializable {

    private final AddReaderScreenViewModel viewModel;

    @Inject
    public AddReaderScreenController(AddReaderScreenViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @FXML
    private TableView<Usuario> readersTable;

    @FXML
    private TableColumn<Reader, Integer> idColumn;

    @FXML
    private TableColumn<Reader, String> nameColumn;

    @FXML
    private TableColumn<Reader, String> rolColumn;

    @FXML
    private MFXTextField nameTextField;

    @FXML
    private MFXTextField passwordField;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        idColumn.setCellValueFactory(new PropertyValueFactory<>(ConstantesUI.ID));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        rolColumn.setCellValueFactory(new PropertyValueFactory<>("rol"));

        viewModel.getUsuarios();

        viewModel.getState().addListener((observableValue, oldState, newState) -> {
            if (newState.error != null) {
                Platform.runLater(() -> getPrincipalController().createAlert(newState.error));
            }
            if (newState.usuarios != null) {
                Platform.runLater(() -> {
                    readersTable.getItems().clear();
                    readersTable.setItems(FXCollections.observableArrayList(newState.usuarios));
                });
            }
        });
    }

    public void saveUsuario() {
        if (nameTextField.getText().isEmpty() || passwordField.getText().isEmpty()) {
            this.getPrincipalController().createAlert(ConstantesUI.YOU_MUST_FILL_ALL_THE_FIELDS);
        } else {
            if (!nameTextField.getText().isEmpty() || !passwordField.getText().isEmpty()) {
                Usuario usuario = new Usuario(nameTextField.getText(), passwordField.getText());
                viewModel.addUsuario(usuario);
            }
        }
    }
}
