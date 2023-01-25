package data.retrofit;

import io.reactivex.rxjava3.core.Single;
import modelo.Carpeta;
import retrofit2.http.*;

import java.util.List;

public interface CarpetasAPI {

    @GET("carpetas/{id}")
    Single<List<Carpeta>> getCarpetasByUsuario(@Path("id") String id);

    @POST("carpetas")
    Single<Carpeta> addCarpeta(@Body Carpeta carpeta);

    @GET("carpetas")
    Single<Carpeta> cargarCarpetaCompartida(@Query("nombreCarpeta") String nombreCarpeta, @Query("nombreUsuario") String nombreUsuario, @Query("passwordCarpeta") String passwordCarpeta);

    @PUT("carpetas")
    Single<Carpeta> updateCarpeta(@Body Carpeta carpeta);
}
