package ui.pantallas.updatenewspaperscreen;

import jakarta.inject.Inject;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import modelo.Newspaper;
import ui.pantallas.common.BasePantallaController;
import ui.pantallas.listnewspaperscreen.ListNewspaperScreenViewModel;

import java.net.URL;
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        releaseDateColumn.setCellValueFactory(new PropertyValueFactory<>("releaseDate"));
        newspaperTable.setItems(viewModel.getNewspapers());
    }
}
