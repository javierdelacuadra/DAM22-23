package org.servidor.domain.servicios.impl;

import jakarta.inject.Inject;
import org.servidor.dao.impl.FolderDaoImpl;
import org.servidor.domain.errores.BaseDeDatosException;
import org.servidor.domain.errores.InvalidFieldsException;
import org.servidor.domain.servicios.ServiciosFolder;
import org.servidor.domain.servicios.common.ServicesConstants;
import org.utils.common.AplicationConstants;
import org.utils.domain.modelo.Folder;

import java.util.List;

public class ServiciosFolderImpl implements ServiciosFolder {

    private final FolderDaoImpl folderDao;

    @Inject
    public ServiciosFolderImpl(FolderDaoImpl folderDao) {
        this.folderDao = folderDao;
    }

    @Override
    public Folder add(Folder folder) {
        if (!folder.getName().isEmpty() && !folder.getPassword().isEmpty()) {
            if (folderDao.add(folder) > 0) {
                return folder;
            }
            throw new InvalidFieldsException(ServicesConstants.NO_SE_PUDO_AGREGAR_LA_FOLDER);
        }
        throw new InvalidFieldsException(AplicationConstants.COMPLETA_TODOS_LOS_CAMPOS);
    }

    @Override
    public Folder getFolder(String userName, String folderPassword, String folderName) {
        return folderDao.getFolder(userName, folderPassword, folderName);
    }

    @Override
    public Folder changePassword(String userName, String folderName, String oldPassword, String newPassword) {
        if (!userName.isEmpty() && !folderName.isEmpty() && !oldPassword.isEmpty() && !newPassword.isEmpty()) {
            Folder f = folderDao.changePassword(userName, folderName, oldPassword, newPassword);
            if (f != null) {
                return f;
            }
            throw new BaseDeDatosException(ServicesConstants.NO_SE_PUDO_CAMBIAR_LA_CONTRASENA);
        }
        throw new InvalidFieldsException(AplicationConstants.COMPLETA_TODOS_LOS_CAMPOS);
    }

    @Override
    public List<Folder> getAll(String userName) {
        return folderDao.getAll(userName);
    }
}
