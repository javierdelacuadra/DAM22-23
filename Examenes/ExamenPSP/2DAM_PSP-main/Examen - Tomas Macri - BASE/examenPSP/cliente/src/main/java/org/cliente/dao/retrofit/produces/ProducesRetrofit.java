package org.cliente.dao.retrofit.produces;

import com.squareup.moshi.Moshi;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Singleton;
import okhttp3.OkHttpClient;
import org.cliente.config.common.Configuracion;
import org.cliente.dao.retrofit.llamadas.MyApi;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.moshi.MoshiConverterFactory;

public class ProducesRetrofit {

    @Produces
    @Singleton
    public Moshi getMoshi() {
        return new Moshi.Builder().add(new LocalDateMoshiAdapter()).add(new LocalDateTimeMoshiAdapter()).build();
    }

    @Produces
    @Singleton
    public Retrofit retrofit(Moshi moshi, Configuracion config) {

        OkHttpClient clientOK = new OkHttpClient.Builder()
                .connectionPool(new okhttp3.ConnectionPool(1, 10, java.util.concurrent.TimeUnit.SECONDS))
                .build();


        return new Retrofit.Builder()
                .baseUrl(config.getUrlApi())
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .client(clientOK)
                .build();
    }

    @Produces
    public MyApi getNewsApi(Retrofit retrofit) {
        return retrofit.create(MyApi.class);
    }

}
