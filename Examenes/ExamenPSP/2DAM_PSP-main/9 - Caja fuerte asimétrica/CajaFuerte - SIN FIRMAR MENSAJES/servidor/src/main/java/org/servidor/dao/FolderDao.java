package org.servidor.dao;

import org.utils.domain.modelo.Folder;

import java.util.List;

public interface FolderDao {
    Folder getFolder(String userName, String folderName);

    Folder changePassword(String userName, String folderName, String passwordOld, String passwordNew);

    int add(Folder folder);

    List<Folder> getAll(String userName);
}
