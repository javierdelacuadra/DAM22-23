package ui.pantallas.updatenewspaperscreen;

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
import java.time.LocalDate;
import java.util.ResourceBundle;

public class UpdateNewspaperScreenController extends BasePantallaController implements Initializable {

    private final UpdateNewspaperScreenViewModel viewModel;

    @Inject
    public UpdateNewspaperScreenController(UpdateNewspaperScreenViewModel viewModel) {
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

    public void updateNewspaper() {
        if (newspaperTable.getSelectionModel().getSelectedItem() != null) {
            Newspaper newspaper = new Newspaper(
                    newspaperTable.getSelectionModel().getSelectedItem().getId(),
                    nameTextField.getText(),
                    releaseDatePicker.getValue().toString()
            );
            viewModel.updateNewspaper(newspaper);
        } else {
            this.getPrincipalController().createAlert(ConstantesUI.YOU_HAVEN_T_SELECTED_ANY_NEWSPAPER);
        }
        nameTextField.setText(ConstantesUI.ANY);
        releaseDatePicker.setValue(null);
    }

    public void fillTextFields() {
        if (newspaperTable.getSelectionModel().getSelectedItem() != null) {
            Newspaper newspaper = newspaperTable.getSelectionModel().getSelectedItem();
            nameTextField.setText(newspaper.getName());
            releaseDatePicker.setValue(LocalDate.parse(newspaper.getRelease_date()));
        }
    }
}