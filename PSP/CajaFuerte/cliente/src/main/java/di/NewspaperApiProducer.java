package di;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import config.common.Constantes;
import data.network.AuthorizationInterceptor;
import data.retrofit.*;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import servicios.modelo.CacheAuthorization;

import java.net.CookieManager;
import java.net.CookiePolicy;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.TimeUnit;

public class NewspaperApiProducer {

    private final CacheAuthorization cache;

    @Inject
    public NewspaperApiProducer(CacheAuthorization cache) {
        this.cache = cache;
    }

    @Produces
    @Singleton
    public OkHttpClient getOkHttpClient() {
        CookieManager cookieManager = new CookieManager();
        cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);

        return new OkHttpClient.Builder()
                .readTimeout(Duration.of(10, ChronoUnit.MINUTES))
                .callTimeout(Duration.of(10, ChronoUnit.MINUTES))
                .connectTimeout(Duration.of(10, ChronoUnit.MINUTES))
                .addInterceptor(new AuthorizationInterceptor(cache))
                .connectionPool(new ConnectionPool(1, 1, TimeUnit.SECONDS))
                .cookieJar(new JavaNetCookieJar(cookieManager))
                .build();
    }

    @Produces
    @Singleton
    public Retrofit getRetrofit() {
        OkHttpClient httpClient = getOkHttpClient();

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                .create();


        return new Retrofit.Builder()
                .baseUrl(Constantes.BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .client(httpClient)
                .build();
    }

    @Produces
    public NewspapersApi getNewspapersApi(Retrofit retro) {
        return retro.create(NewspapersApi.class);
    }

    @Produces
    public LoginApi getLoginApi(Retrofit retro) {
        return retro.create(LoginApi.class);
    }

    @Produces
    public UsuariosAPI getReadersApi(Retrofit retro) {
        return retro.create(UsuariosAPI.class);
    }

    @Produces
    public MensajesAPI getMensajesApi(Retrofit retro) {
        return retro.create(MensajesAPI.class);
    }

    @Produces
    public CarpetasAPI getCarpetasApi(Retrofit retro) {
        return retro.create(CarpetasAPI.class);
    }
}