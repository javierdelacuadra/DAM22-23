package ui.pantallas.updatearticlescreen;

import jakarta.inject.Inject;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import modelo.Article;
import ui.pantallas.common.BasePantallaController;
import ui.pantallas.listarticlescreen.ListArticleScreenViewModel;

import java.net.URL;
import java.util.ResourceBundle;

public class UpdateArticleScreenController extends BasePantallaController implements Initializable {

    private final UpdateArticleScreenViewModel viewModel;

    @Inject
    public UpdateArticleScreenController(UpdateArticleScreenViewModel viewModel) {
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
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("nameArticle"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("idType"));
        newspaperIDColumn.setCellValueFactory(new PropertyValueFactory<>("idNewspaper"));
        articlesTable.setItems(viewModel.getArticles());
    }
}
