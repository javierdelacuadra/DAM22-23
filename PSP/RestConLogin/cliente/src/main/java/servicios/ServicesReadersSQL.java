package servicios;

import data.DaoReadersSQL;
import io.reactivex.rxjava3.core.Single;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import model.Reader;
import model.ReaderLogin;
import okhttp3.Credentials;
import retrofit2.Response;

import java.util.List;

public class ServicesReadersSQL {

    private final DaoReadersSQL daoReadersSQL;

    @Inject
    public ServicesReadersSQL(DaoReadersSQL daoReadersSQL) {
        this.daoReadersSQL = daoReadersSQL;
    }

    public Single<Either<String, Reader>> saveReader(Reader reader) {
        return daoReadersSQL.add(reader);
    }

    public Single<Either<String, List<Reader>>> getAllReaders() {
        return daoReadersSQL.getAll();
    }

    public Single<Either<String, Boolean>> deleteReader(String id) {
        return daoReadersSQL.delete(id);
    }

    public Single<Either<String, Reader>> updateReader(Reader reader) {
        return daoReadersSQL.update(reader);
    }


    public Single<Either<String, ReaderLogin>> login(ReaderLogin readerLogin) {
        String credentials = Credentials.basic(readerLogin.getUsername(), readerLogin.getPassword());
        return daoReadersSQL.login(credentials);
    }

    public Single<Either<String, ReaderLogin>> register(ReaderLogin reader) {
        return daoReadersSQL.register(reader);
    }

    public Single<Response<String>> recoverPassword(String email) {
        return daoReadersSQL.recoverPassword(email);
    }

    public Single<Response<String>> sendEmail(String email) {
        return daoReadersSQL.sendEmail(email);
    }

    public Single<Response<String>> logout() {
        return daoReadersSQL.logout();
    }
}