package ui.pantallas.updatereaderscreen;

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

public class UpdateReaderController extends BasePantallaController implements Initializable {

    private final UpdateReaderViewModel viewModel;

    @Inject
    public UpdateReaderController(UpdateReaderViewModel viewModel) {
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

    public void updateReader() {
        if (readersTable.getSelectionModel().getSelectedItem() != null) {
            Reader reader = new Reader(
                    readersTable.getSelectionModel().getSelectedItem().getId(),
                    nameTextField.getText(),
                    birthDatePicker.getValue()
            );
            viewModel.updateReader(reader);
            nameTextField.setText(ConstantesUI.ANY);
            birthDatePicker.setValue(null);
        }
    }

    public void fillTextFields() {
        if (readersTable.getSelectionModel().getSelectedItem() != null) {
            Reader reader = readersTable.getSelectionModel().getSelectedItem();
            nameTextField.setText(reader.getName());
            birthDatePicker.setValue(reader.getDateOfBirth());
        }
    }
}