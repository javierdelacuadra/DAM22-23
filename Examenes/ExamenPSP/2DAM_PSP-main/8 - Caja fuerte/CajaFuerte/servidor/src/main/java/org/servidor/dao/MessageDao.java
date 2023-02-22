package org.servidor.dao;

import org.utils.domain.modelo.Message;

public interface MessageDao {
    int add(Message message, String user);

    int update(Message message, String user);

    int delete(int id, String user);
}
