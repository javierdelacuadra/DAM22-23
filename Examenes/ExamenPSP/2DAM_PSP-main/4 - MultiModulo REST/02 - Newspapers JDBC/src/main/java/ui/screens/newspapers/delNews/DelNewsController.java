package ui.screens.newspapers.delNews;

import io.github.palexdev.materialfx.controls.MFXTableColumn;
import io.github.palexdev.materialfx.controls.MFXTableView;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import jakarta.inject.Inject;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import model.Article;
import model.Newspaper;
import ui.screens.common.BaseScreenController;
import ui.screens.common.ScreenConstants;

import java.net.URL;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.ResourceBundle;

public class DelNewsController  extends BaseScreenController implements Initializable {

    @FXML
    MFXTableView<Article> mfxArticlesByNews;

    @FXML
    MFXTableView<Newspaper> mfxNewspapers;

    DelNewsViewModel delNewsViewModel;
    @Inject
    public DelNewsController(DelNewsViewModel delNewsViewModel) {
        this.delNewsViewModel = delNewsViewModel;
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
        mfxArticlesByNews.setDisable(true);

        MFXTableColumn<Newspaper> idCol = new MFXTableColumn<>(ScreenConstants.ID, true, Comparator.comparing(Newspaper::getId));
        MFXTableColumn<Newspaper> nameCol = new MFXTableColumn<>(ScreenConstants.NAME, true, Comparator.comparing(Newspaper::getName_newspaper));
        MFXTableColumn<Newspaper> releaseColumn = new MFXTableColumn<>(ScreenConstants.RELEASED, true, Comparator.comparing(Newspaper::getRelease_date));
        idCol.setRowCellFactory(newspaper -> new MFXTableRowCell<>(Newspaper::getId));
        nameCol.setMinWidth(160);
        nameCol.setRowCellFactory(newspaper -> new MFXTableRowCell<>(Newspaper::getName_newspaper));
        releaseColumn.setRowCellFactory(newspaper -> new MFXTableRowCell<>(Newspaper::getRelease_date));

        mfxNewspapers.getTableColumns().addAll(idCol, nameCol, releaseColumn);
        mfxNewspapers.setFooterVisible(false);
    }

    @Override
    public void principalCargado(){
        loadNews();
        delNewsViewModel.loadScreen();
    }

    private void loadNews() {
        delNewsViewModel.getState().addListener((observableValue, delNewsState, delNewsStateNew) -> {
            if (delNewsStateNew.getWarningMessage() != null) {
                boolean agreed = getPrincipalController().showAlertConfirmation(delNewsStateNew.getWarningMessage());
                if (agreed){
                    delNewsViewModel.deleteNewspaper(true);
                }
            }
            if (delNewsStateNew.getError() != null) {
                getPrincipalController().showAlertError(delNewsStateNew.getError());
            }
            if (delNewsStateNew.getNewspaperList() != null && !delNewsStateNew.getNewspaperList().isEmpty()) {
                    if (delNewsState.getNewspaperList() == null) {
                        mfxNewspapers.getItems().clear();
                        mfxNewspapers.getItems().addAll(delNewsStateNew.getNewspaperList());
                        mfxNewspapers.getSelectionModel().clearSelection();
                    }
                    else if (delNewsState.getNewspaperList() != null && delNewsState.getSuccessMessage() != null) {
                        mfxNewspapers.getItems().clear();
                        mfxNewspapers.getItems().addAll(delNewsStateNew.getNewspaperList());
                        mfxNewspapers.getSelectionModel().clearSelection();
                        mfxArticlesByNews.getItems().clear();
                    }
                }
            else {
                mfxNewspapers.getItems().clear();
                mfxArticlesByNews.getItems().clear();
                mfxNewspapers.getSelectionModel().clearSelection();
            }
            if (delNewsStateNew.getArticlesList() != null && delNewsState.getSuccessMessage() == null) {
                mfxArticlesByNews.getItems().clear();
                mfxArticlesByNews.getItems().addAll(delNewsStateNew.getArticlesList());
            }
            else {
                mfxArticlesByNews.getItems().clear();
            }
            if (delNewsStateNew.getSuccessMessage() != null) {
                getPrincipalController().showAlertInformation(delNewsStateNew.getSuccessMessage());
                delNewsViewModel.loadScreen();
            }
        });

        mfxNewspapers.getSelectionModel().selectionProperty().addListener((observableValue, delNewsState, delNewsStateNew) -> {
            if (delNewsStateNew != null && observableValue.getValue().size() != 0) {
                delNewsViewModel.loadArticles(delNewsStateNew.values().stream().findFirst().orElse(new Newspaper(-1, "", LocalDate.now())));
            }
        });
    }

    public void deleteNewspaper(ActionEvent actionEvent) {
        delNewsViewModel.deleteNewspaper(false);
    }
}
