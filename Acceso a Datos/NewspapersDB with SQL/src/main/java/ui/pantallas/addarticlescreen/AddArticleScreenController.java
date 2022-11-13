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
import model.ArticleType;
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
    private TableColumn<Article, Integer> idColumn;

    @FXML
    private TableColumn<Article, String> nameColumn;

    @FXML
    private TableColumn<Article, String> typeColumn;

    @FXML
    private TableColumn<Article, Integer> newspaperIDColumn;

    @FXML
    private MFXTextField nameText;

    @FXML
    private MFXComboBox<ArticleType> typeIDComboBox;

    @FXML
    private MFXComboBox<Newspaper> newspaperIDComboBox;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name_article"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("id_type"));
        newspaperIDColumn.setCellValueFactory(new PropertyValueFactory<>("id_newspaper"));
        articlesTable.setItems(viewModel.getArticles());
        typeIDComboBox.setItems(viewModel.getArticleTypes());
        newspaperIDComboBox.setItems(viewModel.getNewspapers());
    }

    public void addArticle() {
        if (nameText.getText().isEmpty() || typeIDComboBox.getSelectionModel().getSelectedItem() == null || newspaperIDComboBox.getSelectionModel().getSelectedItem() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, ConstantesUI.YOU_MUST_FILL_ALL_THE_FIELDS, ButtonType.OK);
            alert.showAndWait();
        } else {
            Article article = new Article(-1,
                    nameText.getText(),
                    typeIDComboBox.getSelectionModel().getSelectedItem().getId(),
                    newspaperIDComboBox.getSelectionModel().getSelectedItem().getId());
            if (viewModel.addArticle(article).isRight()) {
                articlesTable.getItems().clear();
                articlesTable.setItems(viewModel.getArticles());
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR, ConstantesUI.ARTICLE_ID_ISN_T_UNIQUE_OR_THE_NEWSPAPER_DOESN_T_EXIST, ButtonType.OK);
                alert.showAndWait();
            }
        }
    }
}
