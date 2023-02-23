package data.retrofit;

import io.reactivex.rxjava3.core.Single;
import modelo.Raton;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

import java.util.List;

public interface RatonesAPI {
    @GET("ratones")
    Single<List<Raton>> getRatones();

    @POST("ratones")
    Single<Raton> addRaton(@Body Raton raton);
}
