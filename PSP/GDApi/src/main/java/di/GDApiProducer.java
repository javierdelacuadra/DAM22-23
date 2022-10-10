package di;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import data.common.Constantes;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Singleton;
import okhttp3.OkHttpClient;
import retrofit.GDApi;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GDApiProducer {

    @Produces
    @Singleton
    public Retrofit getRetrofit() {
        OkHttpClient httpClient = new OkHttpClient.Builder()
                .connectionPool(new okhttp3.ConnectionPool(1, 5, java.util.concurrent.TimeUnit.SECONDS))
                .build();

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();


        return new Retrofit.Builder()
                .baseUrl(Constantes.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(httpClient)
                .build();
    }

    @Produces
    public GDApi getGDApi(Retrofit retro) {
        return retro.create(GDApi.class);
    }

}