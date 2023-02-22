package ui.screens.folders.main_folders;

import io.github.palexdev.materialfx.controls.*;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import jakarta.inject.Inject;
import javafx.application.Platform;
import javafx.fxml.FXML;
import org.utils.domain.modelo.Folder;
import ui.screens.common.BaseScreenController;
import ui.screens.common.ScreenConstants;

public class MainFoldersController extends BaseScreenController {

    private final MainFoldersViewModel viewModel;

    @FXML
    private MFXCheckbox chkReadOnly;

    @FXML
    private MFXTextField txtNameFolder;

    @FXML
    private MFXPasswordField pwdFolder;
    @FXML private MFXTableColumn<Folder> isOwnerColumn;

    @FXML
    private MFXTableColumn<Folder> nameColumn;
    @FXML
    private MFXTableColumn<Folder> readOnlyColumn;

    @FXML
    private MFXTableColumn<Folder> idColumn;
    @FXML
    private MFXTableView<Folder> mfxFolders;



    @Inject
    public MainFoldersController(MainFoldersViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void principalCargado() {
        nameColumn.setRowCellFactory(folder -> new MFXTableRowCell<>(Folder::getName));
        readOnlyColumn.setRowCellFactory(folder -> new MFXTableRowCell<>(folder1 -> folder1.isReadOnly() ? ScreenConstants.SI : ScreenConstants.NO));
        isOwnerColumn.setRowCellFactory(folder -> new MFXTableRowCell<>(folder1 -> folder1.isOwner()? ScreenConstants.SI : ScreenConstants.NO));
        idColumn.setRowCellFactory(folder -> new MFXTableRowCell<>(Folder::getId));
        mfxFolders.setFooterVisible(false);
        stateListener();

        viewModel.getFoldersByUser(getPrincipalController().getUserLoggedIn().getName());
        tblListener();


    }

    private void tblListener() {
        mfxFolders.getSelectionModel().selectionProperty().addListener((observableValue, folder, folderNew) -> {
            if (folderNew != null && !observableValue.getValue().isEmpty()) {
                viewModel.setFolder(folderNew.values().stream().findFirst().orElse(null));
            }
        });
    }

    private void stateListener() {
        viewModel.getState().addListener((observable, oldValue, newValue) -> Platform.runLater(() -> {
        if (newValue.getError() != null) {
            getPrincipalController().showAlertError(newValue.getError());
            viewModel.clearMessage();
        }
        if (newValue.getFolders() != null) {
            mfxFolders.getItems().clear();
            mfxFolders.getItems().addAll(newValue.getFolders());
        }
        if (newValue.getFolderSelected() != null) {
            txtNameFolder.setText(newValue.getFolderSelected().getName());
        }
        if (newValue.getSuccess() != null) {
            getPrincipalController().showAlertInformation(newValue.getSuccess());
            viewModel.clearMessage();
        }
        if (newValue.isFolderFound()){
            //El usuario ingresó una carpeta con credenciales válidas, vamos a los mensajes
            getPrincipalController().goToFolderMessages(newValue.getFolderSelected());
        }
    }));
        txtNameFolder.textProperty().addListener((observableValue, s, t1) -> pwdFolder.setText(t1));
        txtNameFolder.setOnAction(actionEvent -> searchFolder());
    }


    public void searchFolder() {
        viewModel.tryGetFolder(getPrincipalController().getUserLoggedIn().getName(), txtNameFolder.getText(), pwdFolder.getText());
    }

    public void addFolder() {
        viewModel.addFolder(new Folder(txtNameFolder.getText(), pwdFolder.getText(), getPrincipalController().getUserLoggedIn().getName(), true, chkReadOnly.isSelected()));
    }
}
