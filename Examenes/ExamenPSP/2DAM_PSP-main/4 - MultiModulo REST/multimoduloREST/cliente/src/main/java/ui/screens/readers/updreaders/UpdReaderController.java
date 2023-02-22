package ui.screens.readers.updreaders;

import io.github.palexdev.materialfx.controls.MFXPasswordField;
import io.github.palexdev.materialfx.controls.MFXTableColumn;
import io.github.palexdev.materialfx.controls.MFXTableView;
import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import jakarta.inject.Inject;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.DatePicker;
import org.miutils.domain.modelo.Login;
import org.miutils.domain.modelo.Reader;
import ui.screens.common.BaseScreenController;

public class UpdReaderController extends BaseScreenController {

    private final UpdReaderViewModel updReaderViewModel;

    @FXML
    private MFXTableView<Reader> tblReaders;

    @FXML
    private MFXTableColumn<Reader> idColumn;
    @FXML
    private MFXTableColumn<Reader> nameColumn;
    @FXML
    private MFXTableColumn<Reader> birthColumn;


    @FXML
    private MFXTextField txtName;

    @FXML
    private MFXTextField txtUser;

    @FXML
    private MFXPasswordField txtPass;

    @FXML
    private DatePicker dpkBirth;

    @Inject
    public UpdReaderController(UpdReaderViewModel updReaderViewModel) {
        this.updReaderViewModel = updReaderViewModel;
    }

    @Override
    public void principalCargado() {
        // EL DUPLICADO ES DE OTRA PANTALLA
        idColumn.setRowCellFactory(reader -> new MFXTableRowCell<>(Reader::getId));
        birthColumn.setRowCellFactory(reader -> new MFXTableRowCell<>(Reader::getBirthDate));
        nameColumn.setRowCellFactory(reader -> new MFXTableRowCell<>(Reader::getName));
        tblReaders.setFooterVisible(false);
        addListeners();
        updReaderViewModel.loadScreen();
    }

    private void addListeners() {
        updReaderViewModel.getState().addListener((observableValue, updReaderState, updReaderStateNew) ->
                Platform.runLater(() -> {
                    if (updReaderStateNew.isLoading())
                        getPrincipalController().root.setCursor(Cursor.WAIT);
                    else
                        getPrincipalController().root.setCursor(Cursor.DEFAULT);

                    handleMessages(updReaderStateNew);

                    if (updReaderStateNew.getAllReaders() != null) {
                        tblReaders.getItems().clear();
                        tblReaders.getItems().addAll(updReaderStateNew.getAllReaders());
                    }
                    checkReaderSelected(updReaderStateNew);
                }));

        tblReaders.getSelectionModel().selectionProperty().addListener((observableValue, selectedReader, selectedReaderNew) -> {
            if (selectedReaderNew != null && observableValue.getValue().size() != 0) {
                updReaderViewModel.getReaderDetails(selectedReaderNew.values().stream().findFirst().orElse(null), selectedReaderNew.keySet().stream().findFirst().orElse(-1));
            }
        });
    }

    private void handleMessages(UpdReaderState updReaderStateNew) {
        if (updReaderStateNew.getError() != null || updReaderStateNew.getConfirmation() != null) {
            if (updReaderStateNew.getError() != null) {
                getPrincipalController().showAlertError(updReaderStateNew.getError());
            } else {
                getPrincipalController().showAlertInformation(updReaderStateNew.getConfirmation());
            }
            updReaderViewModel.clearMessages();
        }
    }

    private void checkReaderSelected(UpdReaderState updReaderStateNew) {
        if (updReaderStateNew.getReaderSelected() != null) {
            Reader actualReader = updReaderStateNew.getReaderSelected();
            txtName.setText(actualReader.getName());
            dpkBirth.setValue(actualReader.getBirthDate());
            txtUser.setText(actualReader.getLogin().getUser());
            txtPass.setText(actualReader.getLogin().getPassword());
        }
    }

    public void updateReader() {
        updReaderViewModel.updateReader(txtName.getText(), dpkBirth.getValue(), txtUser.getText(), txtPass.getText());
    }

    public void addReader() {
        updReaderViewModel.addReader(new Reader(txtName.getText(), dpkBirth.getValue(), new Login(txtUser.getText(), txtPass.getText())));
    }
}
