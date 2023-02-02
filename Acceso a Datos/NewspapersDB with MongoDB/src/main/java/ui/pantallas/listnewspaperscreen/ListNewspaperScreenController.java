package ui.pantallas.listnewspaperscreen;

import io.github.palexdev.materialfx.controls.MFXButton;
import jakarta.inject.Inject;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Article;
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

    @FXML
    private MFXButton deleteArticlesButton;

    @FXML
    private TableView<String> numberArticlesTable;

    @FXML
    private TableColumn<String, String> numberArticlesColumn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("_id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        releaseDateColumn.setCellValueFactory(new PropertyValueFactory<>("releaseDate"));
        newspaperTable.setItems(viewModel.getNewspapers());
        deleteArticlesButton.setVisible(false);
    }

    public void deleteArticlesFromNewspaper() {
//        Newspaper newspaper = newspaperTable.getSelectionModel().getSelectedItem();
//        if (newspaper != null) {
//            if (newspaper.getArticles().isEmpty()) {
//                this.getPrincipalController().createAlert("This newspaper has no articles");
//            } else {
//                int result = viewModel.deleteArticlesFromNewspaper(newspaper);
//                if (result > 0) {
//                    this.getPrincipalController().createAlert("Articles deleted successfully");
//                    articlesTable.setItems(FXCollections.emptyObservableList());
//                    numberArticlesTable.setItems(FXCollections.emptyObservableList());
//                    newspaperTable.setItems(viewModel.getNewspapers());
//                    deleteArticlesButton.setVisible(false);
//                } else {
//                    this.getPrincipalController().createAlert("Error deleting articles");
//                }
//            }
//        } else {
//            this.getPrincipalController().createAlert("Select a newspaper");
//        }
    }

    public void getArticlesAndNumbers() {
        getArticlesOfNewspaper();
//        getNumberArticlesOfNewspaper();
    }

    private void getArticlesOfNewspaper() {
        Newspaper newspaper = newspaperTable.getSelectionModel().getSelectedItem();
        Newspaper newspaperWithArticles = viewModel.getArticlesFromNewspaper(newspaper);
        if (!newspaperWithArticles.getArticles().isEmpty()) {
            idArticleColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
            nameArticleColumn.setCellValueFactory(new PropertyValueFactory<>("name_article"));
            nameTypeColumn.setCellValueFactory(cellData -> {
                Article article = cellData.getValue();
                return new SimpleStringProperty(article.getType());
            });
            articlesTable.setItems(FXCollections.observableArrayList(newspaperWithArticles.getArticles()));
            deleteArticlesButton.setVisible(true);
        } else {
            articlesTable.setItems(FXCollections.emptyObservableList());
            this.getPrincipalController().createAlert("This newspaper has no articles");
            deleteArticlesButton.setVisible(false);
        }
    }

//    private void getNumberArticlesOfNewspaper() {
//        Newspaper newspaper = newspaperTable.getSelectionModel().getSelectedItem();
//        if (newspaper != null) {
//            Map<String, Integer> avgRatings = viewModel.getNbrArticles(newspaper.getId());
//            if (avgRatings != null) {
//                List<String> dataList = new ArrayList<>();
//                for (Map.Entry<String, Integer> entry : avgRatings.entrySet()) {
//                    dataList.add(entry.getKey() + ": " + entry.getValue() + " article");
//                }
//                numberArticlesTable.setItems(FXCollections.observableArrayList(dataList));
//                numberArticlesColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()));
//            } else {
//                this.getPrincipalController().createAlert("The newspaper " + newspaper.getName() + " has no articles");
//            }
//        } else {
//            this.getPrincipalController().createAlert("Please select a reader");
//        }
//    }
}
