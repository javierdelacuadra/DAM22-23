package dao.impl;

import dao.retrofit.llamadas.CredentialsApi;
import io.reactivex.rxjava3.core.Single;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import lombok.extern.log4j.Log4j2;
import retrofit2.Response;

@Log4j2
public class CredentialsDaoImpl extends DaoGenericsImpl {
    private final CredentialsApi credentialsApi;

    @Inject
    public CredentialsDaoImpl(CredentialsApi credentialsApi) {
        this.credentialsApi = credentialsApi;
    }

    public Single<Either<String, Response<Object>>> getPublic() {
        return safeSingleResponseApicall(credentialsApi.getPublicKey());
    }
}
