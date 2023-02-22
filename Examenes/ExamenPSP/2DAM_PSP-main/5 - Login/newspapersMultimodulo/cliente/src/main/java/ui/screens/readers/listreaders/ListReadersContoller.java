package ui.screens.readers.listreaders;

import io.github.palexdev.materialfx.controls.MFXTableColumn;
import io.github.palexdev.materialfx.controls.MFXTableView;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import jakarta.inject.Inject;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.ComboBox;
import org.miutils.domain.modelo.Newspaper;
import org.miutils.domain.modelo.Reader;
import org.miutils.domain.modelo.TypeArt;
import ui.screens.common.BaseScreenController;
import ui.screens.common.ScreenConstants;

import java.time.LocalDate;
import java.util.Comparator;

public class ListReadersContoller extends BaseScreenController {


    @FXML
    private ComboBox<Newspaper> cmbNews;

    @FXML
    private MFXTableView<Reader> tblReadersNews;
    @FXML
    private MFXTableView<Reader> tblReaders;

    @FXML
    private ComboBox<TypeArt> cmbTypes;
    @FXML
    private MFXTableView<Reader> tblReadersTypes;

    ListReadersViewModel listReaderDBsViewModel;

    @Inject
    public ListReadersContoller(ListReadersViewModel listReaderDBsViewModel) {
        this.listReaderDBsViewModel = listReaderDBsViewModel;
    }

    @Override
    public void principalCargado() {

        initializeTable(tblReaders);

        initializeTable(tblReadersTypes);

        initializeTable(tblReadersNews);


        addListener();
        listReaderDBsViewModel.loadScreen();

    }

    private void initializeTable(MFXTableView<Reader> tblReaders) {
        MFXTableColumn<Reader> idColumn = new MFXTableColumn<>(ScreenConstants.ID, true, Comparator.comparing(Reader::getId));
        MFXTableColumn<Reader> nameReaderDB = new MFXTableColumn<>(ScreenConstants.NAME, true, Comparator.comparing(Reader::getName));
        MFXTableColumn<Reader> birthReaderDB = new MFXTableColumn<>(ScreenConstants.BIRTH, true, Comparator.comparing(Reader::getBirthDate));

        rowCell(tblReaders, idColumn, nameReaderDB, birthReaderDB);
        tblReaders.getTableColumns().addAll(idColumn, nameReaderDB, birthReaderDB);
    }

    private void rowCell(MFXTableView<Reader> tblReaders, MFXTableColumn<Reader> idColumn, MFXTableColumn<Reader> nameReaderDB, MFXTableColumn<Reader> birthReaderDB) {
        idColumn.setRowCellFactory(reader -> new MFXTableRowCell<>(Reader::getId));
        nameReaderDB.setRowCellFactory(reader -> new MFXTableRowCell<>(Reader::getName));
        tblReaders.setFooterVisible(false);
        birthReaderDB.setRowCellFactory(reader -> new MFXTableRowCell<>(Reader::getBirthDate));
    }


    private void addListener() {
        listReaderDBsViewModel.getState().addListener((observableValue, listadoState, listadoStateNew) -> runLaterChanges(listadoState, listadoStateNew));
    }

    private void runLaterChanges(ListReadersState listadoState, ListReadersState listadoStateNew) {
        Platform.runLater(() -> {
            if (listadoStateNew.isLoading())
                getPrincipalController().root.setCursor(Cursor.WAIT);
            else
                getPrincipalController().root.setCursor(Cursor.DEFAULT);

            checkMessages(listadoStateNew);

            if (listadoStateNew.getReaders() != null) {
                validReaders(listadoState, listadoStateNew);
            }
            if (listadoStateNew.getReadersFilteredType() == null) {
                tblReadersTypes.getItems().clear();
            }
            if (listadoStateNew.getReadersFilteredNewspaper() == null) {
                tblReadersNews.getItems().clear();
            }
        });
    }

    private void checkMessages(ListReadersState listadoStateNew) {
        if (listadoStateNew.getError() != null) {
            getPrincipalController().showAlertError(listadoStateNew.getError());
            listReaderDBsViewModel.clearMessages();
        }
        if (listadoStateNew.getWarningMessage() != null) {
            boolean agreed = getPrincipalController().showAlertConfirmation(listadoStateNew.getWarningMessage());
            if (agreed) {
                listReaderDBsViewModel.deleteReader(listadoStateNew.getReaderSelected(), getPrincipalController().getReaderLoggedIn());

            }
            listReaderDBsViewModel.clearMessages();
        }
        if (listadoStateNew.getSuccessMessage() != null) {
            getPrincipalController().showAlertInformation(listadoStateNew.getSuccessMessage());
            if (listadoStateNew.getSuccessMessage().equals(ScreenConstants.YOU_HAVE_DELETED_YOURSELF)) {
                getPrincipalController().backToLogin();
            }
            listReaderDBsViewModel.clearMessages();
        }
    }

    private void validReaders(ListReadersState listadoState, ListReadersState listadoStateNew) {
        tblReaders.getItems().clear();
        tblReaders.getItems().addAll(listadoStateNew.getReaders());
        if (listadoStateNew.getTypeArts() != listadoState.getTypeArts()) {
            cmbTypes.getItems().clear();
            cmbTypes.getItems().addAll(listadoStateNew.getTypeArts());
            cmbTypes.setValue(listadoStateNew.getTypeArts().get(0));
        }

        if (listadoStateNew.getNewspaperList() != listadoState.getNewspaperList()) {
            cmbNews.getItems().clear();
            cmbNews.getItems().addAll(listadoStateNew.getNewspaperList());
            cmbNews.getSelectionModel().selectFirst();
        }
        if (listadoStateNew.getReadersFilteredType() != null) {
            tblReadersTypes.getItems().clear();
            tblReadersTypes.getItems().addAll(listadoStateNew.getReadersFilteredType());
        }
        if (listadoStateNew.getReadersFilteredNewspaper() != null) {
            tblReadersNews.getItems().clear();
            tblReadersNews.getItems().addAll(listadoStateNew.getReadersFilteredNewspaper());
        }
    }

    public void changeType() {
        listReaderDBsViewModel.loadReadersByType(cmbTypes.getSelectionModel().getSelectedItem());
    }

    public void changeNews() {
        listReaderDBsViewModel.loadReadersByNews(cmbNews.getSelectionModel().getSelectedItem());
    }

    public void deleteReader() {
        Reader reader = tblReaders.getSelectionModel().getSelection().values().stream().findFirst().orElse(new Reader(-1, "", LocalDate.now()));
        listReaderDBsViewModel.validateReader(reader);
    }
}
