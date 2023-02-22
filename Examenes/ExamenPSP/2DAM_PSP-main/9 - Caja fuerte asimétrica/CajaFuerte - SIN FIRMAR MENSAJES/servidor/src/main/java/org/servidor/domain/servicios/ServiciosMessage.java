package org.servidor.domain.servicios;

import org.utils.domain.modelo.Message;

public interface ServiciosMessage {

    Message add(Message message, String user);

    Message update(Message message, String user);

    boolean delete(String id, String user);
}
