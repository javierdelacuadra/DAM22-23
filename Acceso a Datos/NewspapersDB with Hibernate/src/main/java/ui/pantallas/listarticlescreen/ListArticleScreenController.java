package ui.pantallas.listarticlescreen;

import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.vavr.control.Either;
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
import model.Query1;
import ui.common.ConstantesUI;
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

    @FXML
    private MFXComboBox<ArticleType> typeComboBox;

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
        articlesQueryTable.setVisible(false);
        nameArticleColumn.setCellValueFactory(new PropertyValueFactory<>("name_article"));
        readerCountColumn.setCellValueFactory(new PropertyValueFactory<>("count"));
        articleTypeColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        typeComboBox.setItems(viewModel.getArticleTypes());
    }

    public void showArticles() {
        articlesTable.setVisible(true);
        articlesTable.setItems(viewModel.getArticles());
        articlesQueryTable.setVisible(false);
        typeComboBox.getSelectionModel().clearSelection();
    }

    public void showArticlesByType() {
        if (typeComboBox.getValue() != null) {
            articlesTable.setItems(viewModel.getArticlesByType(typeComboBox.getValue().getDescription()));
            articlesTable.setVisible(true);
            articlesQueryTable.setVisible(false);
        } else {
            this.getPrincipalController().createAlert(ConstantesUI.YOU_HAVEN_T_SELECTED_ANY_ARTICLE_TYPE);
        }
    }

    public void showMostReadType() {
        Either<Integer, ArticleType> mostReadType = viewModel.getMostReadType();
        if (mostReadType.isRight()) {
            this.getPrincipalController().createAlert("The most read type is: " + mostReadType.get().getDescription());
        } else {
            this.getPrincipalController().createAlert("There are " + mostReadType.getLeft() + " article types that have been read \nthe same number of times");
        }
    }
}