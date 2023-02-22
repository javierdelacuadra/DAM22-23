package dao.impl;

import common.CachedCredentials;
import dao.MessagesDao;
import dao.common.APIConstants;
import dao.retrofit.llamadas.MessagesApi;
import io.reactivex.rxjava3.core.Single;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import org.utils.domain.modelo.Message;
import retrofit2.Response;

public class MessagesDaoImpl extends DaoGenericsImpl implements MessagesDao {

    private final MessagesApi messagesApi;

    private final CachedCredentials ca;

    @Inject
    public MessagesDaoImpl(MessagesApi messagesApi, CachedCredentials ca) {
        this.messagesApi = messagesApi;
        this.ca = ca;
    }


    @Override
    public Single<Either<String, Message>> update(Message message) {
        if (ca.getFolderPass() != null){
            return safeSingleApicall(messagesApi.updateMessage(message));
        }
        return Single.just(Either.left(APIConstants.NO_TIENES_ACCESO_A_ESTA_CARPETA));
    }

    @Override
    public Single<Either<String, Response<Object>>> delete(int id) {
        return safeSingleResponseApicall(messagesApi.deleteMessage(id + ""));
    }

    @Override
    public Single<Either<String, Message>> add(Message message) {
        if (ca.getFolderPass() != null) {
            return safeSingleApicall(messagesApi.addMessage(message));
        }
        return Single.just(Either.left(APIConstants.NO_TIENES_ACCESO_A_ESTA_CARPETA));
    }
}
