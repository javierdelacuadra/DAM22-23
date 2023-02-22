package ui.screens.newspapers.listNews;

import io.github.palexdev.materialfx.controls.MFXTableColumn;
import io.github.palexdev.materialfx.controls.MFXTableView;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import jakarta.inject.Inject;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import model.Newspaper;
import ui.screens.common.BaseScreenController;
import ui.screens.common.ScreenConstants;

import java.net.URL;
import java.util.Comparator;
import java.util.ResourceBundle;

public class ListNewsController extends BaseScreenController implements Initializable {


    @FXML
     private MFXTableColumn<String> nameColumn;

    private ListNewsViewModel listNewsViewModel;

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
        MFXTableColumn<Newspaper> newsIdColumn = new MFXTableColumn<>(ScreenConstants.ID, true, Comparator.comparing(Newspaper::getId));
        MFXTableColumn<Newspaper> newsNameColumn = new MFXTableColumn<>(ScreenConstants.NAME, true, Comparator.comparing(Newspaper::getName_newspaper));
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
        listNewsViewModel.getState().addListener((observableValue, listadoState, listadoStateNew) -> {
            if (listadoStateNew.getError() != null) {
                getPrincipalController().showAlertError(listadoStateNew.getError());
            }
            if (listadoStateNew.getNewspapers() != null) {
                mfxNewspapers.getItems().clear();
                mfxNewspapers.getItems().addAll(listadoStateNew.getNewspapers());
            }
            if (listadoStateNew.getOldestReaders() != null) {
                tblReaders.getItems().clear();
                tblReaders.getItems().addAll(listadoStateNew.getOldestReaders());
            }
        });

        mfxNewspapers.getSelectionModel().selectionProperty().addListener((observableValue, newsSelected, newsNewSelected) -> {
            if (newsNewSelected != null && observableValue.getValue().size() != 0) {
                listNewsViewModel.setNewsSelected(newsNewSelected.values().stream().findFirst().orElse(null));
            }
        });
    }

    public void getOldestReaders(ActionEvent actionEvent) {
        listNewsViewModel.getOldestReaders();
    }
}

