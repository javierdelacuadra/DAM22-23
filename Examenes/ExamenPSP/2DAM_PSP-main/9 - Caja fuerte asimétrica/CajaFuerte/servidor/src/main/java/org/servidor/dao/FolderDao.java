package org.servidor.dao;

import org.servidor.dao.modelo.FolderHibernate;
import org.utils.domain.modelo.Folder;

import java.util.List;

public interface FolderDao {
    FolderHibernate getFolder(String userName, String folderName);

    int add(Folder folder);

    List<Folder> getAll(String userName);
}
