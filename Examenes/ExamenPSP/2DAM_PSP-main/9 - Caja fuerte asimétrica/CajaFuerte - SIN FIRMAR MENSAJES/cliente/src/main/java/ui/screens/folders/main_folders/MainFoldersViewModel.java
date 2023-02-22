package ui.screens.folders.main_folders;

import common.CachedCredentials;
import domain.serivces.impl.FolderServicesImpl;
import domain.serivces.impl.MessageServicesImpl;
import io.reactivex.rxjava3.schedulers.Schedulers;
import jakarta.inject.Inject;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import org.utils.common.AplicationConstants;
import org.utils.domain.modelo.Folder;
import org.utils.domain.modelo.Message;
import ui.screens.common.ScreenConstants;

import java.util.List;
import java.util.Objects;


public class MainFoldersViewModel {

    private final FolderServicesImpl foldersServicesImpl;

    private final MessageServicesImpl messageServices;

    private final CachedCredentials ca;

    @Inject
    public MainFoldersViewModel(FolderServicesImpl folderServices, MessageServicesImpl messageServices, CachedCredentials ca) {
        this.foldersServicesImpl = folderServices;
        this.messageServices = messageServices;
        this.ca = ca;
        _state = new SimpleObjectProperty<>(new MainFoldersState(null, null, null, false, null));
    }


    private final ObjectProperty<MainFoldersState> _state;

    public ReadOnlyObjectProperty<MainFoldersState> getState() {
        return _state;
    }

    public void tryGetFolder(String nameUser, String nameFolder, String passFolder) {
        if (nameFolder == null || nameFolder.isEmpty() || passFolder == null || passFolder.isEmpty() || nameUser == null || nameUser.isEmpty()) {
            _state.setValue(new MainFoldersState(AplicationConstants.COMPLETA_TODOS_LOS_CAMPOS, null, _state.get().getFolders(), false, null));
        }
        else {
            foldersServicesImpl.get(nameUser, nameFolder, passFolder)
                    .observeOn(Schedulers.single())
                    .subscribe(folder -> {
                        MainFoldersState ps = copyOf();
                        if (folder.isRight()) {
                            //Existe una folder con ese nombre de usuario y carpeta. Comprobamos la contraseña en servicios antes de devolverlo aqui.
                            ca.setFolderPass(passFolder);
                            ps.setFolderSelected(folder.get());
                            List<Message> messages = messageServices.decrypt(folder.get().getMessages());
                            folder.get().setMessages(messages);
                            ps.setFolderFound(true);
                        } else {
                            ps.setError(folder.getLeft());
                        }
                        _state.setValue(ps);
                    });
        }
    }

    public void getFoldersByUser(String name) {
        foldersServicesImpl.getAll(name)
                .observeOn(Schedulers.single())
                .subscribe(folders -> {
                    MainFoldersState ps;
                    if (folders.isRight()) {
                        ps = new MainFoldersState(null, null, folders.get(), false, null);
                    } else {
                        ps = new MainFoldersState(folders.getLeft(), null, null, false, null);
                    }
                    _state.setValue(ps);
                });
    }

    public void clearMessage() {
        MainFoldersState m = copyOf();
        m.setError(null);
        m.setSuccess(null);
        _state.setValue(m);
    }

    public void setFolder(Folder f) {
        MainFoldersState m = copyOf();
        m.setFolderSelected(f);
        _state.setValue(m);
    }

    private MainFoldersState copyOf() {
        return new MainFoldersState(_state.get().getError(), _state.get().getFolderSelected(), _state.get().getFolders(), _state.get().isFolderFound(), _state.get().getSuccess());
    }

    public void addFolder(Folder f) {
        MainFoldersState m = copyOf();
        if (f.getName() == null || f.getName().isEmpty() || f.getPassword() == null || f.getPassword().isEmpty()) {
            m.setError(AplicationConstants.COMPLETA_TODOS_LOS_CAMPOS);
        }
        else {
            foldersServicesImpl.add(f)
                    .observeOn(Schedulers.single())
                    .subscribe(response -> {
                        MainFoldersState ps = copyOf();
                        if (response.isRight()) {
                            String body = Objects.requireNonNull(response.get().body()).toString();
                            f.setId(Integer.parseInt(body.substring(body.indexOf(ScreenConstants.ID)+3, body.indexOf(ScreenConstants.PUNTO))));
                            ps.setFolderSelected(f);
                            ps.setSuccess(ScreenConstants.CARPETA_AGREGADA_CON_EXITO);
                            ps.getFolders().add(f);
                        } else {
                            ps.setError(response.getLeft());
                            ps.setFolderSelected(null);
                        }
                        _state.setValue(ps);
                    });
        }
    }

}
