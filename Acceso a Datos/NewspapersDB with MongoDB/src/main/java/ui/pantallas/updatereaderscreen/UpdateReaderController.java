package ui.pantallas.updatereaderscreen;

import io.github.palexdev.materialfx.controls.MFXDatePicker;
import io.github.palexdev.materialfx.controls.MFXTextField;
import jakarta.inject.Inject;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Reader;
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
    private TableColumn<Reader, String> cancellationDateColumn;

    @FXML
    private MFXTextField nameTextField;

    @FXML
    private MFXDatePicker birthDatePicker;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        cancellationDateColumn.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setText(null);
                } else {
                    setText(item == null ? "No cancellation date" : item);
                }
            }
        });
        readersTable.setItems(viewModel.getReaders());
    }

    public void updateReader() {
        if (readersTable.getSelectionModel().getSelectedItem() != null || birthDatePicker.getValue() != null) {
            Reader reader = new Reader(
                    readersTable.getSelectionModel().getSelectedItem().getId(),
                    nameTextField.getText(),
                    "");


            int result = viewModel.updateReader(reader);
            if (result >= 1) {
                readersTable.getItems().clear();
                readersTable.setItems(viewModel.getReaders());
            } else if (result == -1) {
                this.getPrincipalController().createAlert(ConstantesUI.THERE_WAS_AN_ERROR_UPDATING_THE_READER);
            } else {
                this.getPrincipalController().createAlert(ConstantesUI.THE_READER_DOES_NOT_EXIST);
            }
        } else {
            this.getPrincipalController().createAlert(ConstantesUI.YOU_HAVEN_T_SELECTED_ANY_READER);
        }
        birthDatePicker.setValue(null);
        nameTextField.setText(ConstantesUI.ANY);
    }

    public void fillTextFields() {
        if (

                readersTable.getSelectionModel().getSelectedItem() != null) {
            Reader reader = readersTable.getSelectionModel().getSelectedItem();
//            birthDatePicker.setValue(LocalDate.parse(reader.getDateOfBirth()));
            nameTextField.setText(reader.getName());
        }
    }
}