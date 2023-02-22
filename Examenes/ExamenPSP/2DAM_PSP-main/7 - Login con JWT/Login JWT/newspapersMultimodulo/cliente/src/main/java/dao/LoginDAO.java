package dao;

import io.reactivex.rxjava3.core.Single;
import io.vavr.control.Either;
import org.miutils.domain.modelo.Reader;
import retrofit2.Response;

public interface LoginDAO {

    Single<Either<String, Response<Object>>> login(String user, String password);

    Single<Either<String, Response<Object>>> logout();

    Single<Either<String, Response<Object>>> forgotPassword(String text);

    Single<Either<String, Reader>> add(Reader reader);

    Single<Either<String, Response<Object>>> loginInvitado();
}
