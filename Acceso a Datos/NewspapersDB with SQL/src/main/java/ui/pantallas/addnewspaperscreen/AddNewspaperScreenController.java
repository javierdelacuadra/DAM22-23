package ui.pantallas.addnewspaperscreen;

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
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        releaseDateColumn.setCellValueFactory(new PropertyValueFactory<>("release_date"));
        newspaperTable.setItems(viewModel.getNewspapers());
    }

    public void addNewspaper() {
        if (nameTextField.getText().isEmpty() || releaseDatePicker.getValue() == null) {
            this.getPrincipalController().createAlert(ConstantesUI.YOU_MUST_FILL_ALL_THE_FIELDS);
        } else {
            if (viewModel.addNewspaper(nameTextField.getText(), releaseDatePicker.getValue()).isRight()) {
                newspaperTable.setItems(viewModel.getNewspapers());
            } else {
                this.getPrincipalController().createAlert(ConstantesUI.ERROR_ADDING_NEWSPAPER);
            }
        }
    }
}
