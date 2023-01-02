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
    private TableColumn<Article, Integer> idColumn;

    @FXML
    private TableColumn<Article, String> nameColumn;

    @FXML
    private TableColumn<Article, String> typeColumn;

    @FXML
    private TableColumn<Article, Integer> newspaperIDColumn;

    @FXML
    private MFXTextField nameTextField;

    @FXML
    private MFXTextField typeTextField;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name_article"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("id_type"));
        newspaperIDColumn.setCellValueFactory(new PropertyValueFactory<>("id_newspaper"));
        articlesTable.setItems(viewModel.getArticles());
    }

    public void updateArticle() {
        if (articlesTable.getSelectionModel().getSelectedItem() != null) {
            Article article = new Article();
            article.setId(articlesTable.getSelectionModel().getSelectedItem().getId());
            article.setName_article(nameTextField.getText());
            try {
                article.getType().setId(Integer.parseInt(typeTextField.getText()));
            } catch (NumberFormatException e) {
                this.getPrincipalController().createAlert("QUE TE CREES QUE ES UNA LETRA? UN NUMERO ES!");
                //TODO: diarios de copilot capítulo 1 se le ha ido la cabeza
            }
            article.getNewspaper().setId(articlesTable.getSelectionModel().getSelectedItem().getNewspaper().getId());
            if (viewModel.updateArticle(article) == 1) {
                articlesTable.getSelectionModel().getSelectedItem().setName_article(nameTextField.getText());
                articlesTable.getSelectionModel().getSelectedItem().getType().setId(Integer.parseInt(typeTextField.getText()));
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
            nameTextField.setText(article.getName_article());
            typeTextField.setText(String.valueOf(article.getType().getId()));
        }
    }
}