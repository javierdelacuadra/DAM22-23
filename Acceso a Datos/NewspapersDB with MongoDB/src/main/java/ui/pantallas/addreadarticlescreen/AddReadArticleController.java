package ui.pantallas.addreadarticlescreen;

import io.github.palexdev.materialfx.controls.MFXComboBox;
import jakarta.inject.Inject;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Article;
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
    private TableColumn<Article, String> nameColumn;

    @FXML
    private TableColumn<Article, String> typeColumn;

    @FXML
    private MFXComboBox<Integer> ratingComboBox;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
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
            ReadArticle readArticle = new ReadArticle(this.getPrincipalController().getReader().getId(), ratingComboBox.getSelectionModel().getSelectedItem());
            Article article = articlesTable.getSelectionModel().getSelectedItem();
            int result = viewModel.addRating(readArticle, article);
            if (result >= 1) {
                this.getPrincipalController().createAlert(ConstantesUI.THE_RATING_HAS_BEEN_SUBMITTED_SUCCESSFULLY);
            } else if (result == -1) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation");
                alert.setHeaderText("You have already rated this article");
                alert.setContentText("Do you want to update the rating?");
                alert.showAndWait().ifPresent(response -> {
                    if (response == ButtonType.OK) {
                        int resultUpdate = viewModel.updateRating(readArticle, article);
                        if (resultUpdate >= 1) {
                            this.getPrincipalController().createAlert("The rating has been updated successfully");
                        } else {
                            this.getPrincipalController().createAlert("The rating could not be updated");
                        }
                    }
                });
            } else if (result == -2) {
                this.getPrincipalController().createAlert("The reader was not found");
            }
        } else {
            this.getPrincipalController().createAlert(ConstantesUI.SELECT_AN_ARTICLE_AND_A_RATING);
        }
    }

}