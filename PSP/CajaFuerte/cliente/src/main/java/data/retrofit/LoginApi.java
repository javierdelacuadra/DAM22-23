package data.retrofit;

import data.retrofit.common.ConstantesAPI;
import io.reactivex.rxjava3.core.Single;
import modelo.ReaderLogin;
import modelo.Usuario;
import retrofit2.Response;
import retrofit2.http.*;

public interface LoginApi {
    @GET(ConstantesAPI.LOGIN)
    Single<Usuario> loginReader(@Header(ConstantesAPI.AUTHORIZATION) String authorization);

    @POST(ConstantesAPI.LOGIN)
    Single<ReaderLogin> registerReader(@Body ReaderLogin reader);

    @GET(ConstantesAPI.LOGIN_PASSWORD_RECOVERY)
    Single<Response<String>> recoverPassword(@Query(ConstantesAPI.EMAIL) String email);

    @GET(ConstantesAPI.LOGIN_EMAIL_RESEND)
    Single<Response<String>> sendEmail(@Query(ConstantesAPI.EMAIL) String email);

    @GET(ConstantesAPI.LOGIN_LOGOUT)
    Single<Response<String>> logout();
}
