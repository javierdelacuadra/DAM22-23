package data.retrofit;

import data.retrofit.common.ConstantesAPI;
import io.reactivex.rxjava3.core.Single;
import modelo.User;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface LoginApi {
    @GET(ConstantesAPI.LOGIN)
    Single<User> loginReader(@Header(ConstantesAPI.AUTHORIZATION) String authorization);
}
