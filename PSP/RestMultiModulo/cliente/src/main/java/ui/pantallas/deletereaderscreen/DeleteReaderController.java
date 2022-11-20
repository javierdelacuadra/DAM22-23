package ui.pantallas.deletereaderscreen;

import jakarta.inject.Inject;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import modelo.Reader;
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

    public void initialize() {
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

    public void deleteReader() {
        Reader reader = readersTable.getSelectionModel().getSelectedItem();
        if (reader != null) {
            if (viewModel.deleteReader(String.valueOf(reader.getId())).isRight()) {
                readersTable.getItems().removeIf(reader1 -> reader1.getId() == reader.getId());
            }
        } else {
            this.getPrincipalController().createAlert(ConstantesUI.YOU_HAVEN_T_SELECTED_ANY_READER);
        }
    }
}
