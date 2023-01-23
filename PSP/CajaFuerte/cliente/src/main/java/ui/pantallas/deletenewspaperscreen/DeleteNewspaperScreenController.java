package ui.pantallas.deletenewspaperscreen;

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

    public void deleteNewspaper() {
        Newspaper newspaper = newspaperTable.getSelectionModel().getSelectedItem();
        if (newspaper != null) {
            if (viewModel.deleteNewspaper(newspaper).isRight()) {
                newspaperTable.getItems().removeIf(newspaper1 -> newspaper1.getId() == newspaper.getId());
            }
        } else {
            this.getPrincipalController().createAlert(ConstantesUI.YOU_HAVEN_T_SELECTED_ANY_NEWSPAPER);
        }
    }
}
