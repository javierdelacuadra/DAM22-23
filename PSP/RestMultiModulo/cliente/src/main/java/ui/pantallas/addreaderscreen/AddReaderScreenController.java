package ui.pantallas.addreaderscreen;

import io.github.palexdev.materialfx.controls.MFXDatePicker;
import io.github.palexdev.materialfx.controls.MFXTextField;
import jakarta.inject.Inject;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import modelo.Reader;
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
    private TableView<Reader> readersTable;

    @FXML
    private TableColumn<Reader, Integer> idColumn;

    @FXML
    private TableColumn<Reader, String> nameColumn;

    @FXML
    private TableColumn<Reader, String> birthDateColumn;

    @FXML
    private MFXTextField nameTextField;

    @FXML
    private MFXTextField passwordField;

    @FXML
    private MFXDatePicker birthDatePicker;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        idColumn.setCellValueFactory(new PropertyValueFactory<>(ConstantesUI.ID));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>(ConstantesUI.NAME));
        birthDateColumn.setCellValueFactory(new PropertyValueFactory<>(ConstantesUI.DATE_OF_BIRTH));

        viewModel.getReaders();

        viewModel.getState().addListener((observableValue, oldState, newState) -> {
            if (newState.error != null) {
                Platform.runLater(() -> {
                    getPrincipalController().createAlert(newState.error);
                });
            }
            if (newState.readers != null) {
                Platform.runLater(() -> {
                    readersTable.getItems().clear();
                    readersTable.getItems().addAll(newState.readers);
                });
            }
        });
    }

    public void saveReader() {
        if (nameTextField.getText().isEmpty() || passwordField.getText().isEmpty() || birthDatePicker.getValue() == null) {
            this.getPrincipalController().createAlert(ConstantesUI.YOU_MUST_FILL_ALL_THE_FIELDS);
        } else {
            if (!nameTextField.getText().isEmpty() || !passwordField.getText().isEmpty() || birthDatePicker.getValue() != null) {
                Reader reader = new Reader(nameTextField.getText(), birthDatePicker.getValue());
                viewModel.addReader(reader);
            }
        }
    }
}
