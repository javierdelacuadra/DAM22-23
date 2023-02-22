package dao.impl;

import common.CachedCredentials;
import dao.MessagesDao;
import dao.retrofit.llamadas.MessagesApi;
import io.reactivex.rxjava3.core.Single;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import org.seguridad.impl.EncriptacionAES;
import org.utils.domain.modelo.Message;
import retrofit2.Response;

public class MessagesDaoImpl extends DaoGenericsImpl implements MessagesDao {

    public static final String NO_TIENES_ACCESO_A_ESTA_CARPETA = "No tienes acceso a esta carpeta";
    private final MessagesApi messagesApi;

    private final CachedCredentials ca;

    private final EncriptacionAES encriptarAES;

    @Inject
    public MessagesDaoImpl(MessagesApi messagesApi, CachedCredentials ca, EncriptacionAES encriptarAES) {
        this.messagesApi = messagesApi;
        this.ca = ca;
        this.encriptarAES = encriptarAES;
    }


    @Override
    public Single<Either<String, Message>> update(Message message) {
        if (ca.getFolderPass() != null){
            message.setMessageText(encriptarAES.encriptar(message.getMessageText(), ca.getFolderPass()));
            return safeSingleApicall(messagesApi.updateMessage(message));
        }
        return Single.just(Either.left(NO_TIENES_ACCESO_A_ESTA_CARPETA));
    }

    @Override
    public Single<Either<String, Response<Object>>> delete(int id) {
        return safeSingleResponseApicall(messagesApi.deleteMessage(id + ""));
    }

    @Override
    public Single<Either<String, Message>> add(Message message) {
        if (ca.getFolderPass() != null) {
            message.setMessageText(encriptarAES.encriptar(message.getMessageText(), ca.getFolderPass()));
            return safeSingleApicall(messagesApi.addMessage(message));
        }
        return Single.just(Either.left(NO_TIENES_ACCESO_A_ESTA_CARPETA));
    }
}
