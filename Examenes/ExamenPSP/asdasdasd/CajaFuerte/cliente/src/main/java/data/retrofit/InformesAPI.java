package data.retrofit;

import io.reactivex.rxjava3.core.Single;
import modelo.Informe;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

import java.util.List;

public interface InformesAPI {
    @GET("informes")
    Single<List<Informe>> getInformes();

    @POST("informes")
    Single<Informe> addInforme(@Body Informe informe);

    @GET("informes/{nombre}")
    Single<Informe> getInforme(@Path("nombre") String nombre);
}
