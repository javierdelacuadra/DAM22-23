package ui.pantallas.deletereaderscreen;

import jakarta.inject.Inject;
import jakarta.xml.bind.JAXBException;
import javafx.fxml.FXML;
import javafx.scene.control.*;
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
    private TableColumn<Reader, String> cancellationDateColumn;

    public void initialize() throws JAXBException {
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

    public void deleteReader() {
        Reader reader = readersTable.getSelectionModel().getSelectedItem();
        if (reader != null) {
            int result = viewModel.deleteReader(reader, false);
            if (result >= 1) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "The reader was deleted successfully", ButtonType.OK);
                alert.showAndWait();
                readersTable.setItems(viewModel.getReaders());
            } else if (result == -1) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "There was an error deleting the reader", ButtonType.OK);
                alert.showAndWait();
            } else if (result == -2) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation");
                alert.setHeaderText("Reader has subscriptions");
                alert.setContentText("Do you want to delete reader and subscriptions?");
                alert.showAndWait().ifPresent(response -> {
                    if (response == ButtonType.OK) {
                        int resultado = viewModel.deleteReader(reader, true);
                        if (resultado == 1) {
                            Alert alert2 = new Alert(Alert.AlertType.INFORMATION, "The reader and the subscriptions were deleted successfully", ButtonType.OK);
                            alert2.showAndWait();
                            readersTable.setItems(viewModel.getReaders());
                        } else {
                            Alert alert2 = new Alert(Alert.AlertType.ERROR, "There was an error deleting the reader", ButtonType.OK);
                            alert2.showAndWait();
                        }
                    }
                });
            }
        } else {
            this.getPrincipalController().createAlert(ConstantesUI.YOU_HAVEN_T_SELECTED_ANY_READER);
        }
    }
}
