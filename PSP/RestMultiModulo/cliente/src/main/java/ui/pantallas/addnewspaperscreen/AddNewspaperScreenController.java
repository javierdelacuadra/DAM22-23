package ui.pantallas.addnewspaperscreen;

import io.github.palexdev.materialfx.controls.MFXDatePicker;
import io.github.palexdev.materialfx.controls.MFXTextField;
import jakarta.inject.Inject;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import modelo.Newspaper;
import ui.common.ConstantesUI;
import ui.pantallas.common.BasePantallaController;

import java.net.URL;
import java.util.ResourceBundle;

public class AddNewspaperScreenController extends BasePantallaController implements Initializable {

    private final AddNewspaperScreenViewModel viewModel;

    @Inject
    public AddNewspaperScreenController(AddNewspaperScreenViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @FXML
    private TableView<Newspaper> newspaperTable;

    @FXML
    private TableColumn<Newspaper, Integer> idColumn;

    @FXML
    private TableColumn<Newspaper, String> nameColumn;

    @FXML
    private TableColumn<Newspaper, String> releaseDateColumn;

    @FXML
    private MFXTextField nameTextField;

    @FXML
    private MFXDatePicker releaseDatePicker;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        idColumn.setCellValueFactory(new PropertyValueFactory<>(ConstantesUI.ID));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>(ConstantesUI.NAME));
        releaseDateColumn.setCellValueFactory(new PropertyValueFactory<>(ConstantesUI.RELEASE_DATE));
        viewModel.getNewspapers();

        viewModel.getState().addListener((observableValue, oldState, newState) -> {
            if (newState.error != null) {
                Platform.runLater(() -> {
                    getPrincipalController().createAlert(newState.error);
                });
            }
            if (newState.newspapers != null) {
                Platform.runLater(() -> {
                    newspaperTable.getItems().clear();
                    newspaperTable.getItems().addAll(newState.newspapers);
                });
            }
        });
    }

    public void addNewspaper() {
        if (nameTextField.getText().isEmpty() || releaseDatePicker.getValue() == null) {
            this.getPrincipalController().createAlert(ConstantesUI.YOU_MUST_FILL_ALL_THE_FIELDS);
        } else {
            viewModel.addNewspaper(nameTextField.getText(), releaseDatePicker.getValue());
        }
    }
}
