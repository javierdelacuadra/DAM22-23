package ui.screens.messages.main_messages;

import common.CachedCredentials;
import domain.serivces.impl.FolderServicesImpl;
import domain.serivces.impl.MessageServicesImpl;
import jakarta.inject.Inject;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import org.utils.domain.modelo.Folder;
import org.utils.domain.modelo.Message;
import ui.screens.common.ScreenConstants;

public class MainMessagesViewModel {
    private final MessageServicesImpl messageServicesImpl;
    private final FolderServicesImpl folderServices;

    private final CachedCredentials ca;

    @Inject
    public MainMessagesViewModel(MessageServicesImpl messageServicesImpl, FolderServicesImpl folderServices, CachedCredentials ca) {
        this.messageServicesImpl = messageServicesImpl;
        this.folderServices = folderServices;
        this.ca = ca;
        _state = new SimpleObjectProperty<>(new MainMessagesState(null, null, null, null, null));
    }


    private final ObjectProperty<MainMessagesState> _state;

    public ReadOnlyObjectProperty<MainMessagesState> getState() {
        return _state;
    }

    private MainMessagesState copyOf(){return new MainMessagesState(_state.get().getError(), _state.get().getWorkingFolder(), _state.get().getMessages(), _state.get().getMessageSelected(), _state.get().getSuccess());}

    public void initialLoad(Folder folder){
        MainMessagesState ps = copyOf();
        ps.setWorkingFolder(folder);
        ps.setMessages(folder.getMessages());
        _state.setValue(ps);
    }

    public void selectMessage(Message message){
        MainMessagesState ps = copyOf();
        ps.setMessageSelected(message);
        _state.setValue(ps);
    }

    public void clearSelection(){
        MainMessagesState ps = copyOf();
        ps.setMessageSelected(null);
        _state.setValue(ps);
    }

    public void deleteMessage(){
        Message messageSelected = _state.get().getMessageSelected();
        if(messageSelected == null){
            MainMessagesState ps = copyOf();
            ps.setError(ScreenConstants.NO_HAY_MENSAJE_SELECCIONADO);
            _state.setValue(ps);
        }
        else{
            messageServicesImpl.delete(messageSelected.getId())
                    .subscribe(message -> {
                        MainMessagesState ps = copyOf();
                        if(message.isRight()){
                            ps.setSuccess(ScreenConstants.MENSAJE_ELIMINADO);
                            ps.getWorkingFolder().getMessages().remove(messageSelected);
                            ps.setMessages(ps.getWorkingFolder().getMessages());
                            ps.setMessageSelected(null);
                        }
                        else{
                            ps.setError(message.getLeft());
                            ps.setMessageSelected(null);
                        }
                        _state.setValue(ps);
                    });
        }
    }

    public void addMessage(String message, String issuer){
        if (message.isEmpty()){
            MainMessagesState ps = copyOf();
            ps.setError(ScreenConstants.EL_MENSAJE_NO_PUEDE_ESTAR_VACIO);
            _state.setValue(ps);
        } else {
            Message newMessage = new Message(message, _state.get().getWorkingFolder().getId(), issuer);
            messageServicesImpl.add(newMessage)
                    .subscribe(messageAddedEither -> {
                        MainMessagesState ps = copyOf();
                        if (messageAddedEither.isRight()) {
                            ps.setSuccess(ScreenConstants.MENSAJE_ANADIDO);
                            Message messageAdded = messageAddedEither.get();
                            // se ha encriptado el nuevo mensaje, le cambiamos el texto al original
                            messageAdded.setMessageText(message);
                            ps.getWorkingFolder().getMessages().add(messageAdded);
                            ps.setMessages(ps.getWorkingFolder().getMessages());
                            ps.setMessageSelected(null);
                        } else {
                            ps.setError(messageAddedEither.getLeft());
                            ps.setMessageSelected(null);
                        }
                        _state.setValue(ps);
                    });
        }
    }

    public void updateMessage(String message, String issuer){
        Message messageSelected = _state.get().getMessageSelected();
        if(messageSelected == null){
            MainMessagesState ps = copyOf();
            ps.setError(ScreenConstants.NO_HAY_MENSAJE_SELECCIONADO);
            _state.setValue(ps);
        }
        else{
            Message newMessage = new Message(messageSelected.getId(), message, messageSelected.getMessageTextSigned(), messageSelected.getFolder(), issuer);
            messageServicesImpl.update(newMessage)
                    .subscribe(messageUpdated -> {
                        MainMessagesState ps = copyOf();
                        if(messageUpdated.isRight()){
                            ps.setSuccess(ScreenConstants.MENSAJE_ACTUALIZADO);
                            newMessage.setMessageText(message);
                            ps.getWorkingFolder().getMessages().set(ps.getWorkingFolder().getMessages().indexOf(messageSelected), newMessage);
                            ps.setMessages(ps.getWorkingFolder().getMessages());
                            ps.setMessageSelected(null);
                        }
                        else{
                            ps.setError(messageUpdated.getLeft());
                            ps.setMessageSelected(null);
                        }
                        _state.setValue(ps);
                    });
        }
    }


    public void clearMessages() {
        MainMessagesState ps = copyOf();
        ps.setSuccess(null);
        ps.setError(null);
        _state.setValue(ps);
    }

    public void shareFolder(Folder folderSelected, String nameUserToShare) {
        MainMessagesState ms = copyOf();
        if (folderSelected.isOwner() && !nameUserToShare.isEmpty() && !nameUserToShare.equals(folderSelected.getNameUserWithAccess())) {
            Folder newFolder = new Folder(folderSelected.getName(), ca.getFolderPass(), nameUserToShare,false, folderSelected.isReadOnly());
            newFolder.setOriginalFolderId(folderSelected.getId());
            folderServices.add(newFolder)
                    .subscribe(folderEither -> {
                        MainMessagesState ps = copyOf();
                        if (folderEither.isRight()) {
                            ps.setSuccess(ScreenConstants.CARPETA_COMPARTIDA + nameUserToShare);
                        } else {
                            ps.setError(folderEither.getLeft());
                        }
                        _state.setValue(ps);
                    });
        } else {
            if (!folderSelected.isOwner())
                ms.setError(ScreenConstants.NO_ES_EL_PROPIETARIO);
            else
                ms.setError(ScreenConstants.INGRESE_UN_USUARIO_QUE_NO_SEA_EL_PROPIO);
            _state.setValue(ms);
        }
    }
}