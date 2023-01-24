package ui.pantallas.deletearticlescreen;

import jakarta.inject.Inject;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Article;
import model.ArticleType;
import model.Newspaper;
import ui.pantallas.common.BasePantallaController;

import java.net.URL;
import java.util.ResourceBundle;

public class DeleteArticleScreenController extends BasePantallaController implements Initializable {

    private final DeleteArticleViewModel viewModel;

    @Inject
    public DeleteArticleScreenController(DeleteArticleViewModel viewModel) {
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name_article"));
        typeColumn.setCellValueFactory(cellData -> {
            Article article = cellData.getValue();
            ArticleType type = article.getType();
            return new SimpleStringProperty(type.getDescription());
        });
        newspaperIDColumn.setCellValueFactory(cellData -> {
            Article article = cellData.getValue();
            Newspaper newspaper = article.getNewspaper();
            return new SimpleIntegerProperty(newspaper.getId()).asObject();
        });
        articlesTable.setItems(viewModel.getArticles());
    }

    public void deleteArticle() {
        if (articlesTable.getSelectionModel().getSelectedItem() != null) {
            Article article = articlesTable.getSelectionModel().getSelectedItem();
            if (viewModel.deleteArticle(article) == 1) {
                articlesTable.getItems().remove(articlesTable.getSelectionModel().getSelectedItem());
            } else {
                this.getPrincipalController().createAlert("Error al eliminar el artículo");
            }
        }
    }
}