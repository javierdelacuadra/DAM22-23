package dao.retrofit.llamadas;

import io.reactivex.rxjava3.core.Single;
import org.utils.common.AplicationConstants;
import org.utils.domain.modelo.Message;
import retrofit2.Response;
import retrofit2.http.*;

public interface MessagesApi {

    String BASE_PATH = "messages";

    @DELETE(BASE_PATH + AplicationConstants.PATH_PARAM_ID)
    Single<Response<Object>> deleteMessage(@Path(AplicationConstants.ID) String id);

    @POST(BASE_PATH)
    Single<Message> addMessage(@Body Message message);

    @PUT(BASE_PATH)
    Single<Message> updateMessage(@Body Message message);

}
