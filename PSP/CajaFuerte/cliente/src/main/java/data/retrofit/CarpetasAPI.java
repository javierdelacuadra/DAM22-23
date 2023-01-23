package data.retrofit;

import io.reactivex.rxjava3.core.Single;
import modelo.Carpeta;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

import java.util.List;

public interface CarpetasAPI {

    @GET("carpetas/{id}")
    Single<List<Carpeta>> getCarpetasByUsuario(@Path("id") String id);

    @POST("carpetas")
    Single<Carpeta> addCarpeta(@Body Carpeta carpeta);
}
