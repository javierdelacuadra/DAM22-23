package data.retrofit;

import io.reactivex.rxjava3.core.Single;
import model.Newspaper;
import model.Reader;
import retrofit2.http.*;

import java.util.List;

public interface NewspapersApi {
    @GET("newspapers")
    Single<List<Newspaper>> getNewspapers();

    @GET("newspapers/{id}")
    Single<Newspaper> getNewspaper(@Path("id") String id);

    @POST("newspapers")
    Single<Newspaper> addNewspaper(@Body Newspaper newspaper);

    @PUT("newspapers")
    Single<Newspaper> updateNewspaper(@Body Newspaper newspaper);

    @DELETE("newspapers/{id}")
    Single<Newspaper> deleteNewspaper(@Path("id") String id);

    @GET("readers")
    Single<List<Reader>> getReaders();

    @GET("readers/{id}")
    Single<Reader> getReader(@Path("id") String id);

    @POST("readers")
    Single<Reader> addReader(@Body Reader reader);

    @PUT("readers")
    Single<Reader> updateReader(@Body Reader reader);

    @DELETE("readers/{id}")
    Single<Reader> deleteReader(@Path("id") String id);
}