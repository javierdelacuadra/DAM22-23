package data.retrofit;

import data.retrofit.common.ConstantesAPI;
import io.reactivex.rxjava3.core.Single;
import model.Newspaper;
import model.Reader;
import model.ReaderLogin;
import retrofit2.Response;
import retrofit2.http.*;

import java.util.List;

public interface NewspapersApi {

    @GET(ConstantesAPI.NEWSPAPERS)
    Single<List<Newspaper>> getNewspapers();

    @POST(ConstantesAPI.NEWSPAPERS)
    Single<Newspaper> addNewspaper(@Body Newspaper newspaper);

    @PUT(ConstantesAPI.NEWSPAPERS)
    Single<Newspaper> updateNewspaper(@Body Newspaper newspaper);

    @DELETE(ConstantesAPI.NEWSPAPERS_ID)
    Single<Response<Object>> deleteNewspaper(@Path(ConstantesAPI.ID) String id);

    @GET(ConstantesAPI.READERS)
    Single<List<Reader>> getReaders();

    @POST(ConstantesAPI.READERS)
    Single<Reader> addReader(@Body Reader reader);

    @PUT(ConstantesAPI.READERS)
    Single<Reader> updateReader(@Body Reader reader);

    @DELETE(ConstantesAPI.READERS_ID)
    Single<Response<Object>> deleteReader(@Path(ConstantesAPI.ID) String id);

    @GET(ConstantesAPI.LOGIN)
    Single<ReaderLogin> loginReader(@Header(ConstantesAPI.AUTHORIZATION) String authorization);

    @POST(ConstantesAPI.LOGIN)
    Single<ReaderLogin> registerReader(@Body ReaderLogin reader);

    @GET(ConstantesAPI.LOGIN_PASSWORD_RECOVERY)
    Single<Response<String>> recoverPassword(@Query(ConstantesAPI.EMAIL) String email);

    @GET(ConstantesAPI.LOGIN_EMAIL_RESEND)
    Single<Response<String>> sendEmail(@Query(ConstantesAPI.EMAIL) String email);

    @GET(ConstantesAPI.LOGIN_LOGOUT)
    Single<Response<String>> logout();
}