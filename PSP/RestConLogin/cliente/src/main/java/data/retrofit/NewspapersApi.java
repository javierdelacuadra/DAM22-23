package data.retrofit;

import io.reactivex.rxjava3.core.Single;
import model.Newspaper;
import model.Reader;
import model.ReaderLogin;
import retrofit2.Response;
import retrofit2.http.*;

import java.util.List;

public interface NewspapersApi {
    @GET("newspapers")
    Single<List<Newspaper>> getNewspapers();

    @POST("newspapers")
    Single<Newspaper> addNewspaper(@Body Newspaper newspaper);

    @PUT("newspapers")
    Single<Newspaper> updateNewspaper(@Body Newspaper newspaper);

    @DELETE("newspapers/{id}")
    Single<Response<Object>> deleteNewspaper(@Path("id") String id);

    @GET("readers")
    Single<List<Reader>> getReaders();

    @POST("readers")
    Single<Reader> addReader(@Body Reader reader);

    @PUT("readers")
    Single<Reader> updateReader(@Body Reader reader);

    @DELETE("readers/{id}")
    Single<Response<Object>> deleteReader(@Path("id") String id);

    @GET("login")
    Single<ReaderLogin> loginReader(@Query("username") String username, @Query("password") String password);

    @POST("login")
    Single<ReaderLogin> registerReader(@Body ReaderLogin reader);

    @GET("login/passwordRecovery")
    Single<String> recoverPassword(@Query("email") String email);

    @GET("login/emailResend")
    Single<String> sendEmail(@Query("email") String email);
}