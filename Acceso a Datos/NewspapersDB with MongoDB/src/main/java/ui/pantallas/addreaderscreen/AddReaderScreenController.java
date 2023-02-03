package ui.pantallas.addreaderscreen;

import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import io.github.palexdev.materialfx.controls.MFXTextField;
import jakarta.inject.Inject;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Newspaper;
import model.Reader;
import ui.common.ConstantesUI;
import ui.pantallas.common.BasePantallaController;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicInteger;

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

    @FXML
    private MFXComboBox<Newspaper> newspaperComboBox;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        birthDateColumn.setCellValueFactory(new PropertyValueFactory<>("dateOfBirth"));
        readersTable.setItems(viewModel.getReaders());
        newspaperComboBox.setItems(viewModel.getNewspapers());
    }

    public void saveReader() {
        if (nameTextField.getText().isEmpty() || passwordField.getText().isEmpty() || birthDatePicker.getValue() == null || newspaperComboBox.getValue() == null) {
            this.getPrincipalController().createAlert(ConstantesUI.YOU_MUST_FILL_ALL_THE_FIELDS);
        } else {
            if (!nameTextField.getText().isEmpty() || !passwordField.getText().isEmpty() || birthDatePicker.getValue() != null) {
                Reader reader = new Reader(nameTextField.getText(), birthDatePicker.getValue().toString());
                List<Reader> readers = readersTable.getItems();
                AtomicInteger maxId = new AtomicInteger();
                readers.stream().filter(reader1 -> reader1.getId() > maxId.get()).forEach(reader1 -> maxId.set(reader1.getId()));
                reader.setId(maxId.get() + 1);
                Newspaper newspaper = newspaperComboBox.getValue();
                int result = viewModel.addReader(reader, newspaper);
                if (result == 1) {
                    readersTable.getItems().clear();
                    readersTable.setItems(viewModel.getReaders());
                } else {
                    if (result == -1) {
                        this.getPrincipalController().createAlert(ConstantesUI.SOMETHING_UNEXPECTED_HAPPENED);
                    } else if (result == -2) {
                        this.getPrincipalController().createAlert(ConstantesUI.DATE_FORMAT_IS_NOT_CORRECT_OR_IS_EMPTY);
                    } else if (result == -3) {
                        this.getPrincipalController().createAlert(ConstantesUI.THERE_IS_ALREADY_A_READER_WITH_THAT_NAME_AND_BIRTH_DATE);
                    }
                }
            }
        }
    }
}
