package ui.pantallas.deletenewspaperscreen;

import io.github.palexdev.materialfx.controls.MFXButton;
import jakarta.inject.Inject;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import modelo.Newspaper;
import ui.pantallas.common.BasePantallaController;
import ui.pantallas.listnewspaperscreen.ListNewspaperScreenViewModel;

import java.net.URL;
import java.util.ResourceBundle;

public class DeleteNewspaperScreenController extends BasePantallaController implements Initializable {
    private final DeleteNewspaperScreenViewModel viewModel;

    @Inject
    public DeleteNewspaperScreenController(DeleteNewspaperScreenViewModel viewModel) {
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
    private MFXButton deleteButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        releaseDateColumn.setCellValueFactory(new PropertyValueFactory<>("releaseDate"));
        newspaperTable.setItems(viewModel.getNewspapers());
    }

    public void deleteNewspaper(MouseEvent mouseEvent) {
        if (viewModel.deleteNewspaper(newspaperTable.getSelectionModel().getSelectedItem())) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Newspaper deleted");
            alert.setHeaderText(null);
            alert.setContentText("Newspaper deleted successfully");
            alert.showAndWait();
            viewModel.deleteNewspaper(newspaperTable.getSelectionModel().getSelectedItem());
            newspaperTable.getItems().remove(newspaperTable.getSelectionModel().getSelectedItem());
        }
    }
}
