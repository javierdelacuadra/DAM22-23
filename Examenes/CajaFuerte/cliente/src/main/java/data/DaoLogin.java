package data;

import com.google.gson.Gson;
import data.retrofit.LoginApi;
import io.reactivex.rxjava3.core.Single;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import modelo.User;

public class DaoLogin extends DaoGenerics {

    private final LoginApi loginApi;

    @Inject
    public DaoLogin(LoginApi loginApi, Gson gson) {
        super(gson);
        this.loginApi = loginApi;
    }

    public Single<Either<String, User>> login(String authorization) {
        return createSafeSingleApiCall(loginApi.loginReader(authorization));
    }
}
