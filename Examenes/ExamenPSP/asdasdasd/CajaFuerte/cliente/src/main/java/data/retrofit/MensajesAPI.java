package data.retrofit;

import data.retrofit.common.ConstantesAPI;
import io.reactivex.rxjava3.core.Single;
import modelo.Mensaje;
import retrofit2.Response;
import retrofit2.http.*;

import java.util.List;

public interface MensajesAPI {

    @GET(ConstantesAPI.MENSAJES_ID_PATH)
    Single<List<Mensaje>> getMensajesByCarpeta(@Path(ConstantesAPI.ID) String id, @Query("password") String password);

    @POST(ConstantesAPI.MENSAJES_PATH)
    Single<Mensaje> addMensaje(@Body Mensaje mensaje);

    @PUT(ConstantesAPI.MENSAJES_PATH)
    Single<Mensaje> updateMensaje(@Body Mensaje mensaje);

    @DELETE(ConstantesAPI.MENSAJES_ID_PATH)
    Single<Response<Object>> deleteMensaje(@Path(ConstantesAPI.ID) String id);
}