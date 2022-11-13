package ui.pantallas.updatenewspaperscreen;

import io.github.palexdev.materialfx.controls.MFXDatePicker;
import io.github.palexdev.materialfx.controls.MFXTextField;
import jakarta.inject.Inject;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Newspaper;
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
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        releaseDateColumn.setCellValueFactory(new PropertyValueFactory<>("release_date"));
        newspaperTable.setItems(viewModel.getNewspapers());
    }

    public void updateNewspaper() {
        if (newspaperTable.getSelectionModel().getSelectedItem() != null) {
            Newspaper newspaper = new Newspaper(
                    newspaperTable.getSelectionModel().getSelectedItem().getId(),
                    nameTextField.getText(),
                    releaseDatePicker.getValue().toString()
            );
            if (viewModel.updateNewspaper(newspaper).isRight()) {
                newspaperTable.getItems().clear();
                newspaperTable.setItems(viewModel.getNewspapers());
            } else if (viewModel.updateNewspaper(newspaper).getLeft() == -1) {
                this.getPrincipalController().createAlert(ConstantesUI.THERE_WAS_AN_ERROR_UPDATING_THE_READER);
            } else if (viewModel.updateNewspaper(newspaper).getLeft() == -2) {
                this.getPrincipalController().createAlert(ConstantesUI.THE_READER_DOES_NOT_EXIST);
            }
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