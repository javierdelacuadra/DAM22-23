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
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        releaseDateColumn.setCellValueFactory(new PropertyValueFactory<>("releaseDate"));
        newspaperTable.setItems(viewModel.getNewspapers());
    }

    public void deleteNewspaper() {
        Newspaper newspaper = newspaperTable.getSelectionModel().getSelectedItem();
        if (viewModel.checkArticles(newspaper)) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, ConstantesUI.THE_NEWSPAPER_HAS_ARTICLES_ARE_YOU_SURE_YOU_WANT_TO_DELETE_IT, ButtonType.YES, ButtonType.NO);
            alert.showAndWait();
            if (alert.getResult() == ButtonType.YES) {
                viewModel.deleteNewspaper(newspaper);
                newspaperTable.getItems().remove(newspaper);
            }
        } else {
            viewModel.deleteNewspaper(newspaper);
            newspaperTable.getItems().remove(newspaper);
            Alert alert = new Alert(Alert.AlertType.INFORMATION, ConstantesUI.NEWSPAPER_DELETED_SUCCESSFULLY);
            alert.showAndWait();
        }
    }
}
