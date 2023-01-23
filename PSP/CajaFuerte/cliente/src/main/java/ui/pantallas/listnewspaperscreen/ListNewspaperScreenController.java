package ui.pantallas.listnewspaperscreen;

import jakarta.inject.Inject;
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

public class ListNewspaperScreenController extends BasePantallaController implements Initializable {

    private final ListNewspaperScreenViewModel viewModel;

    @Inject
    public ListNewspaperScreenController(ListNewspaperScreenViewModel viewModel) {
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
        idColumn.setCellValueFactory(new PropertyValueFactory<>(ConstantesUI.ID));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>(ConstantesUI.NAME));
        releaseDateColumn.setCellValueFactory(new PropertyValueFactory<>(ConstantesUI.RELEASE_DATE));
        newspaperTable.setItems(viewModel.getNewspapers());
    }
}
