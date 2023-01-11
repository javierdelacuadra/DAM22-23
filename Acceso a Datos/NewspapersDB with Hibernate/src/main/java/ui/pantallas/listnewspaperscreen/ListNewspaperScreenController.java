package ui.pantallas.listnewspaperscreen;

import jakarta.inject.Inject;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
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

public class ListNewspaperScreenController extends BasePantallaController implements Initializable {

    private final ListNewspaperScreenViewModel viewModel;

    @Inject
    public ListNewspaperScreenController(ListNewspaperScreenViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @FXML
    private TableView<Newspaper> newspaperTable;

    @FXML
    private TableColumn<Newspaper, Integer> idColumn;

    @FXML
    private TableColumn<Newspaper, String> nameColumn;

    @FXML
    private TableColumn<Newspaper, String> releaseDateColumn;

    @FXML
    private TableView<Article> articlesTable;

    @FXML
    private TableColumn<Article, Integer> idArticleColumn;

    @FXML
    private TableColumn<Article, String> nameArticleColumn;

    @FXML
    private TableColumn<Article, String> nameTypeColumn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        releaseDateColumn.setCellValueFactory(new PropertyValueFactory<>("release_date"));
        newspaperTable.setItems(viewModel.getNewspapers());
    }

    public void getArticlesOfNewspaper() {
        Newspaper newspaper = newspaperTable.getSelectionModel().getSelectedItem();
        Newspaper newspaperWithArticles = viewModel.getArticlesFromNewspaper(newspaper);
        if (!newspaperWithArticles.getArticles().isEmpty()) {
            idArticleColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
            nameArticleColumn.setCellValueFactory(new PropertyValueFactory<>("name_article"));
            nameTypeColumn.setCellValueFactory(cellData -> {
                Article article = cellData.getValue();
                ArticleType type = article.getType();
                return new SimpleStringProperty(type.getDescription());
            });
            articlesTable.setItems(FXCollections.observableArrayList(newspaperWithArticles.getArticles()));
        } else {
            articlesTable.setItems(FXCollections.emptyObservableList());
            this.getPrincipalController().createAlert("This newspaper has no articles");
        }
    }
}
