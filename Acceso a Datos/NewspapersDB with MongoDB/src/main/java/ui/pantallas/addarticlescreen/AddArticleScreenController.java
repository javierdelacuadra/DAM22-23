package ui.pantallas.addarticlescreen;

import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXTextField;
import jakarta.inject.Inject;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Article;
import model.Newspaper;
import ui.common.ConstantesUI;
import ui.pantallas.common.BasePantallaController;

import java.net.URL;
import java.util.ResourceBundle;

public class AddArticleScreenController extends BasePantallaController implements Initializable {

    private final AddArticleScreenViewModel viewModel;

    @Inject
    public AddArticleScreenController(AddArticleScreenViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @FXML
    private TableView<Article> articlesTable;

    @FXML
    private TableColumn<Article, String> nameColumn;

    @FXML
    private TableColumn<Article, String> typeColumn;

    @FXML
    private MFXTextField nameText;

    @FXML
    private MFXTextField typeTextField;

    @FXML
    private MFXComboBox<Newspaper> newspaperComboBox;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        articlesTable.setItems(viewModel.getArticles());
        newspaperComboBox.setItems(viewModel.getNewspapers());
    }

    public void addArticle() {
        if (nameText.getText().isEmpty() || typeTextField.getText().isEmpty() || newspaperComboBox.getSelectionModel().getSelectedItem() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, ConstantesUI.YOU_MUST_FILL_ALL_THE_FIELDS, ButtonType.OK);
            alert.showAndWait();
        } else {
            Article article = new Article(nameText.getText(),
                    String.valueOf(typeTextField.getText()));
            Newspaper newspaper = newspaperComboBox.getSelectionModel().getSelectedItem();
            if (viewModel.addArticle(article, newspaper) == 1) {
                articlesTable.getItems().clear();
                articlesTable.setItems(viewModel.getArticles());
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR, ConstantesUI.ARTICLE_ID_ISN_T_UNIQUE_OR_THE_NEWSPAPER_DOESN_T_EXIST, ButtonType.OK);
                alert.showAndWait();
            }
        }
    }
}
