package ui.pantallas.addsubscriptionscreen;

import jakarta.inject.Inject;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import model.Newspaper;
import ui.pantallas.common.BasePantallaController;

import java.net.URL;
import java.util.ResourceBundle;

public class AddSubscriptionController extends BasePantallaController implements Initializable {

    private final AddSubscriptionViewModel viewModel;

    @Inject
    public AddSubscriptionController(AddSubscriptionViewModel viewModel) {
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
        releaseDateColumn.setCellValueFactory(new PropertyValueFactory<>("release_date"));
        newspaperTable.setItems(viewModel.getNewspapers());
    }

    public void addSubscription() {
        if (newspaperTable.getSelectionModel().getSelectedItem() != null) {
            Newspaper newspaper = newspaperTable.getSelectionModel().getSelectedItem();
            if (viewModel.addSubscription(newspaper, this.getPrincipalController().getReader().getId()) == 1) {
                this.getPrincipalController().createAlert("You have successfully subscribed to the newspaper \n" + newspaper.getName());
            } else {
                this.getPrincipalController().createAlert("You have already subscribed to the newspaper \n" + newspaper.getName());
            }
        }
    }

    public void removeSubscription() {
        if (newspaperTable.getSelectionModel().getSelectedItem() != null) {
            Newspaper newspaper = newspaperTable.getSelectionModel().getSelectedItem();
            if (viewModel.removeSubscription(newspaper, this.getPrincipalController().getReader().getId()) == 1) {
                this.getPrincipalController().createAlert("You have successfully unsubscribed to the newspaper \n" + newspaper.getName());
            } else {
                this.getPrincipalController().createAlert("You have not subscribed to the newspaper \n" + newspaper.getName());
            }
        }
    }
}