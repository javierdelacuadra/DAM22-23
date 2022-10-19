package ui.pantallas.addreaderscreen;

import io.github.palexdev.materialfx.controls.MFXDatePicker;
import io.github.palexdev.materialfx.controls.MFXTextField;
import jakarta.inject.Inject;
import jakarta.xml.bind.JAXBException;
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
    private MFXDatePicker birthDatePicker;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        birthDateColumn.setCellValueFactory(new PropertyValueFactory<>("dateOfBirth"));
        readersTable.setItems(viewModel.getReaders());
    }

    public void saveReader(MouseEvent mouseEvent) {
        Reader reader = new Reader(0, nameTextField.getText(), birthDatePicker.getValue());
        if (viewModel.addReader(reader).isRight()) {
            readersTable.getItems().clear();
            readersTable.setItems(viewModel.getReaders());
        } else {
            this.getPrincipalController().createAlert(viewModel.addReader(reader).getLeft().toString());
        }
    }
}
