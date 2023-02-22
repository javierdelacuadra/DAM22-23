package ui.screens.messages.main_messages;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTableColumn;
import io.github.palexdev.materialfx.controls.MFXTableView;
import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import jakarta.inject.Inject;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import org.utils.domain.modelo.Folder;
import org.utils.domain.modelo.Message;
import ui.screens.common.BaseScreenController;
import ui.screens.common.Screens;


public class MainMessagesController extends BaseScreenController {
    private final MainMessagesViewModel viewModel;

    @FXML private MFXTextField txtNewPassword;
    @FXML
    private MFXButton btnUpd;
    @FXML private MFXButton btnAdd;
    @FXML private MFXTableView<Message> tblMessages;
    @FXML private MFXTableColumn<Message> idColumn;
    @FXML private MFXTableColumn<Message> messageColumn;
    @FXML private MFXButton btnClear;
    @FXML private MFXButton btnDelete;
    @FXML private TextArea txtMessage;

    @FXML
    private Label txtTitle;

    private boolean readOnly = false;

    @Inject
    public MainMessagesController(MainMessagesViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void principalCargado(){
        Folder folderLogged = getPrincipalController().getFolderSelected();
        txtTitle.setText(txtTitle.getText() + folderLogged.getName());

        idColumn.setRowCellFactory(message -> new MFXTableRowCell<>(Message::getId));
        messageColumn.setRowCellFactory(message -> new MFXTableRowCell<>(Message::getMessageText));
        tblListener();
        stateListener();
        if (!getPrincipalController().getUserLoggedIn().getName().equals(folderLogged.getOwner())){
            readOnly = folderLogged.isReadOnly();
            if (readOnly){
                btnAdd.setVisible(false);
                btnDelete.setVisible(false);
                btnUpd.setVisible(false);
            }
        }
        viewModel.initialLoad(folderLogged);

    }

    private void stateListener() {
        viewModel.getState().addListener((observableValue, mainMessagesState, stateNew) -> Platform.runLater(() -> {
            if (stateNew.getError() != null){
                getPrincipalController().showAlertError(stateNew.getError());
                viewModel.clearMessages();
            }
            if (stateNew.getSuccess() != null){
                getPrincipalController().showAlertInformation(stateNew.getSuccess());
                viewModel.clearMessages();
            }
            if (stateNew.getMessages() != null){
                tblMessages.getItems().clear();
                tblMessages.getItems().addAll(stateNew.getMessages());
            }
            if (stateNew.getMessageSelected() != null){
                txtMessage.setText(stateNew.getMessageSelected().getMessageText());
                changeBtnsVisibility(true);
            } else {
                txtMessage.setText("");
                changeBtnsVisibility(false);
            }
        }));
    }

    private void changeBtnsVisibility(boolean isMsgSelected) {
    if (!readOnly) {
            btnDelete.setVisible(isMsgSelected);
            btnUpd.setVisible(isMsgSelected);
            btnClear.setVisible(isMsgSelected);
            btnAdd.setVisible(!isMsgSelected);
        }
    }

    private void tblListener() {
        tblMessages.getSelectionModel().selectionProperty().addListener((observableValue, message, messageNew) -> {
            if (messageNew != null && !observableValue.getValue().isEmpty()) {
                viewModel.selectMessage(messageNew.values().stream().findFirst().orElse(null));
            }
        });
    }


    public void deleteMessage(){
        viewModel.deleteMessage();
    }

    public void clearSelection() {
        viewModel.clearSelection();
    }

    public void addMessage() {
        viewModel.addMessage(txtMessage.getText());
    }

    public void updateMessage() {
        viewModel.updateMessage(txtMessage.getText());
    }

    public void backToMyFolders() {
        getPrincipalController().cargarPantalla(Screens.MAIN_FOLDERS);
    }

    public void changePass() {
        viewModel.changePass(getPrincipalController().getUserLoggedIn().getName(), getPrincipalController().getFolderSelected().getName(), txtNewPassword.getText());
    }
}
