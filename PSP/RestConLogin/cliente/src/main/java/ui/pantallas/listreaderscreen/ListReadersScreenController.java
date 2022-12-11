package ui.pantallas.listreaderscreen;

import jakarta.inject.Inject;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Reader;
import ui.common.ConstantesUI;
import ui.pantallas.common.BasePantallaController;

public class ListReadersScreenController extends BasePantallaController {

    private final ListReadersScreenViewModel viewModel;

    @Inject
    public ListReadersScreenController(ListReadersScreenViewModel viewModel) {
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
        viewModel.getState().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                if (newValue.readers != null) {
                    Platform.runLater(() -> readersTable.getItems().setAll(newValue.readers));
                }
                if (newValue.error != null) {
                    Platform.runLater(() -> getPrincipalController().createAlert(newValue.error));
                }
            }
        });
    }
}