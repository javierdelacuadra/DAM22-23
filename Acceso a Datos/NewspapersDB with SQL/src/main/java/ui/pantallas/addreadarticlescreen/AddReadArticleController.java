package ui.pantallas.addreadarticlescreen;

import javafx.fxml.Initializable;
import ui.pantallas.common.BasePantallaController;

import java.net.URL;
import java.util.ResourceBundle;

public class AddReadArticleController extends BasePantallaController implements Initializable {

    private final AddReadArticleViewModel viewModel;

    public AddReadArticleController(AddReadArticleViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}