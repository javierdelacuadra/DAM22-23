package data;

import com.google.gson.Gson;
import data.retrofit.NewspapersApi;
import io.reactivex.rxjava3.core.Single;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import model.Reader;
import model.ReaderLogin;

import java.util.List;

public class DaoReadersSQL extends DaoGenerics {
    private final NewspapersApi newspapersApi;

    @Inject
    public DaoReadersSQL(NewspapersApi newspapersApi, Gson gson) {
        super(gson);
        this.newspapersApi = newspapersApi;
    }

    public Single<Either<String, List<Reader>>> getAll() {
        return createSafeSingleApiCall(newspapersApi.getReaders());
    }

    public Single<Either<String, Reader>> add(Reader reader) {
        return createSafeSingleApiCall(newspapersApi.addReader(reader));
    }

    public Single<Either<String, Reader>> update(Reader reader) {
        return createSafeSingleApiCall(newspapersApi.updateReader(reader));
    }

    public Single<Either<String, Boolean>> delete(String id) {
        return createSafeSingleDeleteCall(newspapersApi.deleteReader(id));
    }

    public Single<Either<String, ReaderLogin>> login(ReaderLogin readerLogin) {
        return createSafeSingleApiCall(newspapersApi.loginReader(readerLogin.getUsername(), readerLogin.getPassword()));
    }

    public Reader get(int id) {
        Reader reader = new Reader();
//        try (Connection con = db.getConnection();
//             PreparedStatement preparedStatement = con.prepareStatement(SQLQueries.SELECT_READER_BY_ID)) {
//            preparedStatement.setInt(1, id);
//            ResultSet rs = preparedStatement.executeQuery();
//            if (rs.next()) {
//                reader.setId(rs.getInt(Constantes.ID));
//                reader.setName(rs.getString(Constantes.NAME));
//                reader.setDateOfBirth(rs.getDate(Constantes.DATE_OF_BIRTH).toLocalDate());
//            }
//        } catch (SQLException e) {
//            Logger.getLogger(DaoReadersSQL.class.getName()).log(Level.SEVERE, null, e);
//        }
        return reader;
    }

    public Single<Either<String, ReaderLogin>> register(ReaderLogin reader) {
        return createSafeSingleApiCall(newspapersApi.registerReader(reader));
    }
}