package org.servidor.domain.servicios;

import org.utils.domain.modelo.Folder;

import java.util.List;

public interface ServiciosFolder {
    Folder add(Folder folder);

    Folder getFolder(String userName, String folderName);

    Folder changePassword(String userName, String folderName, String oldPassword, String newPassword);

    List<Folder> getAll(String userName);
}
