package org.servidor.dao.impl;

import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import org.servidor.dao.MessageDao;
import org.servidor.dao.common.DaoConstants;
import org.servidor.dao.impl.hibernate.JPAUtil;
import org.servidor.dao.modelo.FolderHibernate;
import org.servidor.dao.modelo.MessageHibernate;
import org.servidor.dao.modelo.UserHibernate;
import org.servidor.dao.modelo.datamapper.DataMapper;
import org.servidor.domain.errores.DataIntegrityException;
import org.utils.domain.modelo.Message;

import java.util.List;

public class MessageDaoImpl extends DaoGenericsImpl implements MessageDao {

    private final JPAUtil jpaUtil;

    private final DataMapper dataMapper;

    @Inject
    public MessageDaoImpl(JPAUtil jpaUtil, DataMapper dataMapper) {
        this.jpaUtil = jpaUtil;
        this.dataMapper = dataMapper;
    }

    @Override
    public int add(Message message, String user) {
        MessageHibernate messageHibernate = getMessageHibernate(message);
        if (validateUser(messageHibernate)) {
            int code = add(jpaUtil, messageHibernate, DaoConstants.MENSAJE_DUPLICADO);
            FolderHibernate f = messageHibernate.getFolder();
            List<MessageHibernate> messages = f.getMessages();
            messages.add(messageHibernate);
            f.setMessages(messages);
            update(jpaUtil, f);
            message.setId(messageHibernate.getId());
            return code;
        } else {
            throw new DataIntegrityException(DaoConstants.LA_CARPETA_ES_DE_SOLO_LECTURA);
        }
    }

    private boolean validateUser(MessageHibernate messageHibernate) {
        return !messageHibernate.getFolder().isReadOnly() || messageHibernate.getFolder().getUser().getName().equals(messageHibernate.getUser().getName());
    }

    private MessageHibernate getMessageHibernate(Message message) {
        MessageHibernate m = dataMapper.mensajeToMensajeHibernate(message);
        FolderHibernate f;
        UserHibernate u;
        try(EntityManager em = jpaUtil.getEntityManager()) {
            f = em.find(FolderHibernate.class, message.getFolder());
            u = em.find(UserHibernate.class, message.getNameCreator());
        }
        m.setFolder(f);
        m.setUser(u);
        return m;
    }

    private boolean validateUser(MessageHibernate messageHibernate, String userName) {
        return !messageHibernate.getFolder().isReadOnly() || messageHibernate.getFolder().getUser().getName().equals(userName);
    }

    @Override
    public int update(Message message) {
        MessageHibernate m = getMessageHibernate(message);
        if (validateUser(m)) {
            return update(jpaUtil, m);
        } else {
            throw new DataIntegrityException(DaoConstants.LA_CARPETA_ES_DE_SOLO_LECTURA);
        }
    }

    @Override
    public int delete(int id, String user) {
        MessageHibernate m;
        try (EntityManager em = jpaUtil.getEntityManager()) {
            m = em.find(MessageHibernate.class, id);
            // El usuario de hibernate es el que firmó el mensaje por última vez, para no buscar al
            // usuario que quiere eliminarlo, se compara con su nombre directamente
            if (validateUser(m, user)) {
                int code = delete(jpaUtil, m);
                FolderHibernate f = m.getFolder();
                List<MessageHibernate> messages = f.getMessages();
                messages.remove(m);
                f.setMessages(messages);
                update(jpaUtil, f);
                return code;
            } else {
                throw new DataIntegrityException(DaoConstants.LA_CARPETA_ES_DE_SOLO_LECTURA);
            }
        } catch (DataIntegrityException e){
            throw e;
        } catch (Exception e) {
            throw new DataIntegrityException(DaoConstants.LA_CARPETA_ES_DE_SOLO_LECTURA);
        }
    }

}
