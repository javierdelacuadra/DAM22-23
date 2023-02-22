package ui.screens.articles.listArticles;

import io.github.palexdev.materialfx.controls.MFXTableColumn;
import io.github.palexdev.materialfx.controls.MFXTableView;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import jakarta.inject.Inject;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import model.Article;
import model.TypeArt;
import ui.screens.common.BaseScreenController;
import ui.screens.common.ScreenConstants;

import java.util.Comparator;

public class ListArticlesController extends BaseScreenController {

    @FXML
    private MFXTableView<Article> tblArticles;

    @FXML
    private ComboBox cmbTypes;

    @FXML
    private TextArea txtDescription;

    @FXML
    private Label txtNumberReaders;
    @FXML
    private MFXTableView<Article> tblArticlesTypes;

    ListArticlesViewModel listArticlesViewModel;

    @Inject
    public ListArticlesController(ListArticlesViewModel listArticlesViewModel) {
        this.listArticlesViewModel = listArticlesViewModel;
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
        nameColumn.setRowCellFactory(article -> new MFXTableRowCell<>(Article::getName_article));
        descColumn.setRowCellFactory(article -> new MFXTableRowCell<>(Article::getDescription));
        idNewsColumn.setRowCellFactory(article -> new MFXTableRowCell<>(Article::getId_newspaper));
        tblArticles.setFooterVisible(false);
        tblArticles.getTableColumns().addAll(idColumn, nameColumn, descColumn, idTypeColumn, idNewsColumn);

        MFXTableColumn<Article> idColumnFilter = new MFXTableColumn<>(ScreenConstants.ID, true, Comparator.comparing(Article::getId));
        MFXTableColumn<Article> nameColumnFilter = new MFXTableColumn<>(ScreenConstants.NAME, true, Comparator.comparing(Article::getName_article));
        MFXTableColumn<Article> descColumnFilter = new MFXTableColumn<>(ScreenConstants.DESCRIPTION, true, Comparator.comparing(Article::getDescription));
        MFXTableColumn<Article> idTypeColumnFilter = new MFXTableColumn<>(ScreenConstants.TYPE, true, Comparator.comparing(Article::getId_type));
        MFXTableColumn<Article> idNewsColumnFilter = new MFXTableColumn<>(ScreenConstants.NEWSPAPER, true, Comparator.comparing(Article::getId_newspaper));

        idColumnFilter.setRowCellFactory(article -> new MFXTableRowCell<>(Article::getId));
        idTypeColumnFilter.setRowCellFactory(article -> new MFXTableRowCell<>(Article::getId_type));
        descColumnFilter.setRowCellFactory(article -> new MFXTableRowCell<>(Article::getDescription));
        nameColumnFilter.setRowCellFactory(article -> new MFXTableRowCell<>(Article::getName_article));
        idNewsColumnFilter.setRowCellFactory(article -> new MFXTableRowCell<>(Article::getId_newspaper));
        tblArticlesTypes.setFooterVisible(false);
        tblArticlesTypes.getTableColumns().addAll(idColumnFilter, nameColumnFilter, descColumnFilter, idTypeColumnFilter, idNewsColumnFilter);

        addListener();
        listArticlesViewModel.loadArticlesAndTypes();
    }

    private void addListener() {
        listArticlesViewModel.getState().addListener((observableValue, listadoState, listadoStateNew) -> {
            if (listadoStateNew.getError() != null) {
                getPrincipalController().showAlertError(listadoStateNew.getError());
            }
            if (listadoStateNew.getArticles() != null && listadoStateNew.getTypeArts() != null && listadoStateNew.getArticlesFiltered() != null) {
                tblArticles.getItems().clear();
                tblArticles.getItems().addAll(listadoStateNew.getArticles());
                if (listadoStateNew.getTypeArts() != listadoState.getTypeArts()) {
                    cmbTypes.getItems().clear();
                    cmbTypes.getItems().addAll(listadoStateNew.getTypeArts());
                    cmbTypes.setValue(listadoStateNew.getTypeArts().get(0));
                }

                tblArticlesTypes.getItems().clear();
                tblArticlesTypes.getItems().addAll(listadoStateNew.getArticlesFiltered());
            }

            if (listadoStateNew.getQueryDescAndReaders() != null) {
                txtDescription.setText(listadoStateNew.getQueryDescAndReaders().getDescription());
                txtNumberReaders.setText(String.valueOf(listadoStateNew.getQueryDescAndReaders().getNumberReaders()));
            }

        });

        tblArticles.getSelectionModel().selectionProperty().addListener((observableValue, oldSelection, newSelection) -> {
            if (newSelection != null && observableValue.getValue().size() != 0) {
                listArticlesViewModel.updateQuery(newSelection.values().stream().findFirst().orElse(null));
            }
        });
    }

    public void changeType(ActionEvent actionEvent) {
        listArticlesViewModel.loadArticlesByType(new TypeArt((cmbTypes.getSelectionModel().getSelectedIndex() + 1), cmbTypes.getSelectionModel().getSelectedItem().toString()));
    }


}
