package ui.pantallas.listarticlescreen;

import jakarta.inject.Inject;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import model.Article;
import model.Query1;
import ui.pantallas.common.BasePantallaController;

import java.net.URL;
import java.util.ResourceBundle;

public class ListArticleScreenController extends BasePantallaController implements Initializable {

    private final ListArticleScreenViewModel viewModel;

    @Inject
    public ListArticleScreenController(ListArticleScreenViewModel viewModel) {
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
    private TableView<Query1> articlesQueryTable;

    @FXML
    private TableColumn<Query1, String> nameArticleColumn;

    @FXML
    private TableColumn<Query1, Integer> readerCountColumn;

    @FXML
    private TableColumn<Query1, String> articleTypeColumn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name_article"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("id_type"));
        newspaperIDColumn.setCellValueFactory(new PropertyValueFactory<>("id_newspaper"));
        articlesTable.setItems(viewModel.getArticles());
        articlesQueryTable.setVisible(false);
        nameArticleColumn.setCellValueFactory(new PropertyValueFactory<>("name_article"));
        readerCountColumn.setCellValueFactory(new PropertyValueFactory<>("count"));
        articleTypeColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
    }

    public void showArticlesQuery() {
        if (!viewModel.showArticlesQuery().isEmpty()) {
            articlesQueryTable.setItems(viewModel.showArticlesQuery());
            articlesQueryTable.setVisible(true);
            articlesTable.setVisible(false);
        }
    }

    public void showArticles() {
        articlesTable.setVisible(true);
        articlesQueryTable.setVisible(false);
    }
}