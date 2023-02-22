package dao.retrofit.produces;

import com.squareup.moshi.Moshi;
import config.Configuration;
import dao.retrofit.llamadas.LoginApi;
import dao.retrofit.llamadas.NewspapersApi;
import dao.retrofit.llamadas.ReadersApi;
import dao.retrofit.network.AuthorizationInterceptor;
import dao.retrofit.network.CacheAuthorization;
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
    public Retrofit retrofit(Moshi moshi, CacheAuthorization cache, Configuration config) {
        CookieManager cookieManager = new CookieManager();
        cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);

        OkHttpClient clientOK = new OkHttpClient.Builder()
                .addInterceptor(new AuthorizationInterceptor(cache))
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
    public NewspapersApi getNewsApi(Retrofit retrofit) {
        return retrofit.create(NewspapersApi.class);
    }

    @Produces
    public LoginApi getLoginApi(Retrofit retrofit) {return retrofit.create(LoginApi.class);}

    @Produces
    public ReadersApi getReadersApi(Retrofit retrofit) {return retrofit.create(ReadersApi.class);}

}
