package ui.screens.articles.addArticle;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTableColumn;
import io.github.palexdev.materialfx.controls.MFXTableView;
import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import jakarta.inject.Inject;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import model.Article;
import ui.screens.common.BaseScreenController;
import ui.screens.common.ScreenConstants;

import java.net.URL;
import java.util.Comparator;
import java.util.ResourceBundle;

public class AddArticleController extends BaseScreenController implements Initializable {

    @FXML
    private MFXTextField txtName;

    @FXML
    private TextArea txtDesc;
    @FXML
    private ComboBox cmbNews;

    @FXML
    private ComboBox cmbType;

    @FXML
    private MFXButton btnAdd;

    @FXML
    private MFXTableView<Article> mfxArticlesByNews;


    AddArticleViewModel addArticleViewModel;


    @Inject
    public AddArticleController(AddArticleViewModel addArticleViewModel) {
        this.addArticleViewModel = addArticleViewModel;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        MFXTableColumn<Article> idColumn = new MFXTableColumn<>(ScreenConstants.ID, true, Comparator.comparing(Article::getId));
        MFXTableColumn<Article> nameColumn = new MFXTableColumn<>(ScreenConstants.NAME, true, Comparator.comparing(Article::getName_article));
        MFXTableColumn<Article> descColumn = new MFXTableColumn<>(ScreenConstants.DESCRIPTION, true, Comparator.comparing(Article::getDescription));
        MFXTableColumn<Article> idTypeColumn = new MFXTableColumn<>(ScreenConstants.TYPE, true, Comparator.comparing(Article::getId_type));
        MFXTableColumn<Article> idNewsColumn = new MFXTableColumn<>(ScreenConstants.NEWSPAPER, true, Comparator.comparing(Article::getId_newspaper));

        idColumn.setRowCellFactory(article -> new MFXTableRowCell<>(Article::getId));
        idTypeColumn.setRowCellFactory(article -> new MFXTableRowCell<>(Article::getId_type));
        descColumn.setRowCellFactory(article -> new MFXTableRowCell<>(Article::getDescription));
        nameColumn.setRowCellFactory(article -> new MFXTableRowCell<>(Article::getName_article));
        idNewsColumn.setRowCellFactory(article -> new MFXTableRowCell<>(Article::getId_newspaper));
        mfxArticlesByNews.setFooterVisible(false);
        mfxArticlesByNews.getTableColumns().addAll(idColumn, nameColumn, descColumn, idTypeColumn, idNewsColumn);
        mfxArticlesByNews.setFooterVisible(false);

        addListener();
        addArticleViewModel.loadScreen();
    }

    private void addListener() {
        addArticleViewModel.getState().addListener((observableValue, listState, listStateNew) -> {
            if (listStateNew.getError() != null) {
                getPrincipalController().showAlertError(listStateNew.getError());
            }
            else if (!listStateNew.getTypeArtList().isEmpty() && !listStateNew.getNewspaperList().isEmpty()) {
                if (listState.getNewspaperList() == null || listState.getNewspaperList().size() != listStateNew.getNewspaperList().size()){
                    cmbNews.getItems().clear();
                    cmbNews.getItems().addAll(listStateNew.getNewspaperList());
                    cmbNews.getSelectionModel().select(0);
                }
                if (listState.getTypeArtList() == null || listState.getTypeArtList().size() != listStateNew.getTypeArtList().size()){
                    cmbType.getItems().clear();
                    cmbType.getItems().addAll(listStateNew.getTypeArtList());
                    cmbType.getSelectionModel().select(0);
                }
                if (listStateNew.getConfirmation() != null){
                    getPrincipalController().showAlertInformation(listStateNew.getConfirmation());
                }
                mfxArticlesByNews.getItems().clear();
                mfxArticlesByNews.getItems().addAll(listStateNew.getArticlesList());
            }

        });
    }
    public void newsSelected(ActionEvent actionEvent) {
        addArticleViewModel.updateArtTable(cmbNews.getSelectionModel().getSelectedIndex()+1);

    }

    public void addArticle(ActionEvent actionEvent) {
        addArticleViewModel.addArticle(txtName.getText(), txtDesc.getText(),cmbType.getSelectionModel().getSelectedIndex()+1, cmbNews.getSelectionModel().getSelectedIndex()+1);
    }
}

