package ui.pantallas.updatereaderscreen;

import io.github.palexdev.materialfx.controls.MFXDatePicker;
import io.github.palexdev.materialfx.controls.MFXTextField;
import jakarta.inject.Inject;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Reader;
import ui.common.ConstantesUI;
import ui.pantallas.common.BasePantallaController;

import java.net.URL;
import java.time.LocalDate;
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
    private MFXTextField passwordTextField;

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
//            Login login = readersTable.getSelectionModel().getSelectedItem().getLogin();
//            login.setPassword(passwordTextField.getText());
            Reader reader = new Reader(
                    readersTable.getSelectionModel().getSelectedItem().getId(),
                    readersTable.getSelectionModel().getSelectedItem().getName(),
                    birthDatePicker.getValue().toString());
            int result = viewModel.updateReader(reader);
            if (result == 1) {
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
        passwordTextField.setText(ConstantesUI.ANY);
    }

    public void fillTextFields() {
        if (readersTable.getSelectionModel().getSelectedItem() != null) {
            Reader reader = readersTable.getSelectionModel().getSelectedItem();
            birthDatePicker.setValue(LocalDate.parse(reader.getDateOfBirth()));
//            passwordTextField.setText(reader.getLogin().getPassword());
        }
    }
}