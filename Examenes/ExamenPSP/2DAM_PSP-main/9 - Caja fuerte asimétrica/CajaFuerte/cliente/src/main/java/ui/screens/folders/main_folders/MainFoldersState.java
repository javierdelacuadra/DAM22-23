package ui.screens.folders.main_folders;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.utils.domain.modelo.Folder;

import java.util.List;

@Data
@AllArgsConstructor
public class MainFoldersState {

    private String error;
    private Folder folderSelected;

    private List<Folder> folders;

    private boolean isFolderFound;
    private String success;

}
