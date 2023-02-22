package ui.screens.readers.addReaders;

import io.github.palexdev.materialfx.controls.MFXPasswordField;
import io.github.palexdev.materialfx.controls.MFXTableColumn;
import io.github.palexdev.materialfx.controls.MFXTableView;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import lombok.extern.java.Log;
import model.Login;
import model.Reader;
import io.github.palexdev.materialfx.controls.MFXTextField;
import jakarta.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import ui.screens.common.BaseScreenController;

public class AddReaderController extends BaseScreenController {

    @FXML
    private MFXTextField txtUser;

    @FXML
    private MFXPasswordField txtPass;
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
    private DatePicker dpkBirth;

    private AddReaderViewModel addReaderViewModel;

    @Inject
    public AddReaderController(AddReaderViewModel addReaderViewModel) {
        this.addReaderViewModel = addReaderViewModel;
    }

    @Override
    public void principalCargado() {
        idColumn.setRowCellFactory(newspaper -> new MFXTableRowCell<>(Reader::getId));
        nameColumn.setRowCellFactory(newspaper -> new MFXTableRowCell<>(Reader::getName));
        birthColumn.setRowCellFactory(newspaper -> new MFXTableRowCell<>(Reader::getBirthDate));
        tblReaders.setFooterVisible(false);
        addListener();
        addReaderViewModel.loadScreen();
    }

    private void addListener() {
        addReaderViewModel.getState().addListener((observableValue, addReaderState, addReaderStateNew) -> {
            if (addReaderStateNew.getError() != null){
                getPrincipalController().showAlertError(addReaderStateNew.getError());
            }
            if (addReaderStateNew.getWelcomeMessage() != null) {
                getPrincipalController().showAlertInformation(addReaderStateNew.getWelcomeMessage());
            }
            if (addReaderStateNew.getAllReaders() != null || (addReaderState.getAllReaders() != null && addReaderStateNew.getAllReaders().size() != addReaderState.getAllReaders().size())) {
                tblReaders.getItems().clear();
                tblReaders.getItems().addAll(addReaderStateNew.getAllReaders());
            }
            addReaderViewModel.clearState();
        });
    }

    public void addReader(){
        addReaderViewModel.addReader(new Reader(txtName.getText(), dpkBirth.getValue(), new Login(txtUser.getText(), txtPass.getText())));
    }

}
