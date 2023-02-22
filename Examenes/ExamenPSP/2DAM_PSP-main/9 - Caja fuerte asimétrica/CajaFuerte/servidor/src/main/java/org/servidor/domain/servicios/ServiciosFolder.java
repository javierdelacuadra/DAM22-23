package org.servidor.domain.servicios;

import org.utils.domain.modelo.Folder;

import java.util.List;

public interface ServiciosFolder {
    Folder add(Folder folder);

    Folder getFolder(String userName, String folderName);

    List<Folder> getAll(String userName);
}
