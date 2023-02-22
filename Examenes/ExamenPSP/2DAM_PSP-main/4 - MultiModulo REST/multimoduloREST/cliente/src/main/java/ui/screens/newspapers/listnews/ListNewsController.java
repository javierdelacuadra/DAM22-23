package ui.screens.newspapers.listnews;

import io.github.palexdev.materialfx.controls.MFXTableColumn;
import io.github.palexdev.materialfx.controls.MFXTableView;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import jakarta.inject.Inject;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import org.miutils.domain.modelo.Newspaper;
import ui.screens.common.BaseScreenController;
import ui.screens.common.ScreenConstants;

import java.net.URL;
import java.util.Comparator;
import java.util.ResourceBundle;

public class ListNewsController extends BaseScreenController implements Initializable {


    @FXML
    private MFXTableColumn<String> nameColumn;

    private final ListNewsViewModel listNewsViewModel;

    @FXML
    private MFXTableView<Newspaper> mfxNewspapers;

    @FXML
    private MFXTableView<String> tblReaders;


    @Inject
    public ListNewsController(ListNewsViewModel listNewsViewModel) {
        this.listNewsViewModel = listNewsViewModel;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        MFXTableColumn<Newspaper> newsNameColumn = new MFXTableColumn<>(ScreenConstants.NAME, true, Comparator.comparing(Newspaper::getName_newspaper));
        MFXTableColumn<Newspaper> newsIdColumn = new MFXTableColumn<>(ScreenConstants.ID, true, Comparator.comparing(Newspaper::getId));
        MFXTableColumn<Newspaper> releaseColumn = new MFXTableColumn<>(ScreenConstants.RELEASED, true, Comparator.comparing(Newspaper::getRelease_date));
        newsIdColumn.setRowCellFactory(newspaper -> new MFXTableRowCell<>(Newspaper::getId));
        newsNameColumn.setMinWidth(140);
        newsNameColumn.setRowCellFactory(newspaper -> new MFXTableRowCell<>(Newspaper::getName_newspaper));
        releaseColumn.setRowCellFactory(newspaper -> new MFXTableRowCell<>(Newspaper::getRelease_date));

        mfxNewspapers.getTableColumns().addAll(newsIdColumn, newsNameColumn, releaseColumn);
        mfxNewspapers.setFooterVisible(false);

        nameColumn.setRowCellFactory(s -> new MFXTableRowCell<>(s1 -> s));
        tblReaders.setFooterVisible(false);

    }

    @Override
    public void principalCargado() {
        stateChanges();
        listNewsViewModel.loadNewspapers();
    }


    private void stateChanges() {
        listNewsViewModel.getState().addListener((observableValue, listadoState, listadoStateNew) -> asyncChanges(listadoStateNew));

        mfxNewspapers.getSelectionModel().selectionProperty().addListener((observableValue, newsSelected, newsNewSelected) -> {
            if (newsNewSelected != null && observableValue.getValue().size() != 0) {
                listNewsViewModel.setNewsSelected(newsNewSelected.values().stream().findFirst().orElse(null));
            }
        });
    }

    private void asyncChanges(ListNewsState listadoStateNew) {
        Platform.runLater(() -> {
            messagesUpdates(listadoStateNew);

            if (listadoStateNew.isLoading()) {
                getPrincipalController().root.setCursor(Cursor.WAIT);
            } else {
                getPrincipalController().root.setCursor(Cursor.DEFAULT);
            }
            if (listadoStateNew.getNewspapers() != null) {
                mfxNewspapers.getItems().clear();
                mfxNewspapers.getItems().addAll(listadoStateNew.getNewspapers());
            } else {
                mfxNewspapers.getItems().clear();
            }
            if (listadoStateNew.getOldestReaders() != null) {
                tblReaders.getItems().clear();
                tblReaders.getItems().addAll(listadoStateNew.getOldestReaders());
            } else {
                tblReaders.getItems().clear();
            }
        });
    }

    private void messagesUpdates(ListNewsState listadoStateNew) {
        if (listadoStateNew.getWarningMessage() != null) {
            boolean agreed = getPrincipalController().showAlertConfirmation(listadoStateNew.getWarningMessage());
            if (agreed) {
                listNewsViewModel.deleteNewspaper(true);
            }
            listNewsViewModel.clearMessages();
        }
        if (listadoStateNew.getSuccessMessage() != null) {
            getPrincipalController().showAlertInformation(listadoStateNew.getSuccessMessage());
            listNewsViewModel.clearMessages();
        }
        if (listadoStateNew.getError() != null) {
            getPrincipalController().showAlertError(listadoStateNew.getError());
            listNewsViewModel.clearMessages();
        }
    }

    public void getOldestReaders() {
        listNewsViewModel.getOldestReaders();
    }

    public void deleteNewspaper() {
        listNewsViewModel.deleteNewspaper(false);
    }

}

