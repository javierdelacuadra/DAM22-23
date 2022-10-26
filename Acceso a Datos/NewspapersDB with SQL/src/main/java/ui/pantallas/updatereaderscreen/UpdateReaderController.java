package ui.pantallas.updatereaderscreen;

import io.github.palexdev.materialfx.controls.MFXDatePicker;
import io.github.palexdev.materialfx.controls.MFXTextField;
import jakarta.inject.Inject;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import model.Reader;
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
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        birthDateColumn.setCellValueFactory(new PropertyValueFactory<>("dateOfBirth"));
        readersTable.setItems(viewModel.getReaders());
    }

    public void updateReader() {
        if (readersTable.getSelectionModel().getSelectedItem() != null) {
            Reader reader = new Reader(
                    readersTable.getSelectionModel().getSelectedItem().getId(),
                    nameTextField.getText(),
                    birthDatePicker.getValue()
            );
            if (viewModel.updateReader(reader).isRight()) {
                readersTable.getItems().clear();
                readersTable.setItems(viewModel.getReaders());
            } else {
                this.getPrincipalController().createAlert(viewModel.updateReader(reader).getLeft().toString());
            }
        } else {
            this.getPrincipalController().createAlert("No reader was selected");
        }
    }

    public void fillTextFields(MouseEvent mouseEvent) {
        if (readersTable.getSelectionModel().getSelectedItem() != null) {
            Reader reader = readersTable.getSelectionModel().getSelectedItem();
            nameTextField.setText(reader.getName());
            birthDatePicker.setValue(reader.getDateOfBirth());
        }
    }
}