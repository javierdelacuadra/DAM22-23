package di;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dao.retrofit.JugadoresApi;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Singleton;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import java.time.LocalDate;

public class ApiProducer {

    @Produces
    @Singleton
    public Retrofit getRetrofit() {
        OkHttpClient httpClient = new OkHttpClient.Builder()
                .connectionPool(new okhttp3.ConnectionPool(1, 5, java.util.concurrent.TimeUnit.SECONDS))
                .build();

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .create();


        return new Retrofit.Builder()
                .baseUrl("http://localhost:8080/server-1.0-SNAPSHOT/api/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .client(httpClient)
                .build();
    }

    @Produces
    public JugadoresApi getEquiposApi(Retrofit retro) {
        return retro.create(JugadoresApi.class);
    }
}
