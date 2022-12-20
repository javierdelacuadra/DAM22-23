package data;

import com.google.gson.Gson;
import data.retrofit.LoginApi;
import io.reactivex.rxjava3.core.Single;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import model.Reader;
import model.ReaderLogin;
import retrofit2.Response;

public class DaoLogin extends DaoGenerics {

    private final LoginApi loginApi;

    @Inject
    public DaoLogin(LoginApi loginApi, Gson gson) {
        super(gson);
        this.loginApi = loginApi;
    }

    public Single<Either<String, Reader>> login(String authorization) {
        return createSafeSingleApiCall(loginApi.loginReader(authorization));
    }

    public Single<Either<String, ReaderLogin>> register(ReaderLogin reader) {
        return createSafeSingleApiCall(loginApi.registerReader(reader));
    }

    public Single<Response<String>> recoverPassword(String email) {
        return loginApi.recoverPassword(email);
    }

    public Single<Response<String>> sendEmail(String email) {
        return loginApi.sendEmail(email);
    }

    public Single<Response<String>> logout() {
        return loginApi.logout();
    }
}
