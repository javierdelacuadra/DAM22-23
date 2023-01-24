package data.retrofit;

import io.reactivex.rxjava3.core.Single;
import modelo.Mensaje;
import retrofit2.Response;
import retrofit2.http.*;

import java.util.List;

public interface MensajesAPI {

    @GET("mensajes/{id}")
    Single<List<Mensaje>> getMensajesByCarpeta(@Path("id") String id, @Query("password") String password);

    @POST("mensajes")
    Single<Mensaje> addMensaje(@Body Mensaje mensaje);

    @PUT("mensajes")
    Single<Mensaje> updateMensaje(@Body Mensaje mensaje);

    @DELETE("mensajes/{id}")
    Single<Response<Object>> deleteMensaje(@Path("id") String id);
}