package di;

import com.google.gson.*;
import com.squareup.moshi.JsonReader;
import com.squareup.moshi.Moshi;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Singleton;
import jakarta.json.Json;
import okhttp3.OkHttpClient;
import retrofit.GDApi;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.moshi.MoshiConverterFactory;

import java.time.LocalDateTime;

public class GDApiProducer {

    @Produces
    @Singleton
    public Retrofit getRetrofit() {
        OkHttpClient asdasdsasds = new OkHttpClient.Builder()
                .connectionPool(new okhttp3.ConnectionPool(1, 2, java.util.concurrent.TimeUnit.SECONDS))
                .build();

        Gson gson = new GsonBuilder().setLenient().create();

        return new Retrofit.Builder()
                .baseUrl("https://gdbrowser.com/api/search/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(asdasdsasds)
                .build();
    }

    @Produces
    public GDApi getGDApi(Retrofit retro) {
        return retro.create(GDApi.class);
    }
}