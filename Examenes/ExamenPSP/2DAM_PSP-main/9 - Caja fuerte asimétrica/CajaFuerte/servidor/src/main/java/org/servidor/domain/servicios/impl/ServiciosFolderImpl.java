package org.servidor.domain.servicios.impl;

import jakarta.inject.Inject;
import org.seguridad.EncriptacionAsimetrica;
import org.servidor.dao.impl.FolderDaoImpl;
import org.servidor.dao.modelo.FolderHibernate;
import org.servidor.dao.modelo.MessageHibernate;
import org.servidor.dao.modelo.datamapper.DataMapper;
import org.servidor.domain.errores.InvalidFieldsException;
import org.servidor.domain.servicios.ServiciosFolder;
import org.servidor.domain.servicios.common.ServicesConstants;
import org.utils.common.AplicationConstants;
import org.utils.domain.modelo.Firma;
import org.utils.domain.modelo.Folder;

import java.security.PublicKey;
import java.util.List;

public class ServiciosFolderImpl implements ServiciosFolder {

    private static final String MENSAJE_CORRUPTO = "Mensaje corrupto";
    private final FolderDaoImpl folderDao;

    private final DataMapper dataMapper;

    private final EncriptacionAsimetrica encriptacionAsimetrica;

    @Inject
    public ServiciosFolderImpl(FolderDaoImpl folderDao, DataMapper dataMapper, EncriptacionAsimetrica encriptacionAsimetrica) {
        this.folderDao = folderDao;
        this.dataMapper = dataMapper;
        this.encriptacionAsimetrica = encriptacionAsimetrica;
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
    public Folder getFolder(String userName, String folderName) {
        //Por la lógica de negocio, me tengo que traer el objeto del DAO
        FolderHibernate f = folderDao.getFolder(userName, folderName);
        f.getMessages().forEach(this::validateSignature);
        return dataMapper.folderHibToFolder(f);
    }


    private void validateSignature(MessageHibernate messageHibernate) {
        try {
            PublicKey pk = encriptacionAsimetrica.getPublicKeyFromCertificateEncoded(messageHibernate.getUser().getCertificate()).get();
            encriptacionAsimetrica.verificarfirma(pk, new Firma(messageHibernate.getMessage(), messageHibernate.getMessageFirmado()));
        } catch(Exception e){
            // Cambiamos el nombre para indicar que el mensaje está corrupto
            messageHibernate.getUser().setName(MENSAJE_CORRUPTO);
        }
    }

    @Override
    public List<Folder> getAll(String userName) {
        return folderDao.getAll(userName);
    }
}
