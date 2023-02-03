package ui.pantallas.updatearticlescreen;

import io.github.palexdev.materialfx.controls.MFXTextField;
import jakarta.inject.Inject;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Article;
import ui.pantallas.common.BasePantallaController;

import java.net.URL;
import java.util.ResourceBundle;

public class UpdateArticleController extends BasePantallaController implements Initializable {

    private final UpdateArticleViewModel viewModel;

    @Inject
    public UpdateArticleController(UpdateArticleViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @FXML
    private TableView<Article> articlesTable;

    @FXML
    private TableColumn<Article, String> nameColumn;

    @FXML
    private TableColumn<Article, Integer> typeColumn;

    @FXML
    private MFXTextField nameTextField;

    @FXML
    private MFXTextField typeTextField;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        articlesTable.setItems(viewModel.getArticles());
    }

    public void updateArticle() {
        if (articlesTable.getSelectionModel().getSelectedItem() != null) {
            Article article = new Article();
            article.setName(nameTextField.getText());
            article.setType(typeTextField.getText());
            if (viewModel.updateArticle(article, articlesTable.getSelectionModel().getSelectedItem().getName()) == 1) {
                articlesTable.getSelectionModel().getSelectedItem().setName(nameTextField.getText());
                articlesTable.refresh();
                this.getPrincipalController().createAlert("Article updated successfully!");
            } else {
                this.getPrincipalController().createAlert("Article not updated!");
            }
        }

    }

    public void fillTextFields() {
        if (articlesTable.getSelectionModel().getSelectedItem() != null) {
            Article article = articlesTable.getSelectionModel().getSelectedItem();
            nameTextField.setText(article.getName());
            typeTextField.setText(article.getType());
        }
    }
}