package domain.serivces.impl;

import common.CachedCredentials;
import dao.MessagesDao;
import domain.serivces.MessagesServices;
import io.reactivex.rxjava3.core.Single;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import org.seguridad.impl.EncriptacionAES;
import org.utils.domain.modelo.Message;
import retrofit2.Response;

import java.util.List;

public class MessageServicesImpl implements MessagesServices {

    private final MessagesDao messagesDao;

    private final EncriptacionAES encriptacionAES;

    private final CachedCredentials ca;


    @Inject
    public MessageServicesImpl(MessagesDao messagesDao, EncriptacionAES encriptacionAES, CachedCredentials ca) {
        this.messagesDao = messagesDao;
        this.encriptacionAES = encriptacionAES;
        this.ca = ca;
    }

    @Override
    public Single<Either<String, Message>> update(Message message) {
        return messagesDao.update(message);
    }

    @Override
    public Single<Either<String, Response<Object>>> delete(int id) {
        return messagesDao.delete(id);
    }

    @Override
    public Single<Either<String, Message>> add(Message message) {
        return messagesDao.add(message);
    }

    @Override
    public List<Message> decrypt(List<Message> messages) {
        messages.forEach(message -> message.setMessageText(encriptacionAES.desencriptar(message.getMessageText(), ca.getFolderPass())));
        return messages;
    }
}

