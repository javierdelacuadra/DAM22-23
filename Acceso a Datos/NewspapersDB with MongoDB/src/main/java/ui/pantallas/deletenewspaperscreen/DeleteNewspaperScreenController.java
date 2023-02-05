package ui.pantallas.deletenewspaperscreen;

import jakarta.inject.Inject;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Newspaper;
import ui.common.ConstantesUI;
import ui.pantallas.common.BasePantallaController;

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("_id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        releaseDateColumn.setCellValueFactory(new PropertyValueFactory<>("releaseDate"));
        newspaperTable.setItems(viewModel.getNewspapers());
    }

    public void deleteNewspaper() {
        Newspaper newspaper = newspaperTable.getSelectionModel().getSelectedItem();
        if (newspaper != null) {
            int result = viewModel.deleteNewspaper(newspaper);
            if (result == 1) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Newspaper deleted successfully", ButtonType.OK);
                alert.showAndWait();
                newspaperTable.setItems(viewModel.getNewspapers());
            } else if (result == 0) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "The newspaper couldn't be deleted because it has\nreaders subscribed or articles", ButtonType.OK);
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR, "There was an error trying to delete the newspaper", ButtonType.OK);
                alert.showAndWait();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, ConstantesUI.PLEASE_SELECT_A_NEWSPAPER, ButtonType.OK);
            alert.showAndWait();
        }
    }
}
