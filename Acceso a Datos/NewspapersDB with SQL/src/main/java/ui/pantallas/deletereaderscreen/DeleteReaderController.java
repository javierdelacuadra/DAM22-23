package ui.pantallas.deletereaderscreen;

import jakarta.inject.Inject;
import jakarta.xml.bind.JAXBException;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Reader;
import ui.pantallas.common.BasePantallaController;

public class DeleteReaderController extends BasePantallaController {

    private final DeleteReaderViewModel viewModel;

    @Inject
    public DeleteReaderController(DeleteReaderViewModel viewModel) {
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

    public void initialize() throws JAXBException {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        birthDateColumn.setCellValueFactory(new PropertyValueFactory<>("dateOfBirth"));
        readersTable.setItems(viewModel.getReaders());
    }

    public void deleteReader() throws JAXBException {
        Reader reader = readersTable.getSelectionModel().getSelectedItem();
        if (viewModel.deleteReader(reader).isRight()) {
            readersTable.getItems().clear();
            readersTable.setItems(viewModel.getReaders());
        } else {
            this.getPrincipalController().createAlert(viewModel.deleteReader(reader).getLeft().toString());
        }
    }
}
