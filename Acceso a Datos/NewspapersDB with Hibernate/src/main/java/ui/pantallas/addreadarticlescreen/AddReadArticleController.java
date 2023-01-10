package ui.pantallas.addreadarticlescreen;

import io.github.palexdev.materialfx.controls.MFXComboBox;
import jakarta.inject.Inject;
import javafx.beans.property.SimpleIntegerProperty;
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
import model.ReadArticle;
import ui.common.ConstantesUI;
import ui.pantallas.common.BasePantallaController;

import java.net.URL;
import java.util.ResourceBundle;

public class AddReadArticleController extends BasePantallaController implements Initializable {

    private final AddReadArticleViewModel viewModel;

    @Inject
    public AddReadArticleController(AddReadArticleViewModel viewModel) {
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
    private MFXComboBox<Integer> ratingComboBox;

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
        ratingComboBox.setItems(FXCollections.observableArrayList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
    }

    @Override
    public void principalCargado() {
        if (viewModel.getArticles(this.getPrincipalController().getReader()).isLeft()) {
            articlesTable.setItems(FXCollections.observableArrayList());
            this.getPrincipalController().createAlert(ConstantesUI.SUBSCRIBE_TO_A_NEWSPAPER_TO_RATE_AND_VIEW_ARTICLES);
        } else {
            articlesTable.setItems(FXCollections.observableArrayList(viewModel.getArticles(this.getPrincipalController().getReader()).get()));
        }
    }

    public void addRating() {
        if (articlesTable.getSelectionModel().getSelectedItem() != null && ratingComboBox.getSelectionModel().getSelectedItem() != null) {
            ReadArticle readArticle = new ReadArticle(this.getPrincipalController().getReader(), articlesTable.getSelectionModel().getSelectedItem(), ratingComboBox.getSelectionModel().getSelectedItem());
            int result = viewModel.addRating(readArticle);
            if (result == 1) {
                articlesTable.getItems().clear();
                articlesTable.setItems(FXCollections.observableArrayList(viewModel.getArticles(this.getPrincipalController().getReader()).get()));
                this.getPrincipalController().createAlert(ConstantesUI.THE_RATING_HAS_BEEN_SUBMITTED_SUCCESSFULLY);
            } else if (result == -1) {
                this.getPrincipalController().createAlert(ConstantesUI.ERROR_ADDING_RATING);
            }
//            } else if (result.getLeft() == -2) {
//                this.getPrincipalController().createAlert(ConstantesUI.YOU_HAVE_ALREADY_RATED_THIS_ARTICLE);
//            }
        } else {
            this.getPrincipalController().createAlert(ConstantesUI.SELECT_AN_ARTICLE_AND_A_RATING);
        }
    }
}