package ui.pantallas.addnewspaperscreen;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import jakarta.inject.Inject;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import modelo.Newspaper;
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
    private MFXButton addButton;

    @FXML
    private MFXTextField idText;

    @FXML
    private MFXTextField nameText;

    @FXML
    private MFXTextField dateText;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        releaseDateColumn.setCellValueFactory(new PropertyValueFactory<>("releaseDate"));
        newspaperTable.setItems(viewModel.getNewspapers());
    }

    public void addNewspaper() {
        Newspaper newspaper = new Newspaper(Integer.parseInt(idText.getText()),
                nameText.getText(),
                dateText.getText());
        if (viewModel.addNewspaper(newspaper)) {
            newspaperTable.getItems().clear();
            newspaperTable.setItems(viewModel.getNewspapers());
        }
    }
}
