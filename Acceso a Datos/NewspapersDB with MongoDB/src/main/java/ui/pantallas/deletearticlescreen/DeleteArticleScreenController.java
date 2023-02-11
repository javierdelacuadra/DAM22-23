package ui.pantallas.deletearticlescreen;

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

public class DeleteArticleScreenController extends BasePantallaController implements Initializable {

    private final DeleteArticleViewModel viewModel;

    @Inject
    public DeleteArticleScreenController(DeleteArticleViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @FXML
    private TableView<Article> articlesTable;

    @FXML
    private TableColumn<Article, String> nameColumn;

    @FXML
    private TableColumn<Article, String> typeColumn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
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