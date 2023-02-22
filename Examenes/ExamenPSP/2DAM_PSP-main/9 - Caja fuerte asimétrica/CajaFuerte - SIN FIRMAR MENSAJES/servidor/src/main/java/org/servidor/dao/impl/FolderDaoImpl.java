package org.servidor.dao.impl;

import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import org.seguridad.EncriptacionAsimetrica;
import org.seguridad.impl.EncriptacionAES;
import org.servidor.dao.FolderDao;
import org.servidor.dao.common.DaoConstants;
import org.servidor.dao.common.Queries;
import org.servidor.dao.impl.hibernate.JPAUtil;
import org.servidor.dao.modelo.FolderHibernate;
import org.servidor.dao.modelo.MessageHibernate;
import org.servidor.dao.modelo.UserHibernate;
import org.servidor.dao.modelo.datamapper.DataMapper;
import org.servidor.domain.errores.DataIntegrityException;
import org.servidor.domain.errores.NotFoundException;
import org.utils.common.AplicationConstants;
import org.utils.domain.modelo.Folder;

import java.util.List;

public class FolderDaoImpl extends DaoGenericsImpl implements FolderDao {


    private final JPAUtil jpaUtil;

    private final DataMapper dataMapper;

    private final EncriptacionAES encriptacionAES;

    private final EncriptacionAsimetrica encriptacionAsimetrica;
    @Inject
    public FolderDaoImpl(JPAUtil jpaUtil, DataMapper dataMapper, EncriptacionAES encriptacionAES, EncriptacionAsimetrica encriptacionAsimetrica) {
        this.jpaUtil = jpaUtil;
        this.dataMapper = dataMapper;
        this.encriptacionAES = encriptacionAES;
        this.encriptacionAsimetrica = encriptacionAsimetrica;
    }

    @Override
    public Folder getFolder(String userName, String folderName) {
        try (EntityManager em = jpaUtil.getEntityManager()) {
            FolderHibernate folderHibernate = em.createNamedQuery(Queries.HQL_GET_FOLDER_BY_USER_AND_NAME, FolderHibernate.class)
                    .setParameter(AplicationConstants.USER_NAME, userName)
                    .setParameter(AplicationConstants.NAME_FOLDER, folderName)
                    .getSingleResult();
                // si no hay carpeta con ese nombre, se lanza una excepcion
                if (folderHibernate.getOriginalFolderId() != null) {
                    List<MessageHibernate> messages = em.createNamedQuery(Queries.HQL_GET_MESSAGES_BY_FOLDER_ID, MessageHibernate.class)
                            .setParameter(AplicationConstants.FOLDER_ID, folderHibernate.getOriginalFolderId())
                            .getResultList();
//                    //TODO: VALIDAR FIRMA
//                    try {
//                    for (MessageHibernate messageHibernate : messages
//                    ) {
//
//                            PublicKey pk = encriptacionAsimetrica.getPublicKeyFromCertificateEncoded(messageHibernate.getOwner().getCertificado());
//                            encriptacionAsimetrica.verificarfirma(pk, new Firma(messageHibernate.getMessage(), messageHibernate.getMessageFirmado()));
//                    }
//                    } catch (Exception e) {
//                        //TODO: ELIMINAR MENSAJE
//                        throw new DataIntegrityException("LOS DUEÑOS DE LOS MENSAJES NO SE HAN PODIDO VERIFICAR!!!!!!");
//                    }

                    folderHibernate.setMessages(messages);
                    //Ponemos el id de la carpeta original para que los mensajes se guarden en la carpeta original
                    folderHibernate.setId(folderHibernate.getOriginalFolderId());
                }

                    folderHibernate.getMessages().size(); // para que se carguen los mensajes si es la carpeta del creador
                return dataMapper.folderHibToFolder(folderHibernate);
            // si la contraseña no es correcta, se lanza una excepcion
        } catch (DataIntegrityException e){
            throw e;
        }
        catch (Exception e) {
            throw new NotFoundException(DaoConstants.EL_USUARIO + userName + DaoConstants.NO_TIENE_UNA_CARPETA + folderName + DaoConstants.O_LAS_CREDENCIALES_DE_LA_MISMA_NO_SON_VALIDAS);
        }
    }


    @Override
    public Folder changePassword(String userName, String folderName, String passwordOld, String passwordNew){
        Folder valid = getFolder(userName, folderName);
        if (valid != null) {
            FolderHibernate validFolder = getFolderHibernate(valid);
            EntityManager em = jpaUtil.getEntityManager();
            EntityTransaction tx = em.getTransaction();
            tx.begin();
            try {
                validFolder.getMessages().forEach(m -> {m.setMessage(encriptacionAES.encriptar(encriptacionAES.desencriptar(m.getMessage(), passwordOld), passwordNew));
                    m.setFolder(validFolder);
                    update(jpaUtil, m);});
                em.merge(validFolder);
                tx.commit();
                return dataMapper.folderHibToFolder(validFolder);
            } catch (Exception e) {
                if (tx.isActive()) tx.rollback();
                throw new NotFoundException(DaoConstants.EL_USUARIO + userName + DaoConstants.NO_TIENE_UNA_CARPETA + folderName + DaoConstants.O_LAS_CREDENCIALES_DE_LA_MISMA_NO_SON_VALIDAS);
            }
        } else {
            throw new NotFoundException(DaoConstants.EL_USUARIO + userName + DaoConstants.NO_TIENE_UNA_CARPETA + folderName + DaoConstants.O_LAS_CREDENCIALES_DE_LA_MISMA_NO_SON_VALIDAS);
        }
    }
    @Override
    public int add(Folder folder) {
        FolderHibernate folderHibernate = getFolderHibernate(folder);
        int rows = add(jpaUtil, folderHibernate);
        folder.setId(folderHibernate.getId());
        return rows;

    }

    private FolderHibernate getFolderHibernate(Folder folder) {
        FolderHibernate f = dataMapper.folderToFolderHibernate(folder);
        UserHibernate u;
        try(EntityManager em = jpaUtil.getEntityManager()) {
            u = em.find(UserHibernate.class, folder.getNameUserWithAccess());
        }
        f.setUser(u);
        return f;
    }


    @Override
    public List<Folder> getAll(String userName) {
        EntityManager em = jpaUtil.getEntityManager();
        return em.createNamedQuery(Queries.HQL_GET_ALL_FOLDERS, FolderHibernate.class)
                .setParameter(AplicationConstants.USER_NAME, userName)
                .getResultList()
                .stream()
                .map(dataMapper::folderHibToFolderBasic)
                .toList();
    }
}
