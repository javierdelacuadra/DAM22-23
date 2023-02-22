package dao.retrofit.llamadas;

import io.reactivex.rxjava3.core.Single;
import org.utils.common.AplicationConstants;
import retrofit2.Response;
import retrofit2.http.GET;

public interface CredentialsApi {
    String BASE_PATH = "credentials";
    @GET(BASE_PATH + AplicationConstants.PATH_PUBLIC)
    Single<Response<Object>> getPublicKey();
}
