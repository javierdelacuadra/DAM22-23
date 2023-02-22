package dao.retrofit.llamadas;

import dao.common.APIConstants;
import io.reactivex.rxjava3.core.Single;
import org.miutils.common.AplicationConstants;
import org.miutils.domain.modelo.Reader;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface LoginApi {

    @GET(APIConstants.PATH_LOGIN)
    Single<Response<Object>> login(@Header(AplicationConstants.AUTHORIZATION) String basicAuthorization);

    @GET(APIConstants.PATH_LOGIN + AplicationConstants.PATH_LOGOUT)
    Single<Response<Object>> logout();

    @GET(APIConstants.PATH_LOGIN + AplicationConstants.PATH_FORGOT)
    Single<Response<Object>> forgot(@Header(AplicationConstants.EMAIL) String mail);

    @POST(APIConstants.PATH_LOGIN)
    Single<Reader> register(@Body Reader reader);

    @GET(APIConstants.PATH_LOGIN)
    Single<Response<Object>> loginInvitado(@Header(AplicationConstants.AUTHORIZATION) String basicAuthorization);
}
