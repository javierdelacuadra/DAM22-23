package data.retrofit;

import io.reactivex.rxjava3.core.Single;
import model.Mensaje;
import retrofit2.Response;
import retrofit2.http.*;

import java.util.List;

public interface MensajesAPI {

    @GET("mensajes/carpetas/{id}")
    Single<List<Mensaje>> getMensajesByCarpeta(@Path("id") String id);

    @POST("mensajes")
    Single<Mensaje> addMensaje(@Body Mensaje mensaje);

    @PUT("mensajes/{id}")
    Single<Mensaje> updateMensaje(@Path("id") String id, @Body Mensaje mensaje);

    @DELETE("mensajes/{id}")
    Single<Response<Object>> deleteMensaje(@Path("id") String id);
}