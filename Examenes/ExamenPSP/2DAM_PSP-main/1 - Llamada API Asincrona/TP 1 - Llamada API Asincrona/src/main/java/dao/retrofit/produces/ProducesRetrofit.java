package dao.retrofit.produces;

import com.squareup.moshi.Moshi;
import config.Configuracion;
import dao.common.ConstantesDAO;
import dao.retrofit.llamadas.WorldApi;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Singleton;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.moshi.MoshiConverterFactory;

public class ProducesRetrofit {

    @Produces
    @Singleton
    public Moshi getMoshi() {
        return new Moshi.Builder().build();
    }

    @Produces
    @Singleton
    public Retrofit retrofit(Moshi moshi, Configuracion config) {

        OkHttpClient clientOK = new OkHttpClient.Builder()
                .connectionPool(new okhttp3.ConnectionPool(1, 1, java.util.concurrent.TimeUnit.SECONDS))
                .addInterceptor(chain -> {
                            Request original = chain.request();
                            Request finalRequest = original.newBuilder().header(ConstantesDAO.X_CSCAPI_KEY, config.getApiKey()).build();
                            return chain.proceed(finalRequest);
                        }
                )
                .build();


        return new Retrofit.Builder()
                .baseUrl(config.getUrlApi())
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .client(clientOK)
                .build();
    }

    @Produces
    public WorldApi getWorldApi(Retrofit retrofit) {
        return retrofit.create(WorldApi.class);
    }

}
