package org.servidor.dao.impl;

import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import org.servidor.dao.MessageDao;
import org.servidor.dao.common.DaoConstants;
import org.servidor.dao.impl.hibernate.JPAUtil;
import org.servidor.dao.modelo.FolderHibernate;
import org.servidor.dao.modelo.MessageHibernate;
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
        validateUser(messageHibernate, user);
        int code = add(jpaUtil, messageHibernate);
        FolderHibernate f = messageHibernate.getFolder();
        List<MessageHibernate> messages = f.getMessages();
        messages.add(messageHibernate);
        f.setMessages(messages);
        update(jpaUtil, f);
        message.setId(messageHibernate.getId());
        return code;
    }

    private void validateUser(MessageHibernate messageHibernate, String user) {
        if (messageHibernate.getFolder().isReadOnly() && !messageHibernate.getFolder().getUser().getName().equals(user)) {
            throw new DataIntegrityException(DaoConstants.LA_CARPETA_ES_DE_SOLO_LECTURA);
        }
    }

    private MessageHibernate getMessageHibernate(Message message) {
        MessageHibernate m = dataMapper.mensajeToMensajeHibernate(message);
        FolderHibernate f;
        try(EntityManager em = jpaUtil.getEntityManager()) {
            f = em.find(FolderHibernate.class, message.getFolder());
        }
        m.setFolder(f);
        return m;
    }

    @Override
    public int update(Message message, String user) {
        MessageHibernate m = getMessageHibernate(message);
        validateUser(m, user);
        return update(jpaUtil, m);
    }

    @Override
    public int delete(int id, String user) {
        MessageHibernate m;
        try (EntityManager em = jpaUtil.getEntityManager()) {
            m = em.find(MessageHibernate.class, id);
            validateUser(m, user);
        }
        int code = delete(jpaUtil, m);
        FolderHibernate f = m.getFolder();
        List<MessageHibernate> messages = f.getMessages();
        messages.remove(m);
        f.setMessages(messages);
        update(jpaUtil, f);
        return code;
    }

}
