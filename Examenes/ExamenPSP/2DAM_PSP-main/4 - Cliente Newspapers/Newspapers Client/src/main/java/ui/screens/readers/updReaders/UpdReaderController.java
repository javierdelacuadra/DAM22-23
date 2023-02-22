package ui.screens.readers.updReaders;

import io.github.palexdev.materialfx.controls.MFXPasswordField;
import model.Reader;
import io.github.palexdev.materialfx.controls.MFXTableColumn;
import io.github.palexdev.materialfx.controls.MFXTableView;
import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import jakarta.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import ui.screens.common.BaseScreenController;

public class UpdReaderController extends BaseScreenController {

    private UpdReaderViewModel updReaderViewModel;

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
        idColumn.setRowCellFactory(newspaper -> new MFXTableRowCell<>(Reader::getId));
        nameColumn.setRowCellFactory(newspaper -> new MFXTableRowCell<>(Reader::getName));
        birthColumn.setRowCellFactory(newspaper -> new MFXTableRowCell<>(Reader::getBirthDate));
        tblReaders.setFooterVisible(false);
        addListeners();
        updReaderViewModel.loadScreen();
    }

    private void addListeners() {
        updReaderViewModel.getState().addListener((observableValue, updReaderState, updReaderStateNew) -> {
            if (updReaderStateNew.getError() != null || updReaderStateNew.getConfirmation() != null) {
                if (updReaderStateNew.getError() != null) {
                    getPrincipalController().showAlertError(updReaderStateNew.getError());
                } else {
                    getPrincipalController().showAlertInformation(updReaderStateNew.getConfirmation());
                }
                updReaderViewModel.clearMessages();
            }
            if (updReaderStateNew.getAllReaders() != null){
                tblReaders.getItems().clear();
                tblReaders.getItems().addAll(updReaderStateNew.getAllReaders());
            }
            if (updReaderStateNew.getReaderSelected() != null){
                Reader actualReader = updReaderStateNew.getReaderSelected();
                txtName.setText(actualReader.getName());
                dpkBirth.setValue(actualReader.getBirthDate());
                txtUser.setText(actualReader.getLogin().getUser());
                txtPass.setText(actualReader.getLogin().getPassword());
            }
            else {
                txtName.clear();
                dpkBirth.setValue(null);
            }
        });

        tblReaders.getSelectionModel().selectionProperty().addListener((observableValue, selectedReader, selectedReaderNew) -> {
            if (selectedReaderNew != null && observableValue.getValue().size() != 0) {
                updReaderViewModel.getReaderDetails(selectedReaderNew.values().stream().findFirst().orElse(null), selectedReaderNew.keySet().stream().findFirst().orElse(-1));
            }
        });
    }

    public void updateReader(){
        updReaderViewModel.updateReader(txtName.getText(), dpkBirth.getValue(), txtUser.getText(), txtPass.getText());
    }
}
