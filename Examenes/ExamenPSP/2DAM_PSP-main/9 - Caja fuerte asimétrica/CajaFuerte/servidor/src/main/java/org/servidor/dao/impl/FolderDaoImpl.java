package org.servidor.dao.impl;

import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
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

    @Inject
    public FolderDaoImpl(JPAUtil jpaUtil, DataMapper dataMapper) {
        this.jpaUtil = jpaUtil;
        this.dataMapper = dataMapper;
    }

    @Override
    public FolderHibernate getFolder(String userName, String folderName) {
        try (EntityManager em = jpaUtil.getEntityManager()) {
            FolderHibernate folderHibernate = em.createNamedQuery(Queries.HQL_GET_FOLDER_BY_USER_AND_NAME, FolderHibernate.class)
                    .setParameter(AplicationConstants.USER_NAME, userName)
                    .setParameter(AplicationConstants.NAME_FOLDER, folderName)
                    .getSingleResult();
            if (folderHibernate.getOriginalFolderId() != null) {
                List<MessageHibernate> messages = em.createNamedQuery(Queries.HQL_GET_MESSAGES_BY_FOLDER_ID, MessageHibernate.class)
                        .setParameter(AplicationConstants.FOLDER_ID, folderHibernate.getOriginalFolderId())
                        .getResultList();
                folderHibernate.setMessages(messages);
                //Ponemos el id de la carpeta original para que los mensajes se guarden en la carpeta original
                folderHibernate.setId(folderHibernate.getOriginalFolderId());
            }
            folderHibernate.getMessages().size(); // para que se carguen los mensajes si es la carpeta del creador
            return folderHibernate;
            // si la contraseña no es correcta, se lanza una excepcion
        } catch (DataIntegrityException e) {
            throw e;
        } catch (Exception e) {
            // si no hay carpeta con ese nombre compartida o creada por el usuario, se lanza una excepcion
            throw new NotFoundException(DaoConstants.EL_USUARIO_PARENTHESIS + userName + DaoConstants.NO_TIENE_UNA_CARPETA + folderName + DaoConstants.O_LAS_CREDENCIALES_DE_LA_MISMA_NO_SON_VALIDAS);
        }
    }

    @Override
    public int add(Folder folder) {
        FolderHibernate folderHibernate = getFolderHibernate(folder);
        int rows = add(jpaUtil, folderHibernate, DaoConstants.EL_USUARIO + folder.getNameUserWithAccess() + DaoConstants.YA_TIENE_UNA_CARPETA_CON_ESE_NOMBRE);
        folder.setId(folderHibernate.getId());
        return rows;

    }

    private FolderHibernate getFolderHibernate(Folder folder) {
        FolderHibernate f = dataMapper.folderToFolderHibernate(folder);
        UserHibernate u;
        try (EntityManager em = jpaUtil.getEntityManager()) {
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
                .map(folderHibernate -> {
                            if (folderHibernate.getOriginalFolderId() != null)
                                //Ponemos el id de la carpeta original por estética
                                folderHibernate.setId(folderHibernate.getOriginalFolderId());
                            return dataMapper.folderHibToFolderBasic(folderHibernate);
                        }
                )
                .toList();
    }
}
