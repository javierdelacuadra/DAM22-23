package ui.pantallas.addarticlescreen;

import io.github.palexdev.materialfx.controls.MFXTextField;
import jakarta.inject.Inject;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import modelo.Article;
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
    private MFXTextField idText;

    @FXML
    private MFXTextField nameText;

    @FXML
    private MFXTextField typeIDText;

    @FXML
    private MFXTextField newspaperIDText;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("nameArticle"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("idType"));
        newspaperIDColumn.setCellValueFactory(new PropertyValueFactory<>("idNewspaper"));
        articlesTable.setItems(viewModel.getArticles());
    }

    public void addArticle() {
        if (idText.getText().isEmpty() || nameText.getText().isEmpty() || typeIDText.getText().isEmpty() || newspaperIDText.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR, ConstantesUI.YOU_MUST_FILL_ALL_THE_FIELDS, ButtonType.OK);
            alert.showAndWait();
        } else {
            Article article = new Article(Integer.parseInt(idText.getText()),
                    nameText.getText(),
                    Integer.parseInt(typeIDText.getText()),
                    Integer.parseInt(newspaperIDText.getText()));
            if (viewModel.addArticle(article)) {
                articlesTable.getItems().clear();
                articlesTable.setItems(viewModel.getArticles());
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR, ConstantesUI.ARTICLE_ID_ISN_T_UNIQUE_OR_THE_NEWSPAPER_DOESN_T_EXIST, ButtonType.OK);
                alert.showAndWait();
            }
        }
    }
}
