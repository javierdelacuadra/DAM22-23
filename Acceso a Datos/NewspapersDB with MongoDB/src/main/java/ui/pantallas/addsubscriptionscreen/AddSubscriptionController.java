package ui.pantallas.addsubscriptionscreen;

import jakarta.inject.Inject;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Newspaper;
import model.Subscription;
import ui.common.ConstantesUI;
import ui.pantallas.common.BasePantallaController;

import java.net.URL;
import java.time.LocalDate;
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
            Subscription subscription = new Subscription(this.getPrincipalController().getReader().getId(), newspaper.getId(), LocalDate.now(), null);
            if (viewModel.addSubscription(subscription) == 1) {
                this.getPrincipalController().createAlert(ConstantesUI.YOU_HAVE_SUCCESSFULLY_SUBSCRIBED_TO_THE_NEWSPAPER + newspaper.getName());
            } else {
                this.getPrincipalController().createAlert(ConstantesUI.YOU_ARE_ALREADY_SUBSCRIBED_TO_THE_NEWSPAPER + newspaper.getName());
            }
        } else {
            this.getPrincipalController().createAlert(ConstantesUI.PLEASE_SELECT_A_NEWSPAPER);
        }
    }

    public void removeSubscription() {
        if (newspaperTable.getSelectionModel().getSelectedItem() != null) {
            Newspaper newspaper = newspaperTable.getSelectionModel().getSelectedItem();
            Subscription subscription = new Subscription(this.getPrincipalController().getReader().getId(), newspaper.getId(), null, LocalDate.now());
            if (viewModel.removeSubscription(subscription) == 1) {
                this.getPrincipalController().createAlert(ConstantesUI.YOU_HAVE_SUCCESSFULLY_UNSUBSCRIBED_TO_THE_NEWSPAPER + newspaper.getName());
            } else {
                this.getPrincipalController().createAlert(ConstantesUI.YOU_ARE_NOT_SUBSCRIBED_TO_THE_NEWSPAPER + newspaper.getName());
            }
        } else {
            this.getPrincipalController().createAlert(ConstantesUI.PLEASE_SELECT_A_NEWSPAPER);
        }
    }
}