package di;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import common.Constantes;
import data.retrofit.NewspapersApi;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Singleton;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class NewspaperApiProducer {

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
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .client(httpClient)
                .build();
    }

    @Produces
    public NewspapersApi getnewspapersApi(Retrofit retro) {
        return retro.create(NewspapersApi.class);
    }
}
