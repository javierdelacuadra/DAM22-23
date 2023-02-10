package ui.pantallas.listarticlescreen;

import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Article;
import model.ArticleType;
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
    private TableColumn<Article, String> nameColumn;

    @FXML
    private TableColumn<Article, String> typeColumn;

    @FXML
    private MFXComboBox<ArticleType> typeComboBox;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        articlesTable.setItems(viewModel.getArticles());
        typeComboBox.setItems(viewModel.getArticleTypes());
    }

    public void showArticles() {
        articlesTable.setVisible(true);
        articlesTable.setItems(viewModel.getArticles());
        typeComboBox.getSelectionModel().clearSelection();
    }

//    public void showArticlesByType() {
//        if (typeComboBox.getValue() != null) {
//            articlesTable.setItems(viewModel.getArticlesByType(typeComboBox.getValue().getDescription()));
//            articlesTable.setVisible(true);
//            articlesQueryTable.setVisible(false);
//        } else {
//            this.getPrincipalController().createAlert(ConstantesUI.YOU_HAVEN_T_SELECTED_ANY_ARTICLE_TYPE);
//        }
//    }

    public void showMostReadType() {
        Either<Integer, ArticleType> mostReadType = viewModel.getMostReadType();
        if (mostReadType.isRight()) {
            this.getPrincipalController().createAlert("The most read type is: " + mostReadType.get().getDescription());
        } else {
            this.getPrincipalController().createAlert("There are " + mostReadType.getLeft() + " article types that have been read \nthe same number of times");
        }
    }
}