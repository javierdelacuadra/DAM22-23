package ui.screens.readers.addReadArt;

import model.Article;
import model.Newspaper;
import io.github.palexdev.materialfx.controls.MFXTableColumn;
import io.github.palexdev.materialfx.controls.MFXTableView;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import jakarta.inject.Inject;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import ui.screens.common.BaseScreenController;
import ui.screens.common.ScreenConstants;

import java.time.LocalDate;
import java.util.Comparator;

public class AddReadArtController extends BaseScreenController {

    @FXML
    private MFXTableView<Article> tblUnreadArticles;

    @FXML
    private MFXTableView<Newspaper> tblNews;

    @FXML
    private MFXTableColumn<Newspaper> columnId;
    @FXML
    private MFXTableColumn<Newspaper> columnName;
    @FXML
    private MFXTableColumn<Newspaper> columnReleaseDate;
    
    @FXML
    private ComboBox<Integer> cmbRating;
    private AddReadArtViewModel addReadArtViewModel;


    @Inject
    public AddReadArtController(AddReadArtViewModel addReadArtViewModel) {
        this.addReadArtViewModel = addReadArtViewModel;
    }


    @Override
    public void principalCargado() {
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
        tblUnreadArticles.setFooterVisible(false);
        tblUnreadArticles.getTableColumns().addAll(idColumn, nameColumn, descColumn, idTypeColumn, idNewsColumn);

        columnId.setRowCellFactory(newspaper -> new MFXTableRowCell<>(Newspaper::getId));
        columnName.setRowCellFactory(newspaper -> new MFXTableRowCell<>(Newspaper::getName_newspaper));
        columnReleaseDate.setRowCellFactory(newspaper -> new MFXTableRowCell<>(Newspaper::getRelease_date));
        tblNews.setFooterVisible(false);
//        tblNews.getTableColumns().addAll(idNColumn, nameNColumn, relNColumn);

        cmbRating.getItems().addAll(5, 4, 3, 2, 1);
        cmbRating.getSelectionModel().select(0);
        cmbRating.setDisable(true);

        addListeners();
        addReadArtViewModel.loadScreen(getPrincipalController().getActualUser());
    }

    private void addListeners() {
        addReadArtViewModel.getState().addListener((observableValue, addReadArtState, addReadArtStateNew) -> {
            if (addReadArtStateNew.getSuccess() != null) {
                getPrincipalController().showAlertInformation(addReadArtStateNew.getSuccess());
                tblUnreadArticles.getItems().clear();
                if (addReadArtStateNew.getUnreadArticles() != null) {
                    tblUnreadArticles.getItems().addAll(addReadArtStateNew.getUnreadArticles());
                }
            }

            if (addReadArtStateNew.getNewspapers() != null && ((addReadArtState.getNewspapers() == null) || addReadArtStateNew.getNewspapers() != addReadArtState.getNewspapers())) {
                tblNews.getItems().clear();
                tblNews.getItems().addAll(addReadArtStateNew.getNewspapers());
            }
            if (addReadArtStateNew.getUnreadArticles() != null) {
                tblUnreadArticles.getItems().clear();
                tblUnreadArticles.getItems().addAll(addReadArtStateNew.getUnreadArticles());
            }
            if (addReadArtStateNew.getNewspapers() == null){
                tblNews.getItems().clear();
            }
            if (addReadArtStateNew.getUnreadArticles() == null){
                tblUnreadArticles.getItems().clear();
            }

            if (addReadArtStateNew.getError() != null) {
                getPrincipalController().showAlertError(addReadArtStateNew.getError());
            }
        });

        tblNews.getSelectionModel().selectionProperty().addListener((observableValue, addRead, addReadNew) -> {
            if (addReadNew != null && observableValue.getValue().size() != 0) {
                addReadArtViewModel.loadUnreadArticles(addReadNew.values().stream().findFirst().orElse(new Newspaper(-1, "", LocalDate.now())));
            }
        });
        tblUnreadArticles.getSelectionModel().selectionProperty().addListener((observableValue, addRead, addReadNew) -> {
            if (addReadNew != null && observableValue.getValue().size() != 0) {
                cmbRating.setDisable(false);
            }
        });
    }

    public void addArticle(ActionEvent actionEvent) {
        if (tblUnreadArticles.getSelectionModel().getSelection().size() != 0) {
            addReadArtViewModel.addReadArticle(tblUnreadArticles.getSelectionModel().selectionProperty().values().stream().toList().get(0), getPrincipalController().getActualUser(), cmbRating.getSelectionModel().getSelectedItem());
        }
        else {
            getPrincipalController().showAlertError(ScreenConstants.YOU_MUST_CHOOSE_THE_READ_ARTICLE_FROM_THE_LIST);
        }
    }
}
