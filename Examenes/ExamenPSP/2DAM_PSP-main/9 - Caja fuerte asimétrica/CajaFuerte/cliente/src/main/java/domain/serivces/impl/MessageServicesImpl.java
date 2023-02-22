package domain.serivces.impl;

import common.CachedCredentials;
import dao.MessagesDao;
import domain.serivces.MessagesServices;
import domain.serivces.common.ServicesConstants;
import io.reactivex.rxjava3.core.Single;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import org.seguridad.EncriptacionAsimetrica;
import org.seguridad.impl.EncriptacionAES;
import org.utils.domain.modelo.Message;
import retrofit2.Response;

import java.util.List;

public class MessageServicesImpl implements MessagesServices {

    private final MessagesDao messagesDao;

    private final EncriptacionAES encriptacionAES;

    private final EncriptacionAsimetrica encriptacionAsimetrica;

    private final CachedCredentials ca;

    @Inject
    public MessageServicesImpl(MessagesDao messagesDao, EncriptacionAES encriptacionAES, EncriptacionAsimetrica encriptacionAsimetrica, CachedCredentials ca) {
        this.messagesDao = messagesDao;
        this.encriptacionAES = encriptacionAES;
        this.encriptacionAsimetrica = encriptacionAsimetrica;
        this.ca = ca;
    }

    @Override
    public Single<Either<String, Message>> update(Message message) {
        message.setMessageText(encriptacionAES.encriptar(message.getMessageText(), ca.getFolderPass()));
        return messagesDao.update(message);
    }

    @Override
    public Single<Either<String, Response<Object>>> delete(int id) {
        return messagesDao.delete(id);
    }

    @Override
    public Single<Either<String, Message>> add(Message message) {
        try {
            message.setMessageText(encriptacionAES.encriptar(message.getMessageText(), ca.getFolderPass()));
            message.setMessageTextSigned(encriptacionAsimetrica.firmarConPrivateKey(ca.getPrivateKeyUserLogged(), message.getMessageText()).getTextoFirmado());
            return messagesDao.add(message);
        } catch (Exception e) {
            return Single.just(Either.left(ServicesConstants.ERROR_AL_VALIDAR_TU_IDENTIDAD_POR_FAVOR_VUELVE_A_INICIAR_SESION));
        }
    }

    @Override
    public List<Message> decrypt(List<Message> messages) {
        messages.forEach(message -> message.setMessageText(encriptacionAES.desencriptar(message.getMessageText(), ca.getFolderPass())));
        return messages;
    }
}

