package ui.pantallas.springqueryscreen;

import io.github.palexdev.materialfx.controls.MFXComboBox;
import jakarta.inject.Inject;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.ArticleType;
import model.Newspaper;
import model.Query2;
import model.Query3;
import ui.common.ConstantesUI;
import ui.pantallas.common.BasePantallaController;

import java.net.URL;
import java.util.ResourceBundle;

public class QueriesScreenController extends BasePantallaController implements Initializable {

    private final QueriesScreenViewModel viewModel;

    @Inject
    public QueriesScreenController(QueriesScreenViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @FXML
    private TableView<Query2> tableQuery2;

    @FXML
    private TableColumn<Query2, String> idColumn;

    @FXML
    private TableColumn<Query2, String> nameArticleColumn;

    @FXML
    private TableColumn<Query2, String> idTypeColumn;

    @FXML
    private TableColumn<Query2, String> nameNewspaperColumn;

    @FXML
    private TableView<Query3> tableQuery3;

    @FXML
    private TableColumn<Query3, String> idColumnQ3;

    @FXML
    private TableColumn<Query3, String> nameArticleColumnQ3;

    @FXML
    private TableColumn<Query3, String> idReaderColumn;

    @FXML
    private TableColumn<Query3, String> ratingColumn;

    @FXML
    private TableColumn<Query3, String> badRatingsColumn;

    @FXML
    private MFXComboBox<ArticleType> comboBoxType;

    @FXML
    private MFXComboBox<Newspaper> comboBoxNewspaper;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameArticleColumn.setCellValueFactory(new PropertyValueFactory<>("name_article"));
        idTypeColumn.setCellValueFactory(new PropertyValueFactory<>("idType"));
        nameNewspaperColumn.setCellValueFactory(new PropertyValueFactory<>("name_newspaper"));
        idColumnQ3.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameArticleColumnQ3.setCellValueFactory(new PropertyValueFactory<>("name_article"));
        idReaderColumn.setCellValueFactory(new PropertyValueFactory<>("id_reader"));
        ratingColumn.setCellValueFactory(new PropertyValueFactory<>("rating"));
        badRatingsColumn.setCellValueFactory(new PropertyValueFactory<>("bad_ratings"));
        comboBoxNewspaper.setItems(viewModel.getNewspapers());
        comboBoxType.setItems(viewModel.getArticleTypes());
    }

    public void getArticlesByTypeAndNameNewspaper() {
        if (comboBoxNewspaper.getValue() != null && comboBoxType.getValue() != null) {
            Newspaper newspaper = comboBoxNewspaper.getValue();
            ArticleType articleType = comboBoxType.getValue();
            ObservableList<Query2> articles = viewModel.getArticlesByTypeAndNameNewspaper(articleType.getDescription(), newspaper.getName());
            if (articles.isEmpty()) {
                this.getPrincipalController().createAlert(ConstantesUI.THE_QUERY_DID_NOT_RETURN_ANY_RESULTS);
            } else {
                tableQuery2.setItems(articles);
            }
        } else {
            this.getPrincipalController().createAlert(ConstantesUI.YOU_MUST_SELECT_A_NEWSPAPER_AND_AN_ARTICLE_TYPE);
        }
    }

    public void getArticlesByNewspaperWithBadRatings() {
        if (comboBoxNewspaper.getValue() != null) {
            Newspaper newspaper = comboBoxNewspaper.getValue();
            ObservableList<Query3> articles = viewModel.getArticlesByNewspaperWithBadRatings(newspaper.getName());
            if (articles.isEmpty()) {
                this.getPrincipalController().createAlert(ConstantesUI.THE_QUERY_DID_NOT_RETURN_ANY_RESULTS);
            } else {
                tableQuery3.setItems(articles);
            }
        } else {
            this.getPrincipalController().createAlert(ConstantesUI.YOU_MUST_SELECT_A_NEWSPAPER);
        }
    }
}