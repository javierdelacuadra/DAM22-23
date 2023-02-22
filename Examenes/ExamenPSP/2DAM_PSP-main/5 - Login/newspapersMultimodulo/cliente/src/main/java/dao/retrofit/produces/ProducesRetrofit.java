package dao.retrofit.produces;

import com.squareup.moshi.Moshi;
import config.Configuration;
import dao.retrofit.llamadas.NewsApi;
import dao.retrofit.network.JavaNetCookieJar;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Singleton;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.moshi.MoshiConverterFactory;

import java.net.CookieManager;
import java.net.CookiePolicy;

public class ProducesRetrofit {

    @Produces
    @Singleton
    public Moshi getMoshi() {
        return new Moshi.Builder().add(new LocalDateMoshiAdapter()).add(new LocalDateTimeMoshiAdapter()).build();
    }

    @Produces
    @Singleton
    public Retrofit retrofit(Moshi moshi, Configuration config) {
        CookieManager cookieManager = new CookieManager();
        cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);

        OkHttpClient clientOK = new OkHttpClient.Builder()
                .connectionPool(new okhttp3.ConnectionPool(1, 10, java.util.concurrent.TimeUnit.SECONDS))
                .cookieJar(new JavaNetCookieJar(cookieManager))
                .build();


        return new Retrofit.Builder()
                .baseUrl(config.getApiURL())
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .client(clientOK)
                .build();
    }

    @Produces
    public NewsApi getNewsApi(Retrofit retrofit) {
        return retrofit.create(NewsApi.class);
    }

}
