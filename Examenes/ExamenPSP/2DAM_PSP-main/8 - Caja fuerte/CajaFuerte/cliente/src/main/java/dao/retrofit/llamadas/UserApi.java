package dao.retrofit.llamadas;

import io.reactivex.rxjava3.core.Single;
import org.utils.common.AplicationConstants;
import org.utils.domain.modelo.User;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface UserApi {

    String BASE_PATH = "users";

    @GET(BASE_PATH + AplicationConstants.PATH_LOGIN)
    Single<Response<Object>> login(@Header(AplicationConstants.AUTHORIZATION) String auth);

    @POST(BASE_PATH)
    Single<Response<Object>> add(@Body User newUser);
}
