package di;

import com.google.gson.*;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Singleton;
import okhttp3.OkHttpClient;
import retrofit.GDApi;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.time.LocalDateTime;

public class GDApiProducer {

    private final OkHttpClient asdasdsasds = new OkHttpClient.Builder()
            .connectionPool(new okhttp3.ConnectionPool(1, 2, java.util.concurrent.TimeUnit.SECONDS))
            .build();

    private final Gson gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class,
            (JsonDeserializer<LocalDateTime>) (json, type, jsonDeserializationContext) -> LocalDateTime.parse(json.getAsJsonPrimitive().getAsString())).registerTypeAdapter(LocalDateTime.class,
            (JsonSerializer<LocalDateTime>) (localDateTime, type, jsonSerializationContext) -> new JsonPrimitive(localDateTime.toString())
    ).create();

    private final Retrofit retro = new Retrofit.Builder()
            .baseUrl("https://gdbrowser.com/api/search/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(asdasdsasds)
            .build();

    @Produces
    @Singleton
    public GDApi getGDApi() {
        return retro.create(GDApi.class);
    }
}