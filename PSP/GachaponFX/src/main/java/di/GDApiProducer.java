package di;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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
                .connectionPool(new okhttp3.ConnectionPool(1, 2, java.util.concurrent.TimeUnit.SECONDS))
                .build();

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();


        return new Retrofit.Builder()
                .baseUrl("https://gdbrowser.com/api/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(httpClient)
                .build();
    }

    @Produces
    public GDApi getGDApi(Retrofit retro) {
        return retro.create(GDApi.class);
    }

}