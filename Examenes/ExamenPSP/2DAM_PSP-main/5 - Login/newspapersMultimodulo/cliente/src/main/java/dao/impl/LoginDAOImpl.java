package dao.impl;

import dao.LoginDAO;
import dao.retrofit.llamadas.NewsApi;
import io.reactivex.rxjava3.core.Single;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import org.miutils.domain.modelo.Reader;
import retrofit2.Response;

public class LoginDAOImpl extends DaoGenericsImpl implements LoginDAO {


    private final NewsApi newsApi;

    @Inject
    public LoginDAOImpl(NewsApi newsApi) {
        this.newsApi = newsApi;
    }

    @Override
    public Single<Either<String, Reader>> login(String user, String password) {
       Single<Reader> readerSingle = newsApi.login(user, password);
         return safeSingleApicall(readerSingle);
    }

    @Override
    public Single<Either<String, Response<Object>>> logout() {
        return safeSingleResponseApicall(newsApi.logout());
    }

    @Override
    public Single<Either<String, Response<Object>>> forgotPassword(String mail) {
        return safeSingleResponseApicall(newsApi.forgot(mail));
    }

    @Override
    public Single<Either<String, Reader>> add(Reader reader) {
        return safeSingleApicall(newsApi.register(reader));
    }
}
