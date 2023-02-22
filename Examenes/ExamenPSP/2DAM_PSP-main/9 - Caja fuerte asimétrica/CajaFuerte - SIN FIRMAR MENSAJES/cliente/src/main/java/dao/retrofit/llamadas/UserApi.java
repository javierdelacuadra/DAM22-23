package dao.retrofit.llamadas;

import io.reactivex.rxjava3.core.Single;
import org.utils.common.AplicationConstants;
import org.utils.domain.modelo.User;
import org.utils.domain.modelo.dto.UserDTO;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface UserApi {

    String BASE_PATH = "users";

    @POST(BASE_PATH + AplicationConstants.PATH_LOGIN)
    Single<User> login(@Body UserDTO userDTO);

    @POST(BASE_PATH)
    Single<User> add(@Body User newUser);

    @GET(BASE_PATH + AplicationConstants.PATH_PARAM_USER_NAME + AplicationConstants.PATH_PARAM_CERT)
    Single<String> getCertificate(@Path(AplicationConstants.USER_NAME) String username);
}
