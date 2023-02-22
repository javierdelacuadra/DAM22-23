package ui.screens.readers.delReaders;

import model.Reader;
import io.github.palexdev.materialfx.controls.MFXTableColumn;
import io.github.palexdev.materialfx.controls.MFXTableView;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import jakarta.inject.Inject;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import ui.screens.common.BaseScreenController;
import ui.screens.common.ScreenConstants;

import java.net.URL;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.ResourceBundle;

public class DelReaderController extends BaseScreenController implements Initializable {

    @FXML
    MFXTableView<Reader> tblReaders;

    DelReaderViewModel delReaderViewModel;
    @Inject
    public DelReaderController(DelReaderViewModel delReaderViewModel) {
        this.delReaderViewModel = delReaderViewModel;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        MFXTableColumn<Reader> idColumn = new MFXTableColumn<>(ScreenConstants.ID, true, Comparator.comparing(Reader::getId));
        MFXTableColumn<Reader> nameReader = new MFXTableColumn<>(ScreenConstants.NAME, true, Comparator.comparing(Reader::getName));
        MFXTableColumn<Reader> birthReader = new MFXTableColumn<>(ScreenConstants.BIRTH, true, Comparator.comparing(Reader::getBirthDate));

        idColumn.setRowCellFactory(reader -> new MFXTableRowCell<>(Reader::getId));
        nameReader.setRowCellFactory(reader -> new MFXTableRowCell<>(Reader::getName));
        birthReader.setRowCellFactory(reader -> new MFXTableRowCell<>(Reader::getBirthDate));
        tblReaders.setFooterVisible(false);
        tblReaders.getTableColumns().addAll(idColumn, nameReader, birthReader);

    }

    @Override
    public void principalCargado(){
        loadReaders();
        delReaderViewModel.loadScreen();
    }
    
    private void loadReaders() {
        delReaderViewModel.getState().addListener((observableValue, delReaderState, delReaderStateNew) -> {
            
            if (delReaderStateNew.getError() != null){
                getPrincipalController().showAlertError(delReaderStateNew.getError());
            }
            if (delReaderStateNew.getWarningMessage() != null) {
                boolean agreed = getPrincipalController().showAlertConfirmation(delReaderStateNew.getWarningMessage());
                if (agreed){
                    delReaderViewModel.deleteReader();
                }
            }
            if (delReaderStateNew.getSuccessMessage() != null) {
                getPrincipalController().showAlertInformation(delReaderStateNew.getSuccessMessage());
            }

            if (delReaderStateNew.getReaderList() != null && !delReaderStateNew.getReaderList().isEmpty()) {
                if (delReaderState.getReaderList() == null) {
                    tblReaders.getItems().clear();
                    tblReaders.getItems().addAll(delReaderStateNew.getReaderList());
                    tblReaders.getSelectionModel().clearSelection();
                }
            }
            else {
                tblReaders.getItems().clear();
                tblReaders.getSelectionModel().clearSelection();
            }
        });

    }

    public void deleteReader(ActionEvent actionEvent) {
        Reader reader = tblReaders.getSelectionModel().getSelection().values().stream().findFirst().orElse(new Reader(-1, "", LocalDate.now()));
        delReaderViewModel.validateReader(reader);
    }

}
