package ui.pantallas.deletereaderscreen;

import jakarta.inject.Inject;
import jakarta.xml.bind.JAXBException;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Reader;
import ui.common.ConstantesUI;
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

    public void deleteReader() {
        Reader reader = readersTable.getSelectionModel().getSelectedItem();
        if (reader != null) {
            if (viewModel.deleteReader(reader).isRight()) {
                readersTable.getItems().clear();
                readersTable.setItems(viewModel.getReaders());
            } else if (viewModel.deleteReader(reader).getLeft() == -1) {
                this.getPrincipalController().createAlert(ConstantesUI.ERROR_DELETING_READER);
            } else if (viewModel.deleteReader(reader).getLeft() == -2) {
                this.getPrincipalController().createAlert(ConstantesUI.READER_NOT_FOUND);
            }
        } else {
            this.getPrincipalController().createAlert(ConstantesUI.YOU_HAVEN_T_SELECTED_ANY_READER);
        }
    }
}
