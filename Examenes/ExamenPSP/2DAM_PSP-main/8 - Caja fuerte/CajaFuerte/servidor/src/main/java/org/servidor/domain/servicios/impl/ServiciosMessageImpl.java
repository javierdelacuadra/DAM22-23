package org.servidor.domain.servicios.impl;

import jakarta.inject.Inject;
import org.servidor.dao.impl.MessageDaoImpl;
import org.servidor.domain.errores.InvalidFieldsException;
import org.servidor.domain.servicios.ServiciosMessage;
import org.servidor.domain.servicios.common.ServicesConstants;
import org.utils.common.AplicationConstants;
import org.utils.domain.modelo.Message;

public class ServiciosMessageImpl implements ServiciosMessage {

    private final MessageDaoImpl messageDao;

        @Inject
        public ServiciosMessageImpl(MessageDaoImpl messageDao) {
            this.messageDao = messageDao;
        }

        @Override public Message add(Message message, String user) {
            if (!message.getMessageText().isEmpty()){
                if (messageDao.add(message, user) > 0){
                    return message;
                }
                throw new InvalidFieldsException(ServicesConstants.NO_SE_PUDO_AGREGAR_EL_MENSAJE);
            }
            throw new InvalidFieldsException(AplicationConstants.COMPLETA_TODOS_LOS_CAMPOS);
        }

        @Override public Message update(Message message, String user) {
            if (!message.getMessageText().isEmpty()){
                if (messageDao.update(message, user) > 0){
                    return message;
                }
                throw new InvalidFieldsException(ServicesConstants.NO_SE_PUDO_ACTUALIZAR_EL_MENSAJE);
            }
            throw new InvalidFieldsException(AplicationConstants.COMPLETA_TODOS_LOS_CAMPOS);
        }

        @Override public boolean delete(String id, String user) {
            try {

                return messageDao.delete(Integer.parseInt(id), user) > 0;
            } catch (NumberFormatException e) {
                throw new InvalidFieldsException(ServicesConstants.EL_ID_DEBE_SER_UN_NUMERO);
            }

        }

}
