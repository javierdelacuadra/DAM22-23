package data.retrofit;

import data.retrofit.common.ConstantesAPI;
import io.reactivex.rxjava3.core.Single;
import modelo.Carpeta;
import retrofit2.http.*;

import java.util.List;

public interface CarpetasAPI {

    @GET(ConstantesAPI.CARPETAS_ID_PATH)
    Single<List<Carpeta>> getCarpetasByUsuario(@Path(ConstantesAPI.ID) String id);

    @POST(ConstantesAPI.CARPETAS_PATH)
    Single<Carpeta> addCarpeta(@Body Carpeta carpeta);

    @GET(ConstantesAPI.CARPETAS_PATH)
    Single<Carpeta> cargarCarpetaCompartida(@Query(ConstantesAPI.NOMBRE_CARPETA) String nombreCarpeta, @Query(ConstantesAPI.NOMBRE_USUARIO) String nombreUsuario, @Query(ConstantesAPI.PASSWORD_CARPETA) String passwordCarpeta);

    @PUT(ConstantesAPI.CARPETAS_PATH)
    Single<Carpeta> updateCarpeta(@Body Carpeta carpeta);
}
